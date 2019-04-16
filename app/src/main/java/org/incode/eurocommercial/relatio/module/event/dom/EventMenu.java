package org.incode.eurocommercial.relatio.module.event.dom;

import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.value.Blob;

import javax.inject.Inject;
import java.util.List;

@DomainService(
        nature = NatureOfService.VIEW_MENU_ONLY
)
@DomainServiceLayout(
        named = "Events",
        menuOrder = "10"
)
@DomainObject(objectType = "EventMenu")
public class EventMenu {
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
        return eventRepository.allEventsWithConflicts();
    }

    @Action(
            semantics = SemanticsOf.SAFE
    )
    public EventSource uploadData(
            final EventSourceType type,
            final Blob blob
    ) {
        return type.parseBlob(blob, eventRepository, eventSourceRepository);
    }

    @Inject EventRepository eventRepository;
    @Inject EventSourceRepository eventSourceRepository;
}
