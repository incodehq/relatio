package org.incode.eurocommercial.ecpcrm.dom.event;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;

@DomainService(
        nature = NatureOfService.DOMAIN,
        repositoryFor = ImportEvent.class
)
public class ImportEventRepository {

    @Programmatic
    public java.util.List<ImportEvent> listAll() {
        return container.allInstances(ImportEvent.class);
    }

    @Programmatic
    public ImportEvent findByData(
            final String data
    ) {
        return container.uniqueMatch(
                new org.apache.isis.applib.query.QueryDefault<>(
                        ImportEvent.class,
                        "findByData",
                        "data", data));
    }

    @Programmatic
    public java.util.List<ImportEvent> findByDataContains(
            final String data
    ) {
        return container.allMatches(
                new org.apache.isis.applib.query.QueryDefault<>(
                        ImportEvent.class,
                        "findByDataContains",
                        "data", data));
    }

    @Programmatic
    public ImportEvent create(final String data) {
        final ImportEvent importEvent = container.newTransientInstance(ImportEvent.class);
        importEvent.setData(data);
        container.persistIfNotAlready(importEvent);
        return importEvent;
    }

    @Programmatic
    public ImportEvent findOrCreate(
            final String data
    ) {
        ImportEvent importEvent = findByData(data);
        if (importEvent == null) {
            importEvent = create(data);
        }
        return importEvent;
    }

    @javax.inject.Inject
    org.apache.isis.applib.DomainObjectContainer container;
}
