package org.incode.eurocommercial.relatio.dom.aspect.subscribers;

import javax.inject.Inject;

import org.axonframework.eventhandling.annotation.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.isis.applib.AbstractSubscriber;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;

import org.incode.eurocommercial.relatio.dom.aspect.AspectCreationService;
import org.incode.eurocommercial.relatio.dom.event.Event;

@DomainService(nature = NatureOfService.DOMAIN)
public class EventUpdatedSubscriber extends AbstractSubscriber {

    private static final Logger LOG = LoggerFactory.getLogger(EventUpdatedSubscriber.class);

    @EventHandler
    public void on(Event.UpdatedEvent event) {
        if (event.getEventPhase().isExecuting()) {
            final Event source = event.getSource();
            LOG.debug("Event: {}", source.getData());
            LOG.debug("EventSource: {}", source.getSource());
            aspectCreationService.createAspectsFromEvent(source);
        }
    }

    @Inject
    AspectCreationService aspectCreationService;

}
