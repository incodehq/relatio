package org.incode.eurocommercial.relatio.dom.profile;

import org.apache.isis.applib.services.wrapper.WrapperFactory;
import org.apache.isis.core.unittestsupport.jmocking.JUnitRuleMockery2;
import static org.assertj.core.api.Assertions.assertThat;
import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.junit.Rule;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class ProfileMenuTest {

    @Rule
    public JUnitRuleMockery2 context = JUnitRuleMockery2.createFor(JUnitRuleMockery2.Mode.INTERFACES_AND_CLASSES);

    @Mock
    ProfileRepository mockProfileRepository;

    @Mock
    WrapperFactory mockWrapperFactory;

    @Test
    public void updateMailChimpProfiles() {
        //given
        Profile profileWithoutConsent = new Profile();
        Profile profileWithConsent = new Profile();

        profileWithoutConsent.setThirdPartyConsent(Boolean.FALSE);
        profileWithConsent.setThirdPartyConsent(Boolean.TRUE);

        List<Profile> profilesList = Arrays.asList(profileWithoutConsent, profileWithConsent);

        context.checking(new Expectations()  {{
            oneOf(mockProfileRepository).listAll();
            will(returnValue(profilesList));
            oneOf(mockWrapperFactory).wrap(profileWithConsent);
            will(returnValue(profileWithConsent));
        }});

        ProfileMenu profileMenu = new ProfileMenu();
        profileMenu.profileRepository = mockProfileRepository;
        profileMenu.wrapperFactory = mockWrapperFactory;

        // when
        List<Profile> returnedProfiles = profileMenu.updateMailChimpProfiles();

        //then
        assertThat(returnedProfiles.size()).isEqualTo(1);
        assertThat(returnedProfiles.get(0)).isEqualTo(profileWithConsent);
    }

}