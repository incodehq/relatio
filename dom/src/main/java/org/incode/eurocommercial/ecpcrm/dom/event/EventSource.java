package org.incode.eurocommercial.ecpcrm.dom.event;

import java.util.Comparator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.Queries;
import javax.jdo.annotations.Query;

import com.google.common.collect.Lists;

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
                        + "WHERE createdAt >= :begin && createdAt <= :end "),
        @Query(
                name = "findByTypeAndCreatedAt", language = "JDOQL",
                value = "SELECT "
                        + "FROM org.incode.eurocommercial.ecpcrm.dom.event.EventSource "
                        + "WHERE createdAt == :createdAt "
                        + "&& type == :type ")
})
@DomainObject(
        editing = Editing.DISABLED
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

    @Column(allowsNull = "false")
    @Getter @Setter
    private EventSource.Status status;

    @Persistent(mappedBy = "source", dependentElement = "false")
    @Collection()
    @CollectionLayout(defaultView = "table")
    @Getter @Setter
    private SortedSet<Event> events = new TreeSet<>();

    @CollectionLayout(defaultView = "table")
    public List<Event> getEventsWithConflicts() {
        return Lists.newArrayList(events).stream()
                .filter(event -> event.getAspects().size() == 0)
                .collect(Collectors.toList());
    }

    public boolean hideEventsWithConflicts() {
        return getEventsWithConflicts().size() == 0;
    }

    @Override
    public int compareTo(final EventSource other) {
        // For now, would be better to compare using a key
        return Comparator.comparing(EventSource::getType).thenComparing(EventSource::getCreatedAt).compare(this, other);
    }

    public enum Status {
        SUCCESS,
        FAILURE
    }

}
