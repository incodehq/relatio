package org.incode.eurocommercial.ecpcrm.integtests.tests.event;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;

import org.apache.isis.applib.fixturescripts.FixtureScripts;
import org.apache.isis.applib.services.clock.ClockService;

import org.incode.eurocommercial.ecpcrm.dom.aspect.AspectRepository;
import org.incode.eurocommercial.ecpcrm.dom.event.Event;
import org.incode.eurocommercial.ecpcrm.dom.event.EventRepository;
import org.incode.eurocommercial.ecpcrm.dom.event.EventSource;
import org.incode.eurocommercial.ecpcrm.dom.event.EventSourceRepository;
import org.incode.eurocommercial.ecpcrm.dom.event.EventSourceType;
import org.incode.eurocommercial.ecpcrm.fixture.dom.event.AccessiEventFixture;
import org.incode.eurocommercial.ecpcrm.fixture.dom.event.UtentiEventFixture;
import org.incode.eurocommercial.ecpcrm.integtests.tests.EcpCrmIntegTest;

import static org.assertj.core.api.Assertions.assertThat;

public class EventIntegTest extends EcpCrmIntegTest {

    @Inject private FixtureScripts fixtureScripts;
    @Inject ClockService clockService;
    @Inject EventRepository eventRepository;
    @Inject EventSourceRepository eventSourceRepository;
    @Inject AspectRepository aspectRepository;


    @Before
    public void setUp() throws Exception {
        // given
        fixtureScripts.runFixtureScript(new AccessiEventFixture(), null);
        fixtureScripts.runFixtureScript(new UtentiEventFixture(), null);
    }

    public static class ImportAccessi extends EventIntegTest {
        @Test
        public void can_import_accessi() {
            // given
            String data = "19550995;ebegg4@fastcompany.com;81:B9:C0:9C:14:BB;2018-01-02 10:47:34;2018-01-02 11:14:04;FB;Lost-Service";

            // when
            EventSource source = eventSourceRepository.findByType(EventSourceType.WifiProjects_Accessi_Csv).get(0);
            Event event = eventRepository.findOrCreate(source, data);

            // then
            assertThat(event).isNotNull();
            assertThat(event.getAspects()).isEmpty();
        }
    }

    public static class ImportUtenti extends EventIntegTest {
        @Test
        public void can_import_utenti() {
            // given
            String data = "Luca;Rendina;28/03/2017;rendina.lu@hotmail.it;;Facebook;userurl=&userid=1431369723582333&fullname=Luca Rendina&first_name=Luca&last_name=Rendina&birthday=&age_range=max:20,min:18&gender=male";

            // when
            EventSource source = eventSourceRepository.findOrCreate(EventSourceType.WifiProjects_Utenti_Csv);
            Event event = eventRepository.findOrCreate(source, data);

            // then
            assertThat(event).isNotNull();
            assertThat(event.getAspects()).hasSize(5);
        }
    }




}
