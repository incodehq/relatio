package org.incode.eurocommercial.ecpcrm.dom.event;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.google.common.base.Splitter;
import com.google.common.collect.Maps;

import org.apache.isis.applib.value.Blob;

import org.incode.eurocommercial.ecpcrm.dom.aspect.Aspect;
import org.incode.eurocommercial.ecpcrm.dom.aspect.AspectType;
import org.incode.eurocommercial.ecpcrm.dom.utils.DateFormatUtils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum EventSourceType {

    WifiProjects_Utenti_Csv(WifiProjectsUtentiCsv.class),
    WifiProjects_Accessi_Csv(WifiProjectsAccessiCsv.class),
    ContestOnline_2017_Csv(ContestOnline2017Csv.class),
    Newsletter_Online_Contest_Csv(NewsletterOnlineContestCsv.class),
    Infopoint_Csv(InfoPointCsv.class);

    private Class<? extends EventParser> cls;

    public EventParser getParser() {
        try {
            return cls.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    public EventSource parseBlob(final Blob blob, final EventRepository eventRepository, final EventSourceRepository eventSourceRepository) {
        EventSource source = eventSourceRepository.create(this);

        try {
            ByteArrayInputStream is = new ByteArrayInputStream(blob.getBytes());
            BufferedReader bfReader = new BufferedReader(new InputStreamReader(is));

            /* Skip header lines (TODO: generalise this, since its csv-specific) */
            for (int i = 0;  i < Objects.requireNonNull(getParser()).headerSize(); i++) {
                bfReader.readLine();
            }
            for (String record = bfReader.readLine(); record != null; record = bfReader.readLine()) {
                final long time = System.nanoTime();
                final Event event = eventRepository.create(source, record);
                for (Aspect aspect : event.getAspects()) {
                    if (aspect.getProfile() != null) {
                        aspect.getType().updateProfile(aspect);
                    }
                }
                final double elapsedTime = (System.nanoTime() - time) / 1e9;
                System.out.println("==========================> Elapsed time: " + elapsedTime);
            }
            source.setStatus(EventSource.Status.SUCCESS);
        } catch (IOException e) {
            source.setStatus(EventSource.Status.FAILURE);
            e.printStackTrace();
        }
        return source;
    }


    public interface EventParser {
        Map<AspectType, String> toMap(String data);
        int headerSize();
    }

    public interface EventParserForCsv extends EventParser {
        String header();
        String separator();
    }


    public static class WifiProjectsUtentiCsv implements EventParserForCsv {
        public String header() {
            return "NOME;COGNOME;DATA PRIMO ACCESSO;EMAIL;TELEFONO;ACCESSO SOCIAL;DATI SOCIAL";
        }

        public int headerSize() {
            return 1;
        }

        @Override public String separator() {
            return ";";
        }

        @Override
        public Map<AspectType, String> toMap(String data) {
            Map<AspectType, String> map = Maps.newHashMap();

            try {
                final String[] values = data.split(separator());
                map.put(AspectType.FirstName, values[0]);
                map.put(AspectType.LastName, values[1]);
                map.put(AspectType.Access, DateFormatUtils.toISOLocalDate(values[2], "dd/MM/yyyy"));
                map.put(AspectType.EmailAccount, values[3]);
                map.put(AspectType.CellPhoneNumber, values[4]);
                try {
                    final SocialAccount socialAccount = SocialAccount.valueOfNormalised(values[5]);
                    map.putAll(socialAccount.toMap(values[6]));
                } catch (Exception e){
                    //
                }
            } catch (ArrayIndexOutOfBoundsException e) {

            }

            return map;
        }

        @AllArgsConstructor
        public enum SocialAccount{
            Twitter(AspectType.TwitterAccount) {
                @Override Map<AspectType, String> toMap(final String input) {
                    return new HashMap<>();
                }
            },
            Facebook(AspectType.FacebookAccount) {
                @Override Map<AspectType, String> toMap(final String input) {
                    final Map<String, String> stringMap = Splitter.on('&').withKeyValueSeparator('=').split(input);
                    Map<AspectType, String> map = Maps.newHashMap();
                    map.put(AspectType.FacebookAccount, stringMap.get("userid"));
                    map.put(AspectType.FirstName, stringMap.get("first_name"));
                    map.put(AspectType.LastName, stringMap.get("last_name"));
                    map.put(AspectType.Birthday, stringMap.get("birthday"));
                    map.put(AspectType.Gender, stringMap.get("gender"));
                    // TODO: We see age_range, what to do with it?
                    return map;
                }
            },
            GooglePlus(AspectType.GooglePlusAccount) {
                @Override Map<AspectType, String> toMap(final String input) {
                    final Map<String, String> stringMap = Splitter.on('&').withKeyValueSeparator('=').split(input);
                    Map<AspectType, String> map = Maps.newHashMap();
                    map.put(AspectType.GooglePlusAccount, stringMap.get("userid"));
                    map.put(AspectType.FirstName, stringMap.get("first_name"));
                    map.put(AspectType.LastName, stringMap.get("last_name"));
                    map.put(AspectType.Gender, stringMap.get("gender"));
                    return map;
                }
            },
            LinkedIn(AspectType.LinkedInAccount) {
                @Override Map<AspectType, String> toMap(final String input) {
                    final Map<String, String> stringMap = Maps.newHashMap();
                    String[] pairs = input.split("&");
                    for (String pair : pairs) {
                        String[] parts = pair.split("=", 1);
                        stringMap.put(parts[0], parts[1]);
                    }
                    Map<AspectType, String> map = Maps.newHashMap();
                    map.put(AspectType.LinkedInAccount, stringMap.get("userid"));
                    return map;
                }
            },
            SMS(AspectType.CellPhoneNumber) {
                @Override Map<AspectType, String> toMap(final String input) {
                    return Collections.emptyMap();
                }
            };

            @Getter
            private AspectType aspectType;

            abstract Map<AspectType, String> toMap(String input);

            static SocialAccount valueOfNormalised(final String name){
                return SocialAccount.valueOf(name.replace("+", "Plus"));
            }

        }
    }

    public static class WifiProjectsAccessiCsv implements EventParserForCsv {
        public String header() {
            return "ID;USERNAME;MAC-ADDR.;DATA ACCESSO;DATA FINE;ACCESSO;NOTE";
        }

        public int headerSize() {
            return 1;
        }

        @Override public String separator() {
            return ";";
        }

        @Override
        public Map<AspectType, String> toMap(String data) {
            Map<AspectType, String> map = Maps.newHashMap();

            try {
                final String[] values = data.split(separator());
                map.put(AspectType.MacAddress, values[2]);
                map.put(AspectType.Access, DateFormatUtils.toISOLocalDateTime(values[3], "yyyy-MM-dd HH:mm:ss"));
                map.putAll(Accesso.from(values));
            } catch (ArrayIndexOutOfBoundsException ignored) {
            }

            return map;
        }

        public enum Accesso {
            FB() {
                @Override Map<AspectType, String> toMap(final String[] input) {
                    Map<AspectType, String> map = Maps.newHashMap();
                    map.put(AspectType.EmailAccount, input[1]);
                    return map;
                }
            },
            SMS {
                @Override Map<AspectType, String> toMap(final String[] input) {
                    Map<AspectType, String> map = Maps.newHashMap();
                    map.put(AspectType.CellPhoneNumber, input[1]);
                    return map;
                }
            };

            abstract Map<AspectType, String> toMap(String[] input);

            public static Map<AspectType, String> from(final String[] values) {
                try {
                    return Accesso.valueOf(values[5]).toMap(values);
                } catch (IllegalArgumentException e) {
                    //TODO cannot parse
                    return new HashMap<>();
                }
            }
        }
    }

    public static class ContestOnline2017Csv implements EventParserForCsv {
        public String header() {
            return "nome;cognome;sesso;nascita;email;cellulare;via;citta;civico;cap;provincia;stato_famiglia;hafigli;privacy;data_ins;data_conferma_mail;data_conferma_cell;stato_mail;facebook_id;accetta comunicazioni marketing;accetta profilazione";
        }

        public int headerSize() {
            return 1;
        }

        @Override public String separator() {
            return ";";
        }

        @Override
        public Map<AspectType, String> toMap(String data) {
            Map<AspectType, String> map = Maps.newHashMap();

            try {
                final String[] values = data.replaceAll("\\[NULL]", "").replaceAll("\\{ }", "").split(separator());
                map.put(AspectType.FirstName, values[0]);
                map.put(AspectType.LastName, values[1]);
                map.put(AspectType.EmailAccount, values[4]);
                map.put(AspectType.CellPhoneNumber, values[5]);
                map.put(AspectType.City, values[7]);
                map.put(AspectType.RegisteredAt, DateFormatUtils.toISOLocalDateTime(values[14], "MMM d yyyy HH:mm:ss:SSSa"));
                map.put(AspectType.MailConfirmedAt, DateFormatUtils.toISOLocalDateTime(values[14], "MMM d yyyy HH:mm:ss:SSSa"));
                map.put(AspectType.FacebookAccount, values[18]);
            } catch (ArrayIndexOutOfBoundsException ignored) {
            }

            return map;
        }
    }

    public static class InfoPointCsv implements EventParserForCsv {
        public String header() {
            return "NOME;COGNOME;DATA DI NASCITA;INDIRIZZO;CITTA';Country;PV;CAP;TELEFONO;CELL.;E-MAIL ;CONSENSO II";
        }

        public int headerSize() {
            return 1;
        }

        @Override public String separator() {
            return ";";
        }

        @Override
        public Map<AspectType, String> toMap(String data) {
            Map<AspectType, String> map = Maps.newHashMap();

            try {
                final String[] values = data.split(separator());
                map.put(AspectType.FirstName, values[0]);
                map.put(AspectType.LastName, values[1]);
                try {
                    map.put(AspectType.Birthday, DateFormatUtils.toISOLocalDate(values[2], "dd/MM/yyyy"));
                } catch (Exception ignored) {
                }
                map.put(AspectType.Address, values[3]);
                map.put(AspectType.City, values[4]);
                map.put(AspectType.Country, values[5]);
                map.put(AspectType.PostCode, values[7]);
                map.put(AspectType.HomePhoneNumber, values[8]);
                map.put(AspectType.CellPhoneNumber, values[9]);
                map.put(AspectType.EmailAccount, values[10]);
            } catch (ArrayIndexOutOfBoundsException ignored) {
            }

            return map;
        }
    }

    public static class NewsletterOnlineContestCsv implements EventParserForCsv {
        public String header() {
            return null;
        }

        public int headerSize() {
            return 0;
        }

        @Override public String separator() {
            return ";";
        }

        @Override
        public Map<AspectType, String> toMap(String data) {
            Map<AspectType, String> map = Maps.newHashMap();

            try {
                final String[] values = data.split(separator());
                map.put(AspectType.Address, values[0]);
                map.put(AspectType.EmailAccount, values[1]);
                map.put(AspectType.City, values[2]);
                map.put(AspectType.FirstName, values[3]);
                map.put(AspectType.LastName, values[4]);
            } catch (ArrayIndexOutOfBoundsException e) {
            }

            return map;
        }
    }
}
