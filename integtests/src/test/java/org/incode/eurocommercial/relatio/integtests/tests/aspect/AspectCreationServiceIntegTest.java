package org.incode.eurocommercial.relatio.integtests.tests.aspect;

import org.apache.isis.applib.fixturescripts.FixtureScripts;
import static org.assertj.core.api.Assertions.assertThat;
import org.incode.eurocommercial.relatio.dom.aspect.Aspect;
import org.incode.eurocommercial.relatio.dom.aspect.AspectCreationService;
import org.incode.eurocommercial.relatio.dom.aspect.AspectRepository;
import org.incode.eurocommercial.relatio.dom.event.Event;
import org.incode.eurocommercial.relatio.dom.event.EventRepository;
import org.incode.eurocommercial.relatio.dom.profile.ProfileRepository;
import org.incode.eurocommercial.relatio.fixture.dom.event.WifiprojectAccessiEventFixture;
import org.incode.eurocommercial.relatio.integtests.tests.RelatioIntegTest;
import org.junit.Before;
import org.junit.Test;

import javax.inject.Inject;
import java.util.List;

public class AspectCreationServiceIntegTest extends RelatioIntegTest {

    @Inject
    private FixtureScripts fixtureScripts;

    @Inject
    EventRepository eventRepository;

    @Inject
    AspectRepository aspectRepository;

    @Inject
    AspectCreationService aspectCreationService;

    @Inject
    ProfileRepository profileRepository;

    @Before
    public void setUp() throws Exception {
        // given
        fixtureScripts.runFixtureScript(new WifiprojectAccessiEventFixture(), null);
    }

    public static class AspectCreationService_Test extends AspectCreationServiceIntegTest{
        @Test
        public void createAspectsFromEvent_happy_case(){
            //given
            Event event = eventRepository.listAll().get(0);

            //when
            aspectCreationService.createAspectsFromEvent(event);

            //then
            List<Aspect> aspectsList = aspectRepository.findByEvent(event);
            assertThat(aspectsList).isNotEmpty();
            assertThat(aspectsList.size()).isEqualTo(3);
        }
    }
}
