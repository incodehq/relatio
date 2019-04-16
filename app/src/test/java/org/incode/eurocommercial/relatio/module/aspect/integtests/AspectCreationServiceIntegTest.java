package org.incode.eurocommercial.relatio.module.aspect.integtests;

import java.util.List;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;

import org.apache.isis.applib.fixturescripts.FixtureScripts;

import org.incode.eurocommercial.relatio.module.aspect.dom.Aspect;
import org.incode.eurocommercial.relatio.module.aspect.dom.AspectCreationService;
import org.incode.eurocommercial.relatio.module.aspect.dom.AspectRepository;
import org.incode.eurocommercial.relatio.module.profile.dom.ProfileRepository;
import org.incode.eurocommercial.relatio.module.base.integtests.RelatioIntegTestAbstract;
import org.incode.eurocommercial.relatio.module.event.dom.Event;
import org.incode.eurocommercial.relatio.module.event.dom.EventRepository;
import org.incode.eurocommercial.relatio.module.event.fixture.WifiprojectAccessiEventFixture;

import static org.assertj.core.api.Assertions.assertThat;

public class AspectCreationServiceIntegTest extends RelatioIntegTestAbstract {

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
