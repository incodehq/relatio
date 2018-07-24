package org.incode.eurocommercial.ecpcrm.dom.aspect;

import java.util.List;

import javax.inject.Inject;

import org.joda.time.LocalDateTime;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.repository.RepositoryService;

import org.incode.eurocommercial.ecpcrm.dom.profile.Profile;
import org.incode.eurocommercial.ecpcrm.dom.event.Event;

@DomainService(
        nature = NatureOfService.DOMAIN,
        repositoryFor = Aspect.class
)
public class AspectRepository  {

    @Programmatic
    public List<Aspect> listAll() {
        return repositoryService.allInstances(Aspect.class);
    }

    @Programmatic
    public List<Aspect> findByTypeAndValue(
            final AspectType type,
            final String value
    ) {
        return repositoryService.allMatches(
                new QueryDefault<>(
                        Aspect.class,
                        "findByTypeAndValue",
                        "type", type,
                        "value", value));
    }

    @Programmatic
    public List<Aspect> findByEvent(
            final Event event
    ) {
        return repositoryService.allMatches(
                new QueryDefault<>(
                        Aspect.class,
                        "findByEvent",
                        "event", event));
    }

    @Programmatic
    public Aspect findByEventAndType(
            final Event event,
            final AspectType type
    ) {
        return repositoryService.uniqueMatch(
                new QueryDefault<>(
                        Aspect.class,
                        "findByEventAndType",
                        "event", event,
                        "type", type));
    }

    @Programmatic
    public Aspect create(
            final Profile profile, final Event event,
            final AspectType type,
            final String value,
            final LocalDateTime collectedAt
    ) {
        final Aspect aspect = repositoryService.instantiate(Aspect.class);
        aspect.setEvent(event);
        aspect.setType(type);
        aspect.setValue(value);
        aspect.setProfile(profile);
        aspect.setCollectedAt(collectedAt);

        repositoryService.persist(aspect);
        return aspect;
    }

    @Programmatic
    public Aspect findOrCreate(
            final Profile profile, final Event event,
            final AspectType type,
            final String value,
            final LocalDateTime collectedAt
    ) {
        Aspect aspect = findByEventAndType(event, type);
        if (aspect == null) {
            aspect = create(profile, event, type, value, collectedAt);
        }
        return aspect;
    }

    @Inject RepositoryService repositoryService;
}
