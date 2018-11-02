package org.incode.eurocommercial.relatio.dom.event;

import java.util.List;

import javax.inject.Inject;

import org.joda.time.LocalDateTime;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.clock.ClockService;
import org.apache.isis.applib.services.repository.RepositoryService;

@DomainService(
        nature = NatureOfService.DOMAIN,
        repositoryFor = EventSource.class
)
public class EventSourceRepository {

    @Programmatic
    public List<EventSource> listAll() {
        return repositoryService.allInstances(EventSource.class);
    }

    @Programmatic
    public List<EventSource> findByType(
            final EventSourceType type
    ) {
        return repositoryService.allMatches(
                new QueryDefault<>(
                        EventSource.class,
                        "findByType",
                        "type", type
                ));
    }

    @Programmatic
    public List<EventSource> findByDateTimeRange(
            final LocalDateTime start,
            final LocalDateTime end
    ) {
        return repositoryService.allMatches(
                new QueryDefault<>(
                        EventSource.class,
                        "findByDateTimeRange",
                        "start", start,
                        "end", end
                ));
    }

    @Programmatic
    public EventSource findByTypeAndDateTime(
            final EventSourceType type,
            final LocalDateTime createdAt
    ) {
        return repositoryService.uniqueMatch(
                new QueryDefault<>(
                        EventSource.class,
                        "findByTypeAndCreatedAt",
                        "type", type,
                        "createdAt", createdAt
                ));
    }

    @Programmatic
    public EventSource create(
            final EventSourceType type,
            final String name
    ) {
        return create(type, clockService.nowAsLocalDateTime(), name);
    }

    @Programmatic
    public EventSource create(
            final EventSourceType type,
            final LocalDateTime createdAt,
            final String name) {
        final EventSource eventSource = repositoryService.instantiate(EventSource.class);
        eventSource.setType(type);
        eventSource.setCreatedAt(createdAt);
        eventSource.setName(name);
        repositoryService.persist(eventSource);
        return eventSource;
    }

    @Programmatic
    public EventSource findOrCreate(
            final EventSourceType type,
            final LocalDateTime createdAt,
            final String name
    ) {
        EventSource eventSource = findByTypeAndDateTime(type, createdAt);
        if (eventSource == null) {
            eventSource = create(type, createdAt, name);
        }
        return eventSource;
    }

    @Programmatic
    public EventSource findOrCreate(
            final EventSourceType type,
            final String name
    ) {
        return findOrCreate(type, clockService.nowAsLocalDateTime(), name);
    }

    @Inject RepositoryService repositoryService;
    @Inject ClockService clockService;
}
