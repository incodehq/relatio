package org.incode.eurocommercial.relatio.dom.event;

import com.google.common.collect.Sets;
import lombok.Getter;
import lombok.Setter;
import org.apache.isis.applib.annotation.Collection;
import org.apache.isis.applib.annotation.CollectionLayout;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.Where;
import org.incode.eurocommercial.relatio.dom.aspect.Aspect;
import org.incode.eurocommercial.relatio.dom.aspect.AspectRepository;
import org.incode.eurocommercial.relatio.dom.aspect.AspectType;
import org.incode.eurocommercial.relatio.dom.profile.Profile;
import org.incode.eurocommercial.relatio.dom.profile.ProfileRepository;
import org.incode.eurocommercial.relatio.dom.service.EmailCleaningService;
import org.joda.time.LocalDateTime;

import javax.inject.Inject;
import javax.jdo.annotations.Column;
import javax.jdo.annotations.DatastoreIdentity;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.Queries;
import javax.jdo.annotations.Query;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;


@PersistenceCapable(
        identityType = IdentityType.DATASTORE
)
@DatastoreIdentity(
        strategy = IdGeneratorStrategy.IDENTITY,
        column = "id")
@Queries({
        @Query(
                name = "findByDataContains", language = "JDOQL",
                value = "SELECT "
                        + "FROM org.incode.eurocommercial.relatio.dom.event.Event "
                        + "WHERE data.indexOf(:data) >= 0 "),
        @Query(
                name = "findBySource", language = "JDOQL",
                value = "SELECT "
                        + "FROM org.incode.eurocommercial.relatio.dom.event.Event "
                        + "WHERE source == :source "),
        @Query(
                name = "findByData", language = "JDOQL",
                value = "SELECT "
                        + "FROM org.incode.eurocommercial.relatio.dom.event.Event "
                        + "WHERE data == :data "),
        @Query(
                name = "findBySourceAndData", language = "JDOQL",
                value = "SELECT "
                        + "FROM org.incode.eurocommercial.relatio.dom.event.Event "
                        + "WHERE source == :source && data == :data "),
        @Query(
                name = "finByAspectCount", language = "JDOQL",
                value = "SELECT "
                        + "FROM org.incode.eurocommercial.relatio.dom.event.Event "
                        + "WHERE aspects.size() == :count "),
        @Query(
                name = "findEventsWithConflicts", language = "JDOQL",
                value = "SELECT "
                        + "FROM org.incode.eurocommercial.relatio.dom.event.Event "
                        + "WHERE this.aspects.contains(aspect) && aspect.profile == null && aspect.type.key "
                        + "VARIABLES org.incode.eurocommercial.relatio.dom.aspect.Aspect aspect "
        )
})
@DomainObject(
        editing = Editing.DISABLED
)
public class Event implements Comparable<Event> {

    public String title() {
        return source.title();
    }

    @Column(allowsNull = "false", jdbcType = "CLOB")
    @Getter
    @Setter
    private String data;

    @Column(allowsNull = "false", name = "eventSourceId")
    @Getter
    @Setter
    @Property(hidden = Where.REFERENCES_PARENT)
    private EventSource source;


    @Programmatic
    public void cleanEmailOnAspectMap(final Map<AspectType, String> aspectMap) {
        if (aspectMap.containsKey(AspectType.EmailAccount)) {
            String cleanedEmailAddress = emailCleaningService.process(aspectMap.get(AspectType.EmailAccount));
            aspectMap.put(AspectType.EmailAccount, cleanedEmailAddress);
        }
    }

    @Programmatic
    public void createAspects() {
        Map<AspectType, String> aspectMap = getAspectMap();
        /* filter email on aspectMap */
        cleanEmailOnAspectMap(aspectMap);

        Map<AspectType, String> keyAspectMap = getKeyAspectsFromAspectMap(aspectMap);
        SortedSet<LocalDateTime> collectionDates = getCollectionDatesFromAspectMap(aspectMap);
        Set<Profile> matchedProfiles = getProfilesFromKeyAspects(keyAspectMap);
        Profile profile = null;

        if (matchedProfiles.isEmpty()) {
            if (keyAspectMap.size() > 0) {
                profile = profileRepository.create();
            }
        } else if (matchedProfiles.size() == 1) {
            profile = matchedProfiles.iterator().next();
        }

        for (Map.Entry<AspectType, String> entry : aspectMap.entrySet()) {
            LocalDateTime collectionDate = collectionDates.size() == 0 ? null : collectionDates.last();
            Aspect aspect = aspectRepository.findOrCreate(profile, this, entry.getKey(), entry.getValue(), collectionDate);
            aspects.add(aspect);
        }
    }

    private SortedSet<LocalDateTime> getCollectionDatesFromAspectMap(final Map<AspectType, String> aspectMap) {
        return aspectMap.entrySet().stream()
                .filter(e -> e.getKey().isCollectionDate())
                .map(e -> LocalDateTime.parse(e.getValue()))
                .collect(Collectors.toCollection(TreeSet::new));
    }

    @Programmatic
    private Map<AspectType, String> getAspectMap() {
        /* Retrieve all aspects from event data and filter all empty aspects */
        final Map<AspectType, String> map = getSource().getType().getParser().toMap(getData());
        return map.entrySet().stream()
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

    @CollectionLayout(defaultView = "table")
    public Set<Profile> getConflictingProfiles() {
        if (aspects.size() > 0) {
            return Sets.newHashSet();
        }
        Map<AspectType, String> aspectMap = getAspectMap();
        Map<AspectType, String> keyAspectMap = getKeyAspectsFromAspectMap(aspectMap);

        return getProfilesFromKeyAspects(keyAspectMap);
    }

    public boolean hideConflictingProfiles() {
        return aspects.size() > 0;
    }

    @Persistent(mappedBy = "event", dependentElement = "false")
    @Collection
    @CollectionLayout(defaultView = "table")
//    @Getter @Setter
    private SortedSet<Aspect> aspects = new TreeSet<>();

    public SortedSet<Aspect> getAspects() {
        return aspects;
    }

    public void setAspects(final SortedSet<Aspect> aspects) {
        this.aspects = aspects;
    }

    @Override
    public int compareTo(final Event other) {
        // For now, would be better to compare using a key
        return Comparator.comparing(Event::getSource).thenComparing(Event::getData).compare(this, other);
    }

    @Inject
    private AspectRepository aspectRepository;

    @Inject
    private ProfileRepository profileRepository;

    @Inject
    private EmailCleaningService emailCleaningService;
}
