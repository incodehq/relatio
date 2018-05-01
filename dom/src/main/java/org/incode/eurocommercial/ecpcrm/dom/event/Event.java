package org.incode.eurocommercial.ecpcrm.dom.event;

import java.util.AbstractMap;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
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

import org.joda.time.LocalDateTime;

import org.apache.isis.applib.annotation.Collection;
import org.apache.isis.applib.annotation.CollectionLayout;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.Editing;
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

    @Column(allowsNull = "false")
    @Getter @Setter
    private LocalDateTime createdOn;

    @Column(allowsNull = "false")
    @Getter @Setter
    private String data;

    @Column(allowsNull = "false")
    @Getter @Setter
    @Property(hidden = Where.REFERENCES_PARENT)
    private EventSource source;

    public void createAspects() {

        // Filter the empty aspects
        Map<AspectType, String> aspectMap =
                source.getType().getParser().toMap(getData()).entrySet()
                        .stream()
                        .map( e -> new AbstractMap.SimpleEntry<>(e.getKey(), e.getValue()))
                        .filter(e -> e.getValue() != null && e.getValue().length() > 0)
                        .collect(Collectors.toMap(
                                p -> p.getKey(),
                                p -> p.getValue()));
        // Go search for the profile
        Set<Profile> matchedProfiles = new HashSet<>();

        // Filter key aspects
        Map<AspectType, String> keyAspectMap =
                aspectMap.entrySet()
                        .stream()
                        .map( e -> new AbstractMap.SimpleEntry<>(e.getKey(), e.getValue()))
                        .filter(e -> e.getKey().isKey())
                        .collect(Collectors.toMap(
                                e -> e.getKey(),
                                e -> e.getValue()));
        // lop up key aspects
        for (Map.Entry<AspectType, String> entry : keyAspectMap.entrySet()) {
            final List<Aspect> existingSimilarAspects = aspectRepository.findByTypeAndValue(entry.getKey(), entry.getValue());
            if (existingSimilarAspects.size() > 1){
                throw new IllegalStateException("OMG we found more then one profile with the same aspect");
            }
            if (existingSimilarAspects.size() == 1) {
                matchedProfiles.add(existingSimilarAspects.get(0).getProfile());
            }

        }

        Profile profile = matchedProfiles.size() == 0 ? profileRepository.create() : matchedProfiles.iterator().next();

        for (Map.Entry<AspectType, String> entry : aspectMap.entrySet()) {
            aspectRepository.findOrCreate(this, entry.getKey(), entry.getValue(), profile);
        }
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
