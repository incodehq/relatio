package org.incode.eurocommercial.ecpcrm.dom.Profile;

import java.util.UUID;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;

@DomainService(
        nature = NatureOfService.DOMAIN,
        repositoryFor = Profile.class
)
public class ProfileRepository {

    @Programmatic
    public java.util.List<Profile> listAll() {
        return container.allInstances(Profile.class);
    }

    @Programmatic
    public java.util.List<Profile> findByLastNameContains(
            final String lastName
    ) {
        return container.allMatches(
                new org.apache.isis.applib.query.QueryDefault<>(
                        Profile.class,
                        "findByLastNameContains",
                        "lastName", lastName));
    }

    @Programmatic
    public Profile create() {
        final Profile profile = container.newTransientInstance(Profile.class);
        profile.setUuid(UUID.randomUUID().toString());
        container.persistIfNotAlready(profile);
        return profile;
    }

    @javax.inject.Inject
    org.apache.isis.applib.DomainObjectContainer container;
}
