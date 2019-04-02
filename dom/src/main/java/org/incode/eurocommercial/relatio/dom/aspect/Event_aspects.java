package org.incode.eurocommercial.relatio.dom.aspect;

import java.util.List;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.CollectionLayout;
import org.apache.isis.applib.annotation.Contributed;
import org.apache.isis.applib.annotation.Mixin;
import org.apache.isis.applib.annotation.SemanticsOf;

import org.incode.eurocommercial.relatio.dom.event.Event;

@Mixin
public class Event_aspects {

    private final Event event;

    public Event_aspects(Event event) {
        this.event = event;
    }


    @Action(semantics= SemanticsOf.SAFE)
    @ActionLayout(contributed=Contributed.AS_ASSOCIATION)
    @CollectionLayout(defaultView="table")
    public List<Aspect> aspects() {
        return aspectRepository.findByEvent(event);

    }

    @Inject
    AspectRepository aspectRepository;

}
