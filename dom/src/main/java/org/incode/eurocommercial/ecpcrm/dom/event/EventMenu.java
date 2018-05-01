package org.incode.eurocommercial.ecpcrm.dom.event;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.RestrictTo;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.value.Blob;

import org.incode.eurocommercial.ecpcrm.dom.Profile.Profile;
import org.incode.eurocommercial.ecpcrm.dom.Profile.ProfileRepository;

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
    public java.util.List<Profile> allUpdatedProfiles() {

        for (Profile profile : profileRepository.listAll()) {
            profile.updateFromAspects();
        }

        return profileRepository.listAll();
    }

    @Action(
            semantics = SemanticsOf.SAFE,
            restrictTo = RestrictTo.PROTOTYPING
    )
    public java.util.List<Profile> allProfiles() {
        return profileRepository.listAll();
    }

    @Action(
            semantics = SemanticsOf.SAFE,
            restrictTo = RestrictTo.PROTOTYPING
    )
    public java.util.List<Event> allEvents() {
        return eventRepository.listAll();
    }

    @Action(
            semantics = SemanticsOf.SAFE,
            restrictTo = RestrictTo.PROTOTYPING
    )
    public java.util.List<EventSource> allEventSources() {
        return eventSourceRepository.listAll();
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

    @javax.inject.Inject
    EventRepository eventRepository;

    @javax.inject.Inject
    EventSourceRepository eventSourceRepository;

    @javax.inject.Inject
    ProfileRepository profileRepository;

}
