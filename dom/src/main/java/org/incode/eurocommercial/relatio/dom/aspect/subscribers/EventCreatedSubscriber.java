package org.incode.eurocommercial.relatio.dom.aspect.subscribers;

import com.google.common.eventbus.Subscribe;
import org.apache.isis.applib.AbstractSubscriber;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.incode.eurocommercial.relatio.dom.aspect.AspectCreationService;
import org.incode.eurocommercial.relatio.dom.event.Event;

import javax.inject.Inject;

@DomainService(nature = NatureOfService.DOMAIN)
public class EventCreatedSubscriber extends AbstractSubscriber {

    @Subscribe
    public void on(final Event.EventCreatedEvent domainEvent) {
        final Event source = (Event) domainEvent.getSource();
        aspectCreationService.createAspectsFromEvent(source);
    }

    @Inject
    AspectCreationService aspectCreationService;

}
