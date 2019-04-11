package org.incode.eurocommercial.relatio.dom.event;

import java.util.Comparator;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.inject.Inject;
import javax.jdo.annotations.Column;
import javax.jdo.annotations.DatastoreIdentity;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.Queries;
import javax.jdo.annotations.Query;

import org.joda.time.LocalDateTime;

import org.apache.isis.applib.annotation.Collection;
import org.apache.isis.applib.annotation.CollectionLayout;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.services.wrapper.WrapperFactory;

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
                name = "findByType", language = "JDOQL",
                value = "SELECT "
                        + "FROM org.incode.eurocommercial.relatio.dom.event.EventSource "
                        + "WHERE type == :type "),
        @Query(
                name = "findByDateTimeRange", language = "JDOQL",
                value = "SELECT "
                        + "FROM org.incode.eurocommercial.relatio.dom.event.EventSource "
                        + "WHERE createdAt >= :begin && createdAt <= :end "),
        @Query(
                name = "findByTypeAndCreatedAt", language = "JDOQL",
                value = "SELECT "
                        + "FROM org.incode.eurocommercial.relatio.dom.event.EventSource "
                        + "WHERE createdAt == :createdAt "
                        + "&& type == :type ")
})
@DomainObject(
        editing = Editing.DISABLED,
        objectType = "EventSource"
)
public class EventSource implements Comparable<EventSource> {

    public String title() {
        return "[" + type + "]" + " - " + createdAt;
    }

    @Column(allowsNull = "false")
    @Getter @Setter
    private EventSourceType type;

    @Column(allowsNull = "false")
    @Getter @Setter
    private LocalDateTime createdAt;

    @Column(allowsNull = "true", length = 260)
    @Getter @Setter
    private String name;

    @Column(allowsNull = "false")
    @Getter @Setter
    private EventSource.Status status;

    @Persistent(mappedBy = "source", dependentElement = "false")
    @Collection()
    @CollectionLayout(defaultView = "table")
    @Getter @Setter
    private SortedSet<Event> events = new TreeSet<>();

    /*  TODO: REL-12?
    @CollectionLayout(defaultView = "table")
    public List<Event> getEventsWithConflicts() {
        return null;
    }

    public boolean hideEventsWithConflicts() {
        return true;
    }
    */

    @Override
    public int compareTo(final EventSource other) {
        // For now, would be better to compare using a key
        return Comparator.comparing(EventSource::getType).thenComparing(EventSource::getCreatedAt).compare(this, other);
    }

    public enum Status {
        SUCCESS,
        FAILURE
    }

    public EventSource updateEvents() {

        getEvents().stream().forEach(e -> wrapperFactory.wrap(e).update());
        return this;

    }

    @Inject
    private WrapperFactory wrapperFactory;

}
