package org.incode.eurocommercial.ecpcrm.dom.event;

import java.util.Map;

import org.junit.Ignore;
import org.junit.Test;

import org.incode.eurocommercial.ecpcrm.dom.aspect.AspectType;

import static org.assertj.core.api.Assertions.assertThat;

@Ignore
public class EventSourceTypeTest {

    public static class SocialAccountTest extends EventSourceTypeTest {

        @Test
        public void xxx() throws Exception {

            // given
            String facebookdata = "userurl=&userid=1984384839890369&fullname=Pattie Yeliashev&first_name=Pattie&last_name=Yeliashev&birthday=&age_range=min:21&gender=female";

            // when
            final Map<AspectType, String> map = EventSourceType.WifiProjectsUtentiCsv.SocialAccount.Facebook.toMap(facebookdata);

            // Dan
            assertThat(map.get(AspectType.FirstName)).isEqualTo("Pattie");
            assertThat(map.get(AspectType.LastName)).isEqualTo("Yeliashev");
            assertThat(map.get(AspectType.Birthday)).isEqualTo("");
            assertThat(map.get(AspectType.Gender)).isEqualTo("female");
            assertThat(map.get(AspectType.FacebookAccount)).isEqualTo("1984384839890369");
        }
    }

    public static class WifiProjectsUtentiCsvTest extends EventSourceTypeTest {

        @Test
        public void happy_case() throws Exception {

            // given
            String data = "Leonid;Mergue;29/03/2017;lmerguem@quantcast.com;;Facebook;userurl=&userid=1240943875699972&fullname=Leonid Mergue&first_name=Leonid&last_name=Mergue&birthday=&age_range=max:17,min:13&gender=male";

            // when
            final Map<AspectType, String> map = EventSourceType.WifiProjects_Utenti_Csv.getParser().toMap(data);

            // Dan
            assertThat(map.size()).isEqualTo(8);


            assertThat(map.get(AspectType.FirstName)).isEqualTo("Leonid");
            assertThat(map.get(AspectType.LastName)).isEqualTo("Mergue");
            assertThat(map.get(AspectType.Birthday)).isEqualTo("");
            assertThat(map.get(AspectType.EmailAccount)).isEqualTo("lmerguem@quantcast.com");
            assertThat(map.get(AspectType.FacebookAccount)).isEqualTo("1240943875699972");
            assertThat(map.get(AspectType.Gender)).isEqualTo("male");
            assertThat(map.get(AspectType.Access)).isEqualTo("29/03/2017");
        }
    }

    public static class WifiProjectsAccessiCsvTest extends EventSourceTypeTest {

        @Test
        public void happy_case_with_facebook() throws Exception {

            // given
            String data = "19550995;ebegg4@fastcompany.com;81:B9:C0:9C:14:BB;2018-01-02 10:47:34;2018-01-02 11:14:04;FB;Lost-Service";

            // when
            final Map<AspectType, String> map = EventSourceType.WifiProjects_Accessi_Csv.getParser().toMap(data);

            // Dan
            assertThat(map.size()).isEqualTo(3);
            assertThat(map.get(AspectType.FacebookAccount)).isEqualTo("ebegg4@fastcompany.com");
            assertThat(map.get(AspectType.MacAddress)).isEqualTo("81:B9:C0:9C:14:BB");
            assertThat(map.get(AspectType.EmailAccount)).isEqualTo("ebegg4@fastcompany.com");
        }

        @Test
        public void happy_case_with_sms() throws Exception {

            // given
            String data = "19550995;+319876543210;81:B9:C0:9C:14:BB;2018-01-02 10:47:34;2018-01-02 11:14:04;SMS;Lost-Service";

            // when
            final Map<AspectType, String> map = EventSourceType.WifiProjects_Accessi_Csv.getParser().toMap(data);

            // Dan
            assertThat(map.size()).isEqualTo(2);
            assertThat(map.get(AspectType.MacAddress)).isEqualTo("81:B9:C0:9C:14:BB");
            assertThat(map.get(AspectType.CellPhoneNumber)).isEqualTo("+319876543210");
        }

        @Test
        public void cant_find_method() throws Exception {

            // given
            String data = "19550995;+319876543210;81:B9:C0:9C:14:BB;2018-01-02 10:47:34;2018-01-02 11:14:04;;Lost-Service";

            // when
            final Map<AspectType, String> map = EventSourceType.WifiProjects_Accessi_Csv.getParser().toMap(data);

            // Dan
            assertThat(map.size()).isEqualTo(1);
            assertThat(map.get(AspectType.MacAddress)).isEqualTo("81:B9:C0:9C:14:BB");
        }
    }



}