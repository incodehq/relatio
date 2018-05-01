package org.incode.eurocommercial.ecpcrm.dom.event;

import java.util.Comparator;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.jdo.annotations.Column;
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

import lombok.Getter;
import lombok.Setter;

@PersistenceCapable(
        identityType = IdentityType.DATASTORE
)
@Queries({
        @Query(
                name = "findByType", language = "JDOQL",
                value = "SELECT "
                        + "FROM org.incode.eurocommercial.ecpcrm.dom.event.EventSource "
                        + "WHERE type == :type "),
        @Query(
                name = "findByDateTimeRange", language = "JDOQL",
                value = "SELECT "
                        + "FROM org.incode.eurocommercial.ecpcrm.dom.event.EventSource "
                        + "WHERE dateTime >= :begin && dateTime <= :end "),
        @Query(
                name = "findByTypeAndDateTime", language = "JDOQL",
                value = "SELECT "
                        + "FROM org.incode.eurocommercial.ecpcrm.dom.event.EventSource "
                        + "WHERE dateTime == :dateTime "
                        + "&& type == :type ")
})
@DomainObject(
        editing = Editing.DISABLED
)
public class EventSource implements Comparable<EventSource> {

    public String title() {
        return "[" + type + "]" + " - " + createdOn;
    }

    @Column(allowsNull = "false")
    @Getter @Setter
    private EventSourceType type;

    @Column(allowsNull = "false")
    @Getter @Setter
    private LocalDateTime createdOn;

    @Column(allowsNull = "false")
    @Getter @Setter
    private EventSource.Status status;

    @Persistent(mappedBy = "source", dependentElement = "false")
    @Collection()
    @CollectionLayout(defaultView = "table")
    @Getter @Setter
    private SortedSet<Event> events = new TreeSet<>();

    @Override
    public int compareTo(final EventSource other) {
        // For now, would be better to compare using a key
        return Comparator.comparing(EventSource::getType).thenComparing(EventSource::getCreatedOn).compare(this, other);
    }

    public enum Status {
        SUCCESS,
        FAILURE
    }



}
