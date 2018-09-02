package org.incode.eurocommercial.relatio.dom.event;

import java.util.List;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.RestrictTo;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.value.Blob;

@DomainService(
        nature = NatureOfService.VIEW_MENU_ONLY
)
@DomainServiceLayout(
        named = "Events",
        menuOrder = "10"
)
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
