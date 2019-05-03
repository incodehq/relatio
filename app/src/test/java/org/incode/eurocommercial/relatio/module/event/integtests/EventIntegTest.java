package org.incode.eurocommercial.relatio.module.event.integtests;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;

import org.apache.isis.applib.fixturescripts.FixtureScripts;
import org.apache.isis.applib.services.clock.ClockService;

import org.incode.eurocommercial.relatio.module.aspect.dom.AspectRepository;
import org.incode.eurocommercial.relatio.module.base.integtests.RelatioIntegTestAbstract;
import org.incode.eurocommercial.relatio.module.event.dom.Event;
import org.incode.eurocommercial.relatio.module.event.dom.EventRepository;
import org.incode.eurocommercial.relatio.module.event.dom.EventSource;
import org.incode.eurocommercial.relatio.module.event.dom.EventSourceRepository;
import org.incode.eurocommercial.relatio.module.event.dom.EventSourceType;
import org.incode.eurocommercial.relatio.module.event.fixture.WifiprojectAccessiEventFixture;
import org.incode.eurocommercial.relatio.module.event.fixture.WifiprojectUtentiEventFixture;

import static org.assertj.core.api.Assertions.assertThat;

public class EventIntegTest extends RelatioIntegTestAbstract {

    @Inject private FixtureScripts fixtureScripts;
    @Inject ClockService clockService;
    @Inject EventRepository eventRepository;
    @Inject EventSourceRepository eventSourceRepository;
    @Inject AspectRepository aspectRepository;


    @Before
    public void setUp() throws Exception {
        // given
        fixtureScripts.runFixtureScript(new WifiprojectAccessiEventFixture(), null);
        fixtureScripts.runFixtureScript(new WifiprojectUtentiEventFixture(), null);
    }

    public static class ImportAccessi extends EventIntegTest {
        @Test
        public void can_import_accessi() {
            // given
            String data = "19550995;ebegg4x@fastcompany.com;99:B9:C0:9C:14:BB;2018-01-02 10:47:34;2018-01-02 11:14:04;FB;Lost-Service";

            // when
            EventSource source = eventSourceRepository.findByType(EventSourceType.WifiProjects_Accessi_Csv).get(0);
            Event event = eventRepository.findOrCreate(source, data);

            // then
            assertThat(event).isNotNull();
            assertThat(aspectRepository.findByEvent(event)).hasSize(3);
        }
    }

    public static class ImportUtenti extends EventIntegTest {
        @Test
        public void can_import_utenti() {
            // given
            String data = "Luca;Rendina;28/03/2017;mama@hotmail.it;;Facebook;userurl=&userid=1231369723582333&fullname=Luca Rendina&first_name=Luca&last_name=Rendina&birthday=&age_range=max:20,min:18&gender=male";

            // when
            EventSource source = eventSourceRepository.findOrCreate(EventSourceType.WifiProjects_Utenti_Csv, "integ test");
            Event event = eventRepository.findOrCreate(source, data);

            // then
            assertThat(event).isNotNull();
            assertThat(aspectRepository.findByEvent(event)).hasSize(6);
        }
    }

    public static class QuickTapSurveyIntegTest extends EventIntegTest {
        @Test
        public void can_import_QuickTapSurvey() {
            // given
            String data = "2019-01-27 19:27:33.0000000;2019-01-27 19:27:33.0000000;174;ECPIpad3;0;0;No;Ingresso DESIGUAL;Yes;Potato;Mattavelli;orndo@tim.it;3666666612;1923-02-20;No;Yes;Male;passeggiare - fare un giro - vetrine;1 volta ogni 2 mesi;;Più di 10 anni;Automobile;8;7;7;7;7;7;8;7;1;MAI USATO;MAI USATO;MAI USATO;MAI USATO;MAI USATO;MAI USATO;10;MAI USATO;No;;;;;Esselunga (Monza),Esselunga (Vimercate),Il Gigante (Vimercate);30;Yes;Zara;0;No;Pizza,Hamburger,Pasta;Gluten Free;No;;;;Non uso / Non ho internet / non ci penso;Non ricordo alcuna campagna pubblicitaria o evento;Ristorante,Moda donna,Scarpe,Accessori,Moda uomo,Intimo;8;5;Yes;TORRI BIANCHE,GLOBO;20864;Agrate Brianza;;;;No;No;Solo;4;1953";

            // when
            EventSource source = eventSourceRepository.findOrCreate(EventSourceType.QuickTapSurveyCarosello, "integ test");
            Event event = eventRepository.findOrCreate(source, data);

            // then
            assertThat(event).isNotNull();
            assertThat(aspectRepository.findByEvent(event)).hasSize(30);
        }
    }

    public static class PTA_CouponingCampaignIntegTest extends EventIntegTest {
        @Test
        public void can_import_PTA_CouponingCampaign() {
            // given

            test( "Coupon;;Salmoiraghi;salmoiraghi2@salmoiraghi.com;ND;-40;YES;YES;YES;;", 8);

            test("InfoPad;massimo;pistacio  ;dontcallmepistacio@hotmail.com;M;+40;YES;YES;YES;1969-06-26;via cavour", 11);

        }

        private void test(final String data, final int expectedAspectSize) {
            // when
            EventSource source = eventSourceRepository.findOrCreate(EventSourceType.PTA_CouponingCampaignData, "integ test");
            Event event = eventRepository.findOrCreate(source, data);

            // then
            assertThat(event).isNotNull();
            assertThat(aspectRepository.findByEvent(event)).hasSize(expectedAspectSize);
        }
    }

    public static class OceanEventIntegTest extends EventIntegTest {
        @Test
        public void can_import_Ocean_Event() {
            // given

            test( "5;Ramito;Postaeto;;;via Clemea Alwea, 51;Carote;20061;lala.potato@jenkkins.com;;yes;no;no;no;no", 8);

            test("5;osososos;Dellixioso;;;via Clemente Albertito Bebexito, 51;Malala;20061;tia.tamara@pistacio.com;3666666612;yes;yes;yes;yes;yes", 9);

        }

        private void test(final String data, final int expectedAspectSize) {
            // when
            EventSource source = eventSourceRepository.findOrCreate(EventSourceType.OceanEvent, "integ test");
            Event event = eventRepository.findOrCreate(source, data);

            // then
            assertThat(event).isNotNull();
            assertThat(aspectRepository.findByEvent(event)).hasSize(expectedAspectSize);
        }
    }

    public static class WifiDataIntegTest extends EventIntegTest {
        @Test
        public void can_import_Wifi_Data() {
            // given

            test( "naranha;pensssd;2018-06-07 00:00:00;clssddy.pes1@gmail.com;+393923666666;;;SMS;SI", 6);

            test("Dooén;Kousa;2018-06-07 00:00:00;kougroaa10.kn@gmail.com;;OVER 21;M;Facebook;", 5);

        }

        private void test(final String data, final int expectedAspectSize) {
            // when
            EventSource source = eventSourceRepository.findOrCreate(EventSourceType.WifiData, "integ test");
            Event event = eventRepository.findOrCreate(source, data);

            // then
            assertThat(event).isNotNull();
            assertThat(aspectRepository.findByEvent(event)).hasSize(expectedAspectSize);
        }
    }

    public static class WifiData2017IntegTest extends EventIntegTest {
        @Test
        public void can_import_Wifi_Data() {
            // given

            test( "naranha;pensssd;2018-10-14 00:00:00;2018-10-14;clssddy.pes1@gmail.com;+393923666666;;;SMS;SI", 6);

            test("Dooén;Kousa;2018-10-14 00:00:00;2018-10-14;kougroaa10.kn@gmail.com;;OVER 21;M;Facebook;", 5);

        }

        private void test(final String data, final int expectedAspectSize) {
            // when
            EventSource source = eventSourceRepository.findOrCreate(EventSourceType.WifiData2017LastAccess, "integ test");
            Event event = eventRepository.findOrCreate(source, data);

            // then
            assertThat(event).isNotNull();
            assertThat(aspectRepository.findByEvent(event)).hasSize(expectedAspectSize);
        }
    }

    public static class NewQuickTapSurveyIntegTest extends EventIntegTest {
        @Test
        public void can_import_Wifi_Data() {
            // given

            test( "2019-04-22 19:00:43 +0200;2019-04-22 19:00:43 +0200;56;ECPIpad10;45,546059999999997;9,3284599999999998;A6tta;Tai;Male;1973-05-23;Yes;No;20139;ana.tde@eni.com;3453666583;ACCONSENTO;ACCONSENTO;ACCONSENTO", 12);

            test("2019-04-22 19:00:43 +0200;2019-04-22 19:00:43 +0200;56;ECPIpad10;45,546059999999997;9,3284599999999998;Ana;Tadi;Male;1973-05-23;Yes;No;20139;andreei@eni.com;;ACCONSENTO;ACCONSENTO;ACCONSENTO", 11);

        }

        private void test(final String data, final int expectedAspectSize) {
            // when
            EventSource source = eventSourceRepository.findOrCreate(EventSourceType.NewQuickTapSurveyCarosello, "integ test");
            Event event = eventRepository.findOrCreate(source, data);

            // then
            assertThat(event).isNotNull();
            assertThat(aspectRepository.findByEvent(event)).hasSize(expectedAspectSize);
        }
    }

}
