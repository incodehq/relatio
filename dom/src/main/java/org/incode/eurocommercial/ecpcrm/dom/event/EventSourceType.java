package org.incode.eurocommercial.ecpcrm.dom.event;

import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.google.common.base.Splitter;
import com.google.common.collect.Maps;

import org.apache.isis.applib.value.Blob;

import org.incode.eurocommercial.ecpcrm.dom.aspect.Aspect;
import org.incode.eurocommercial.ecpcrm.dom.aspect.AspectType;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum EventSourceType {

    WifiProjects_Utenti_Csv(WifiProjectsUtentiCsv.class),
    WifiProjects_Accessi_Csv(WifiProjectsAccessiCsv.class),
    ContestOnline_2017_Csv(ContestOnline2017Csv.class);

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

        String data;
        try {
            data = new String(blob.getBytes(), "UTF-8");
            String[] records = data.split("[\r?\n]+");
            for (int i = 1; i < records.length; i++) {
                final Event event = eventRepository.create(source, records[i]);
                for (Aspect aspect : event.getAspects()) {
                    if (aspect.getProfile() != null) {
                        aspect.getType().updateProfile(aspect);
                    }
                }
            }
            source.setStatus(EventSource.Status.SUCCESS);
        } catch (UnsupportedEncodingException e) {
            source.setStatus(EventSource.Status.FAILURE);
            e.printStackTrace();
        }
        return source;
    }


    public interface EventParser {
        Map<AspectType, String> toMap(String data);
    }

    public interface EventParserForCsv extends EventParser {
        String header();
        String separator();
    }


    public static class WifiProjectsUtentiCsv implements EventParserForCsv {
        public String header() {
            return "NOME;COGNOME;DATA PRIMO ACCESSO;EMAIL;TELEFONO;ACCESSO SOCIAL;DATI SOCIAL";
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
                map.put(AspectType.FirstAccess, values[2]);
                map.put(AspectType.EmailAccount, values[3]);
                map.put(AspectType.PhoneNumber, values[4]);
                try {
                    final SocialAccount socialAccount = SocialAccount.valueOfNormalised(values[5]);
                    map.putAll(socialAccount.toMap(values[6]));
                } catch (Exception e){
                    //
                }
            } catch (ArrayIndexOutOfBoundsException e){

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
                    return Collections.emptyMap();
                }
            },
            SMS(AspectType.PhoneNumber) {
                @Override Map<AspectType, String> toMap(final String input) {
                    return Collections.emptyMap();
                }
            };
            ;
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

        @Override public String separator() {
            return ";";
        }

        @Override
        public Map<AspectType, String> toMap(String data) {
            Map<AspectType, String> map = Maps.newHashMap();

            try {
                final String[] values = data.split(separator());
                map.put(AspectType.MacAddress, values[2]);
                map.putAll(Accesso.from(values));
            } catch (ArrayIndexOutOfBoundsException e){

            }

            return map;
        }

        public enum Accesso {
            FB() {
                @Override Map<AspectType, String> toMap(final String[] input) {
                    Map<AspectType, String> map = Maps.newHashMap();
                    map.put(AspectType.FacebookAccount, input[1]);
                    map.put(AspectType.EmailAccount, input[1]);
                    return map;
                }
            },
            SMS {
                @Override Map<AspectType, String> toMap(final String[] input) {
                    Map<AspectType, String> map = Maps.newHashMap();
                    map.put(AspectType.PhoneNumber, input[1]);
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
                map.put(AspectType.PhoneNumber, values[5]);
                map.put(AspectType.City, values[7]);
                map.put(AspectType.RegisteredAt, values[14]);
                map.put(AspectType.MailConfirmedAt, values[15]);
                map.put(AspectType.FacebookAccount, values[18]);
            } catch (ArrayIndexOutOfBoundsException e) {
            }

            return map;
        }
    }
}
