package org.incode.eurocommercial.relatio.dom.aspect.subscribers;

import org.apache.isis.applib.AbstractSubscriber;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.incode.eurocommercial.relatio.dom.aspect.AspectCreationService;
import org.incode.eurocommercial.relatio.dom.event.Event;

import javax.inject.Inject;

@DomainService(nature = NatureOfService.DOMAIN)
public class EventCreatedSubscriber extends AbstractSubscriber {

    @EventHandler
    public void on(Event.PersistedEvent event) {
        final Event source = event.getSource();
        aspectCreationService.createAspectsFromEvent(source);
    }

    @Inject
    AspectCreationService aspectCreationService;

}
