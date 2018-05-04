package org.incode.eurocommercial.ecpcrm.dom.event;

import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.Queries;
import javax.jdo.annotations.Query;

import com.google.common.collect.Sets;

import org.apache.isis.applib.annotation.Collection;
import org.apache.isis.applib.annotation.CollectionLayout;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.Where;

import org.incode.eurocommercial.ecpcrm.dom.Profile.Profile;
import org.incode.eurocommercial.ecpcrm.dom.Profile.ProfileRepository;
import org.incode.eurocommercial.ecpcrm.dom.aspect.Aspect;
import org.incode.eurocommercial.ecpcrm.dom.aspect.AspectRepository;
import org.incode.eurocommercial.ecpcrm.dom.aspect.AspectType;

import lombok.Getter;
import lombok.Setter;

@PersistenceCapable(
        identityType = IdentityType.DATASTORE
)
@Queries({
        @Query(
                name = "find", language = "JDOQL",
                value = "SELECT "
                         + "FROM org.incode.eurocommercial.ecpcrm.dom.event.Event "),
        @Query(
                name = "findByDataContains", language = "JDOQL",
                value = "SELECT "
                        + "FROM org.incode.eurocommercial.ecpcrm.dom.event.Event "
                        + "WHERE data.indexOf(:data) >= 0 "),
        @Query(
                name = "findBySource", language = "JDOQL",
                value = "SELECT "
                        + "FROM org.incode.eurocommercial.ecpcrm.dom.event.Event "
                        + "WHERE source == :source "),
        @Query(
                name = "findByData", language = "JDOQL",
                value = "SELECT "
                        + "FROM org.incode.eurocommercial.ecpcrm.dom.event.Event "
                        + "WHERE data == :data "),
        @Query(
                name = "findBySourceAndData", language = "JDOQL",
                value = "SELECT "
                        + "FROM org.incode.eurocommercial.ecpcrm.dom.event.Event "
                        + "WHERE source == :source && data == :data ")
})
@DomainObject(
        editing = Editing.DISABLED
)
public class Event implements Comparable<Event> {

    @Column(allowsNull = "false", jdbcType = "CLOB")
    @Getter @Setter
    private String data;

    @Column(allowsNull = "false")
    @Getter @Setter
    @Property(hidden = Where.REFERENCES_PARENT)
    private EventSource source;

    public void createAspects() {
        Map<AspectType, String> aspectMap = getAspectMap();
        Map<AspectType, String> keyAspectMap = getKeyAspectsFromAspectMap(aspectMap);
        Set<Profile> matchedProfiles = getProfilesFromKeyAspects(keyAspectMap);

        if (matchedProfiles.size() > 1) {
            return;
        }

        Profile profile = matchedProfiles.size() == 0 ? profileRepository.create() : matchedProfiles.iterator().next();

        for (Map.Entry<AspectType, String> entry : aspectMap.entrySet()) {
            aspectRepository.findOrCreate(this, entry.getKey(), entry.getValue(), profile);
        }
    }

    @Programmatic
    private Map<AspectType, String> getAspectMap() {
        /* Retrieve all aspects from event data and filter all empty aspects */
        return source.getType().getParser().toMap(getData()).entrySet().stream()
                .filter(e -> e.getValue() != null && e.getValue().length() > 0)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @Programmatic
    private Map<AspectType, String> getKeyAspectsFromAspectMap(Map<AspectType, String> aspectMap) {
        /* Filter all key aspects from existing aspect map */
        return aspectMap.entrySet().stream()
                .filter(e -> e.getKey().isKey())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @Programmatic
    private Set<Profile> getProfilesFromKeyAspects(Map<AspectType, String> keyAspectMap) {
        /* Retrieve all profiles related to key aspects */
        return keyAspectMap.entrySet().stream()
                .flatMap(entry -> aspectRepository.findByTypeAndValue(entry.getKey(), entry.getValue()).stream())
                .map(Aspect::getProfile)
                .collect(Collectors.toSet());
    }

    public Set<Profile> getConflictingProfiles() {
        if (aspects.size() > 0) {
            return Sets.newHashSet();
        }
        Map<AspectType, String> aspectMap = getAspectMap();
        Map<AspectType, String> keyAspectMap = getKeyAspectsFromAspectMap(aspectMap);

        return getProfilesFromKeyAspects(keyAspectMap);
    }

    public boolean hideConflictingProfiles() {
        return aspects.size() == 0;
    }

    @Override
    public int compareTo(final Event other) {
        // For now, would be better to compare using a key
        return Comparator.comparing(Event::getSource).thenComparing(Event::getData).compare(this, other);
    }

    @Persistent(mappedBy = "event", dependentElement = "false")
    @Collection
    @CollectionLayout(defaultView = "table")
    @Getter @Setter
    private SortedSet<Aspect> aspects = new TreeSet<>();

    @Inject private AspectRepository aspectRepository;

    @Inject private ProfileRepository profileRepository;
}
