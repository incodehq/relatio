package org.incode.eurocommercial.relatio.dom.aspect;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.joda.time.LocalDateTime;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;

import org.incode.eurocommercial.relatio.dom.event.Event;
import org.incode.eurocommercial.relatio.dom.profile.Profile;
import org.incode.eurocommercial.relatio.dom.profile.ProfileRepository;
import org.incode.eurocommercial.relatio.dom.service.EmailCleaningService;

@DomainService(nature = NatureOfService.DOMAIN)
public class AspectCreationService {

//    private static final Logger LOG = LoggerFactory.getLogger(EventPersistedSubscriber.class);


    @Programmatic
    public void createAspectsFromEvent(Event event){
        Map<AspectType, String> aspectMap = getAspectMap(event);
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

        SortedSet<Aspect> aspects = new TreeSet<>();

        for (Map.Entry<AspectType, String> entry : aspectMap.entrySet()) {
            LocalDateTime collectionDate = collectionDates.size() == 0 ? null : collectionDates.last();
            Aspect aspect = aspectRepository.findOrCreate(profile, event, entry.getKey(), entry.getValue(), collectionDate);

//            LOG.debug("aspect to add: {}", aspect.getValue());

            aspects.add(aspect);
        }
        updateProfileForAspects(aspects);
    }

    @Programmatic
    Map<AspectType, String> getAspectMap(Event event) {
        /* Retrieve all aspects from event data and filter all empty aspects */
        final Map<AspectType, String> map = event.getSource().getType().getParser().toMap(event.getData());
        return map.entrySet().stream()
                .filter(e -> e.getValue() != null && e.getValue().length() > 0)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @Programmatic
    private void cleanEmailOnAspectMap(final Map<AspectType, String> aspectMap) {
        if (aspectMap.containsKey(AspectType.EmailAccount)) {
            String cleanedEmailAddress = emailCleaningService.process(aspectMap.get(AspectType.EmailAccount));
            aspectMap.put(AspectType.EmailAccount, cleanedEmailAddress);
        }
    }

    @Programmatic
    Map<AspectType, String> getKeyAspectsFromAspectMap(Map<AspectType, String> aspectMap) {
        /* Filter all key aspects from existing aspect map */
        return aspectMap.entrySet().stream()
                .filter(e -> e.getKey().isKey())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @Programmatic
    SortedSet<LocalDateTime> getCollectionDatesFromAspectMap(final Map<AspectType, String> aspectMap) {
        return aspectMap.entrySet().stream()
                .filter(e -> e.getKey().isCollectionDate())
                .map(e -> LocalDateTime.parse(e.getValue()))
                .collect(Collectors.toCollection(TreeSet::new));
    }

    @Programmatic
    Set<Profile> getProfilesFromKeyAspects(Map<AspectType, String> keyAspectMap) {
        /* Retrieve all profiles related to key aspects */
        return keyAspectMap.entrySet().stream()
                .flatMap(entry -> aspectRepository.findByTypeAndValue(entry.getKey(), entry.getValue()).stream())
                .map(Aspect::getProfile)
                .filter(profile -> Objects.nonNull(profile))
                .collect(Collectors.toSet());
    }

    @Programmatic
    public void updateProfileForAspects(SortedSet<Aspect> aspects){
        for (Aspect aspect : aspects) {
            if (aspect.getProfile() != null) {
                aspect.getType().updateProfile(aspect);
            }
        }
    }

    @Inject
    private EmailCleaningService emailCleaningService;

    @Inject
    private ProfileRepository profileRepository;

    @Inject
    AspectRepository aspectRepository;

}
