package org.incode.eurocommercial.ecpcrm.dom.event;

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
import org.apache.isis.applib.value.Blob;

import org.incode.eurocommercial.ecpcrm.dom.aspect.AspectRepository;
import org.incode.eurocommercial.ecpcrm.dom.aspect.AspectType;
import org.incode.eurocommercial.ecpcrm.dom.profile.Profile;
import org.incode.eurocommercial.ecpcrm.dom.profile.ProfileRepository;

@DomainService(
        nature = NatureOfService.VIEW_MENU_ONLY
)
@DomainServiceLayout(
        named = "Events",
        menuOrder = "99"
)
public class EventMenu {

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

    @Action(
            semantics = SemanticsOf.SAFE,
            restrictTo = RestrictTo.PROTOTYPING
    )
    public List<Event> allEvents() {
        return eventRepository.listAll();
    }

    @Action(
            semantics = SemanticsOf.SAFE,
            restrictTo = RestrictTo.PROTOTYPING
    )
    public List<EventSource> allEventSources() {
        return eventSourceRepository.listAll();
    }

    public List<Event> allEventsWithConflicts() {
        return eventRepository.allEventsWithoutAspects();
    }

    @Action(
            semantics = SemanticsOf.SAFE
    )
    public EventSource uploadData(
            final EventSourceType type,
            final Blob blob,
            final String consentInformation
    ) {
        return type.parseBlob(blob, eventRepository, eventSourceRepository);
    }

    @Inject EventRepository eventRepository;
    @Inject EventSourceRepository eventSourceRepository;
    @Inject ProfileRepository profileRepository;
    @Inject AspectRepository aspectRepository;

}
