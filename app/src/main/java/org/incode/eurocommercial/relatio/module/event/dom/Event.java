package org.incode.eurocommercial.relatio.module.event.dom;

import java.util.Comparator;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.DatastoreIdentity;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Queries;
import javax.jdo.annotations.Query;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.Where;
import org.apache.isis.applib.services.eventbus.ActionDomainEvent;
import org.apache.isis.applib.services.eventbus.ObjectPersistedEvent;

import lombok.Getter;
import lombok.Setter;

@PersistenceCapable(
        identityType = IdentityType.DATASTORE
)
@DatastoreIdentity(
        strategy = IdGeneratorStrategy.IDENTITY,
        column = "id")
@Queries({
        @Query(
                name = "findByDataContains", language = "JDOQL",
                value = "SELECT "
                        + "FROM org.incode.eurocommercial.relatio.module.event.dom.Event "
                        + "WHERE data.indexOf(:data) >= 0 "),
        @Query(
                name = "findBySource", language = "JDOQL",
                value = "SELECT "
                        + "FROM org.incode.eurocommercial.relatio.module.event.dom.Event "
                        + "WHERE source == :source "),
        @Query(
                name = "findByData", language = "JDOQL",
                value = "SELECT "
                        + "FROM org.incode.eurocommercial.relatio.module.event.dom.Event "
                        + "WHERE data == :data "),
        @Query(
                name = "findBySourceAndData", language = "JDOQL",
                value = "SELECT "
                        + "FROM org.incode.eurocommercial.relatio.module.event.dom.Event "
                        + "WHERE source == :source && data == :data ")
})
@DomainObject(
        editing = Editing.DISABLED,
        persistedLifecycleEvent=Event.PersistedEvent.class,
        objectType = "Event"
)
public class Event implements Comparable<Event> {

    public Event(
            final EventSource source,
            final String data) {
        this.source = source;
        this.data = data;
    }

    public String title() {
        return source.title();
    }

    @Getter
    @Setter
    @Column(allowsNull = "false", jdbcType = "CLOB")
    private String data;

    @Getter
    @Setter
    @Property(hidden = Where.REFERENCES_PARENT)
    @Column(allowsNull = "false", name = "eventSourceId")
    private EventSource source;

    @Override
    public int compareTo(final Event other) {
        // For now, would be better to compare using a key
        return Comparator.comparing(Event::getSource).thenComparing(Event::getData).compare(this, other);
    }

    @Action(domainEvent = Event.UpdatedEvent.class)
    public Event update() {
        //Does nothing except triggering the event
        return this;
    }

    public static class PersistedEvent extends ObjectPersistedEvent<Event> { }

    public static class UpdatedEvent extends ActionDomainEvent<Event> { }

}
