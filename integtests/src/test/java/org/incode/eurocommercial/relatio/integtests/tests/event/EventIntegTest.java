package org.incode.eurocommercial.relatio.integtests.tests.event;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import org.apache.isis.applib.fixturescripts.FixtureScripts;
import org.apache.isis.applib.services.clock.ClockService;

import org.incode.eurocommercial.relatio.dom.aspect.AspectRepository;
import org.incode.eurocommercial.relatio.dom.event.Event;
import org.incode.eurocommercial.relatio.dom.event.EventRepository;
import org.incode.eurocommercial.relatio.dom.event.EventSource;
import org.incode.eurocommercial.relatio.dom.event.EventSourceRepository;
import org.incode.eurocommercial.relatio.dom.event.EventSourceType;
import org.incode.eurocommercial.relatio.integtests.tests.RelatioIntegTest;

import static org.assertj.core.api.Assertions.assertThat;

@Ignore
public class EventIntegTest extends RelatioIntegTest {

    @Inject private FixtureScripts fixtureScripts;
    @Inject ClockService clockService;
    @Inject EventRepository eventRepository;
    @Inject EventSourceRepository eventSourceRepository;
    @Inject AspectRepository aspectRepository;


    @Before
    public void setUp() throws Exception {
        // given
//        fixtureScripts.runFixtureScript(new WifiprojectAccessiEventFixture(), null);
//        fixtureScripts.runFixtureScript(new WifiprojectUtentiEventFixture(), null);
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
            assertThat(aspectRepository.findByEvent(event)).hasSize(27);
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




}
