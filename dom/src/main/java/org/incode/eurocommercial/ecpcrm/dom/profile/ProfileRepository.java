package org.incode.eurocommercial.ecpcrm.dom.profile;

import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.services.repository.RepositoryService;

@DomainService(
        nature = NatureOfService.DOMAIN,
        repositoryFor = Profile.class
)
public class ProfileRepository {

    @Programmatic
    public List<Profile> listAll() {
        return repositoryService.allInstances(Profile.class);
    }

    @Programmatic
    public List<Profile> findByLastNameContains(
            final String lastName
    ) {
        return repositoryService.allMatches(
                new org.apache.isis.applib.query.QueryDefault<>(
                        Profile.class,
                        "findByLastNameContains",
                        "lastName", lastName));
    }

    @Programmatic
    public Profile create() {
        final Profile profile = repositoryService.instantiate(Profile.class);
        profile.setUuid(UUID.randomUUID().toString());
        repositoryService.persist(profile);
        return profile;
    }

    @Inject RepositoryService repositoryService;
}
