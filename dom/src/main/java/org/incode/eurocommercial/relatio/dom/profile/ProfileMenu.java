package org.incode.eurocommercial.relatio.dom.profile;

import java.util.List;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.RestrictTo;
import org.apache.isis.applib.annotation.SemanticsOf;

import org.incode.eurocommercial.relatio.dom.aspect.AspectType;

@DomainService(
        nature = NatureOfService.VIEW_MENU_ONLY
)
@DomainServiceLayout(
        named = "Profiles",
        menuOrder = "20"
)
public class ProfileMenu {

    @Action(
            semantics = SemanticsOf.SAFE,
            restrictTo = RestrictTo.PROTOTYPING
    )
    public List<Profile> allUpdatedProfiles() {
        for (Profile profile : profileRepository.listAll()) {
            profile.updateFromAspects();
        }

        return profileRepository.listAll();
    }

    @Action(
            semantics = SemanticsOf.SAFE,
            restrictTo = RestrictTo.PROTOTYPING
    )
    public List<Profile> allProfiles() {
        return profileRepository.listAll();
    }

    @Action(
            semantics = SemanticsOf.SAFE
    )
    public List<Profile> filterProfiles(
            final @Parameter(optionality = Optionality.OPTIONAL) Profile.Gender gender,
            final boolean withEmailAddress,
            final boolean withCellPhoneNumber,
            final boolean withFacebookAccount
    ) {
        List<Profile> foundProfiles = profileRepository.listAll();

        if (gender != null) {
            List<Profile> profilesWithGender = profileRepository.findByAspectTypeAndValue(AspectType.Gender, gender.toString());
            foundProfiles.retainAll(profilesWithGender);
        }

        if (withEmailAddress) {
            List<Profile> profilesWithEmailAddress = profileRepository.findByAspectType(AspectType.EmailAccount);
            foundProfiles.retainAll(profilesWithEmailAddress);
        }

        if (withCellPhoneNumber) {
            List<Profile> profilesWithCellPhoneNumber = profileRepository.findByAspectType(AspectType.CellPhoneNumber);
            foundProfiles.retainAll(profilesWithCellPhoneNumber);
        }

        if (withFacebookAccount) {
            List<Profile> profilesWithFacebookAccount = profileRepository.findByAspectType(AspectType.FacebookAccount);
            foundProfiles.retainAll(profilesWithFacebookAccount);
        }

        return foundProfiles;
    }

    @Inject ProfileRepository profileRepository;
}
