package org.incode.eurocommercial.relatio.dom.event;

import org.incode.eurocommercial.relatio.dom.aspect.AspectType;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@Ignore
public class EventSourceTypeTest {

    public static class SocialAccountTest extends EventSourceTypeTest {

        @Test
        @Ignore
        public void xxx() throws Exception {

            // given
            String facebookdata = "userurl=&userid=1984384839890369&fullname=Pattie Yeliashev&first_name=Pattie&last_name=Yeliashev&birthday=&age_range=min:21&gender=female";

            // when
            final Map<AspectType, String> map = EventSourceType.WifiProjectsUtentiCsv.SocialAccount.Facebook.toMap(facebookdata);

            // then
            assertThat(map.get(AspectType.FirstName)).isEqualTo("Pattie");
            assertThat(map.get(AspectType.LastName)).isEqualTo("Yeliashev");
            assertThat(map.get(AspectType.DateOfBirth)).isEqualTo("");
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

            // then
            assertThat(map.size()).isEqualTo(8);


            assertThat(map.get(AspectType.FirstName)).isEqualTo("Leonid");
            assertThat(map.get(AspectType.LastName)).isEqualTo("Mergue");
            assertThat(map.get(AspectType.DateOfBirth)).isEqualTo("");
            assertThat(map.get(AspectType.EmailAccount)).isEqualTo("lmerguem@quantcast.com");
            assertThat(map.get(AspectType.FacebookAccount)).isEqualTo("1240943875699972");
            assertThat(map.get(AspectType.Gender)).isEqualTo("male");
            assertThat(map.get(AspectType.Access)).isEqualTo("2017-03-29");
        }
    }

    public static class WifiProjectsAccessiCsvTest extends EventSourceTypeTest {

        @Test
        @Ignore
        public void happy_case_with_facebook() throws Exception {

            // given
            String data = "19550995;ebegg4@fastcompany.com;81:B9:C0:9C:14:BB;2018-01-02 10:47:34;2018-01-02 11:14:04;FB;Lost-Service";

            // when
            final Map<AspectType, String> map = EventSourceType.WifiProjects_Accessi_Csv.getParser().toMap(data);

            // then
            assertThat(map.size()).isEqualTo(3);
            assertThat(map.get(AspectType.FacebookAccount)).isEqualTo("ebegg4@fastcompany.com");
            assertThat(map.get(AspectType.MacAddress)).isEqualTo("81:B9:C0:9C:14:BB");
            assertThat(map.get(AspectType.EmailAccount)).isEqualTo("ebegg4@fastcompany.com");
        }

        @Test
        @Ignore
        public void happy_case_with_sms() throws Exception {

            // given
            String data = "19550995;+319876543210;81:B9:C0:9C:14:BB;2018-01-02 10:47:34;2018-01-02 11:14:04;SMS;Lost-Service";

            // when
            final Map<AspectType, String> map = EventSourceType.WifiProjects_Accessi_Csv.getParser().toMap(data);

            // then
            assertThat(map.size()).isEqualTo(2);
            assertThat(map.get(AspectType.MacAddress)).isEqualTo("81:B9:C0:9C:14:BB");
            assertThat(map.get(AspectType.CellPhoneNumber)).isEqualTo("+319876543210");
        }

        @Test
        @Ignore
        public void cant_find_method() throws Exception {

            // given
            String data = "19550995;+319876543210;81:B9:C0:9C:14:BB;2018-01-02 10:47:34;2018-01-02 11:14:04;;Lost-Service";

            // when
            final Map<AspectType, String> map = EventSourceType.WifiProjects_Accessi_Csv.getParser().toMap(data);

            // then
            assertThat(map.size()).isEqualTo(1);
            assertThat(map.get(AspectType.MacAddress)).isEqualTo("81:B9:C0:9C:14:BB");
        }
    }

    public static class GamePlayedEventV1 extends EventSourceTypeTest {

        @Test
        public void happy_case_rosa() throws Exception {

            // given
            String data = "Rosa~Di Mamma~F~51~~3288993344~22040~NO~YES~2018-07-21T08:19:23~BENVENUTO";

            // when
            final Map<AspectType, String> map = EventSourceType.GamePlayedEventV1.getParser().toMap(data);

            // then
            assertThat(map.size()).isEqualTo(11);
            assertThat(map.get(AspectType.FirstName)).isEqualTo("Rosa");
            assertThat(map.get(AspectType.LastName)).isEqualTo("Di Mamma");
            assertThat(map.get(AspectType.Gender)).isEqualTo("FEMALE");
            assertThat(map.get(AspectType.EmailAccount)).isEqualTo("");
            assertThat(map.get(AspectType.CellPhoneNumber)).isEqualTo("3288993344");
            assertThat(map.get(AspectType.PostalCode)).isEqualTo("22040");
            assertThat(map.get(AspectType.MarketingConsent)).isEqualTo("false");
            assertThat(map.get(AspectType.PrivacyConsent)).isEqualTo("true");
            assertThat(map.get(AspectType.GamePlayDateAndTime)).isEqualTo("2018-07-21T08:19:23");
            assertThat(map.get(AspectType.GameType)).isEqualTo("BENVENUTO");

        }

        @Test
        public void happy_case_sandrino() throws Exception {

            // given
            String data = "Sandrino~Ginnio~M~34~hello@yahoo.com~3912345678~23900~YES~YES~2018-07-21T08:26:51~BENVENUTO";

            // when
            final Map<AspectType, String> map = EventSourceType.GamePlayedEventV1.getParser().toMap(data);

            // then
            assertThat(map.size()).isEqualTo(11);
            assertThat(map.get(AspectType.FirstName)).isEqualTo("Sandrino");
            assertThat(map.get(AspectType.LastName)).isEqualTo("Ginnio");
            assertThat(map.get(AspectType.Gender)).isEqualTo("MALE");
            assertThat(map.get(AspectType.EmailAccount)).isEqualTo("hello@yahoo.com");
            assertThat(map.get(AspectType.CellPhoneNumber)).isEqualTo("3912345678");
            assertThat(map.get(AspectType.PostalCode)).isEqualTo("23900");
            assertThat(map.get(AspectType.MarketingConsent)).isEqualTo("true");
            assertThat(map.get(AspectType.PrivacyConsent)).isEqualTo("true");
            assertThat(map.get(AspectType.GamePlayDateAndTime)).isEqualTo("2018-07-21T08:26:51");
            assertThat(map.get(AspectType.GameType)).isEqualTo("BENVENUTO");

        }

        @Test
        public void happy_case_no_gender() throws Exception {

            // given
            String data = "No~Gender~~51~~3288993344~22040~YES~NO~2018-07-21T08:19:23~BENVENUTO";

            // when
            final Map<AspectType, String> map = EventSourceType.GamePlayedEventV1.getParser().toMap(data);

            // then
            assertThat(map.size()).isEqualTo(10);
            assertThat(map.get(AspectType.FirstName)).isEqualTo("No");
            assertThat(map.get(AspectType.LastName)).isEqualTo("Gender");
            assertThat(map.get(AspectType.Gender)).isNull();
            assertThat(map.get(AspectType.ApproximateDateOfBirth)).isEqualTo("1967-01-21");
            assertThat(map.get(AspectType.EmailAccount)).isEqualTo("");
            assertThat(map.get(AspectType.CellPhoneNumber)).isEqualTo("3288993344");
            assertThat(map.get(AspectType.PostalCode)).isEqualTo("22040");
            assertThat(map.get(AspectType.MarketingConsent)).isEqualTo("true");
            assertThat(map.get(AspectType.PrivacyConsent)).isEqualTo("false");
            assertThat(map.get(AspectType.GamePlayDateAndTime)).isEqualTo("2018-07-21T08:19:23");
            assertThat(map.get(AspectType.GameType)).isEqualTo("BENVENUTO");

        }

        @Test
        public void happy_case_trim() throws Exception {

            // given
            String data = " Rosa ~ Di Mamma ~ F ~ 51 ~  ~ 3288993344 ~ 22040 ~ NO ~ YES ~ 2018-07-21T08:19:23 ~ BENVENUTO ";

            // when
            final Map<AspectType, String> map = EventSourceType.GamePlayedEventV1.getParser().toMap(data);

            // then
            assertThat(map.size()).isEqualTo(11);
            assertThat(map.get(AspectType.FirstName)).isEqualTo("Rosa");
            assertThat(map.get(AspectType.LastName)).isEqualTo("Di Mamma");
            assertThat(map.get(AspectType.Gender)).isEqualTo("FEMALE");
            assertThat(map.get(AspectType.ApproximateDateOfBirth)).isEqualTo("1967-01-21");
            assertThat(map.get(AspectType.EmailAccount)).isEqualTo("");
            assertThat(map.get(AspectType.CellPhoneNumber)).isEqualTo("3288993344");
            assertThat(map.get(AspectType.PostalCode)).isEqualTo("22040");
            assertThat(map.get(AspectType.MarketingConsent)).isEqualTo("false");
            assertThat(map.get(AspectType.PrivacyConsent)).isEqualTo("true");
            assertThat(map.get(AspectType.GamePlayDateAndTime)).isEqualTo("2018-07-21T08:19:23");
            assertThat(map.get(AspectType.GameType)).isEqualTo("BENVENUTO");

        }
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void sad_case() throws Exception {
        // given
        String data = "Rosa~Di Mamma~~~";

        // when
        final Map<AspectType, String> map = EventSourceType.GamePlayedEventV1.getParser().toMap(data);

        // then exception is thrown
    }
}

