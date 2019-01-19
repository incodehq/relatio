package org.incode.eurocommercial.relatio.dom.aspect;

import org.apache.isis.core.unittestsupport.jmocking.JUnitRuleMockery2;
import static org.assertj.core.api.Assertions.assertThat;
import org.incode.eurocommercial.relatio.dom.event.Event;
import org.incode.eurocommercial.relatio.dom.event.EventSource;
import static org.incode.eurocommercial.relatio.dom.event.EventSourceType.GamePlayedEventV1;
import org.incode.eurocommercial.relatio.dom.profile.Profile;
import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public class AspectCreationServiceTest {

    public static final String FIRST_NAME = "fooFirstName";
    public static final String LAST_NAME = "fooLastName";
    public static final String DATE_OF_BIRTH = "2001-01-01";
    public static final String APPROXIMATE_DATE_OF_BIRTH = "2000-01-01";
    public static final String CELLPHONE_NUMBER = "0623447891";
    public static final String FACE_BOOK_ACCOUNT = "fooFaceBookAccount";
    public static final String PRIVACY_CONSENT = "true";
    public static final String MARKETING_CONSENT = "true";
    public static final String EMAIL = "hello@yahoo.com";
    public static final String MALE = "MALE";
    public static final String POSTALCODE = "33333";

    @Rule
    public JUnitRuleMockery2 context = JUnitRuleMockery2.createFor(JUnitRuleMockery2.Mode.INTERFACES_AND_CLASSES);

    @Mock
    Event mockEvent;

    @Mock
    EventSource mockEventSource;

    @Mock
    AspectRepository mockAspectRepository;

    @Test
    public void getAspectMap_happy_case(){
        // given
        Event event = mockEvent;
        String data = ("Sandrino~Ginnio~M~34~hello@yahoo.com~3912345678~23900~YES~YES~2018-07-21T08:26:51~BENVENUTO");

        // expected
        context.checking(new Expectations() {{
            oneOf(mockEvent).getSource();
            will(returnValue(mockEventSource));
            oneOf(mockEventSource).getType();
            will(returnValue(GamePlayedEventV1));
            oneOf(mockEvent).getData();
            will(returnValue(data));
        }});

        Map<AspectType, String> expectedMap = new HashMap<AspectType, String>()
        {
            {
                put(AspectType.FirstName, "Sandrino");
                put(AspectType.LastName, "Ginnio");
                put(AspectType.ApproximateDateOfBirth, "1984-01-21");
                put(AspectType.Gender, "MALE");
                put(AspectType.EmailAccount, "hello@yahoo.com");
                put(AspectType.CellPhoneNumber, "3912345678");
                put(AspectType.PostalCode, "23900");
                put(AspectType.PrivacyConsent, "true");
                put(AspectType.MarketingConsent, "true");
                put(AspectType.GamePlayDateAndTime, "2018-07-21T08:26:51");
                put(AspectType.GameType, "BENVENUTO");
            }
        };

        // when
        AspectCreationService aspectCreationService = new AspectCreationService();
        Map<AspectType, String> resultingMap = aspectCreationService.getAspectMap(event);

        // then
        assertThat(resultingMap).isEqualTo(expectedMap);
    }

    @Test
    public void getKeyAspectsFromAspectMap_all_happy_case(){
        // given
        Map<AspectType, String> givenMap = new HashMap<AspectType, String>()
        {
            {
                put(AspectType.FirstName, "Sandrino");
                put(AspectType.LastName, "Ginnio");
                put(AspectType.ApproximateDateOfBirth, "1984-01-21");
                put(AspectType.Gender, "MALE");
                put(AspectType.PostalCode, "23900");
                put(AspectType.PrivacyConsent, "true");
                put(AspectType.MarketingConsent, "true");
                put(AspectType.GamePlayDateAndTime, "2018-07-21T08:26:51");
                put(AspectType.GameType, "BENVENUTO");
                //key aspects in map
                put(AspectType.EmailAccount, "hello@yahoo.com");
                put(AspectType.CellPhoneNumber, "3912345678");
                put(AspectType.FacebookAccount, "fooFaceBook");
                put(AspectType.TwitterAccount, "fooTwitter");
                put(AspectType.LinkedInAccount, "fooLinkedIn");
                put(AspectType.LiveAccount, "fooLive");
                put(AspectType.InstagramAccount, "fooInstagram");
            }
        };

        //expected
        Map<AspectType, String> expectedKeyMap = new HashMap<AspectType, String>()
        {
            {
                put(AspectType.EmailAccount, "hello@yahoo.com");
                put(AspectType.CellPhoneNumber, "3912345678");
                put(AspectType.FacebookAccount, "fooFaceBook");
                put(AspectType.TwitterAccount, "fooTwitter");
                put(AspectType.LinkedInAccount, "fooLinkedIn");
                put(AspectType.LiveAccount, "fooLive");
                put(AspectType.InstagramAccount, "fooInstagram");
            }
        };

        // when
        AspectCreationService aspectCreationService = new AspectCreationService();
        Map<AspectType, String> resultingMap = aspectCreationService.getKeyAspectsFromAspectMap(givenMap);

        // then
        assertThat(resultingMap).isEqualTo(expectedKeyMap);
    }

    @Test
    public void getCollectionDatesFromAspectMap_happy_case(){
        // given
        LocalDateTime firstDate = LocalDateTime.parse("2000-01-01");
        LocalDateTime secondDate = LocalDateTime.parse("2001-01-01");
        LocalDateTime thirdDate = LocalDateTime.parse("2002-01-01");
        LocalDateTime fourthDate = LocalDateTime.parse("2018-07-21T08:26:51");

        Map<AspectType, String> givenMap = new HashMap<AspectType, String>()
        {
            {
                put(AspectType.FirstName, "Sandrino");
                put(AspectType.LastName, "Ginnio");
                put(AspectType.ApproximateDateOfBirth, "1984-01-21");
                put(AspectType.Gender, "MALE");
                put(AspectType.EmailAccount, "hello@yahoo.com");
                put(AspectType.CellPhoneNumber, "3912345678");
                put(AspectType.PostalCode, "23900");
                put(AspectType.PrivacyConsent, "true");
                put(AspectType.MarketingConsent, "true");
                put(AspectType.GamePlayDateAndTime, fourthDate.toString()); // also collectionDate
                put(AspectType.GameType, "BENVENUTO");
                //collectionDates in map
                put(AspectType.Access, firstDate.toString());
                put(AspectType.RegisteredAt, secondDate.toString());
                put(AspectType.MailConfirmedAt, thirdDate.toString());
            }
        };

        //expected
        Set<LocalDateTime> expectedLocalDateTimes = new HashSet<>();
        expectedLocalDateTimes.add(firstDate);
        expectedLocalDateTimes.add(secondDate);
        expectedLocalDateTimes.add(thirdDate);
        expectedLocalDateTimes.add(fourthDate);

        // when
        AspectCreationService aspectCreationService = new AspectCreationService();
        SortedSet <LocalDateTime> resultingLocalDateTimes = aspectCreationService.getCollectionDatesFromAspectMap(givenMap);

        // then
        assertThat(resultingLocalDateTimes).isEqualTo(expectedLocalDateTimes);
    }

    @Test
    public void getProfilesFromKeyAspects_happy_case(){
        //given
        Map<AspectType, String> givenKeyMap = new HashMap<AspectType, String>()
        {
            {
                put(AspectType.EmailAccount, "hello@yahoo.com");
                put(AspectType.CellPhoneNumber, "3912345678");
                put(AspectType.FacebookAccount, "fooFaceBook");
                put(AspectType.TwitterAccount, "fooTwitter");
                put(AspectType.LinkedInAccount, "fooLinkedIn");
                put(AspectType.LiveAccount, "fooLive");
                put(AspectType.InstagramAccount, "fooInstagram");
            }
        };

        List<Aspect> aspectList = new ArrayList<>();
        givenKeyMap.forEach((aspectType, aspectValue) -> aspectList.add(new Aspect(null, aspectType, aspectValue)));

        // each other profile is not set, to test the filtering function of profile not being null.
        for (int i = 0; i < aspectList.size(); i+=2){
            aspectList.get(i).setProfile(new Profile());
        }

        context.checking(new Expectations() {{
            oneOf(mockAspectRepository).findByTypeAndValue(AspectType.EmailAccount, "hello@yahoo.com");
            will(returnValue(Arrays.asList(aspectList.get(0))));
            oneOf(mockAspectRepository).findByTypeAndValue(AspectType.CellPhoneNumber, "3912345678");
            will(returnValue(Arrays.asList(aspectList.get(1))));
            oneOf(mockAspectRepository).findByTypeAndValue(AspectType.FacebookAccount, "fooFaceBook");
            will(returnValue(Arrays.asList(aspectList.get(2))));
            oneOf(mockAspectRepository).findByTypeAndValue(AspectType.TwitterAccount, "fooTwitter");
            will(returnValue(Arrays.asList(aspectList.get(3))));
            oneOf(mockAspectRepository).findByTypeAndValue(AspectType.LinkedInAccount, "fooLinkedIn");
            will(returnValue(Arrays.asList(aspectList.get(4))));
            oneOf(mockAspectRepository).findByTypeAndValue(AspectType.LiveAccount, "fooLive");
            will(returnValue(Arrays.asList(aspectList.get(5))));
            oneOf(mockAspectRepository).findByTypeAndValue(AspectType.InstagramAccount, "fooInstagram");
            will(returnValue(Arrays.asList(aspectList.get(6))));

        }});

        // when
        AspectCreationService aspectCreationService = new AspectCreationService();
        aspectCreationService.aspectRepository = mockAspectRepository;
        Set<Profile> resultingProfiles = aspectCreationService.getProfilesFromKeyAspects(givenKeyMap);

        // then
        assertThat(resultingProfiles).hasSize(4);
    }

    @Test
    public void updateProfileForAspects_happy_case(){
        //given
        Aspect aspect = new Aspect(mockEvent, AspectType.EmailAccount, "hello@yahoo.com");
        assertThat(aspect.getProfile()).isNull();

        context.checking(new Expectations() {{
            oneOf(mockEvent).compareTo(mockEvent);
            will(returnValue(0));
        }});

        aspect.setProfile(new Profile());
        SortedSet<Aspect> sortedAspects = new TreeSet<>(Arrays.asList(aspect));

        //when
        AspectCreationService aspectCreationService = new AspectCreationService();
        aspectCreationService.updateProfileForAspects(sortedAspects);

        //then
        assertThat(aspect.getProfile().getEmailAccount()).isEqualTo("hello@yahoo.com");
    }

    // test for all aspects being set
    @Test
    public void updateProfileForAspectsMany_happy_case(){
        //given
        Aspect aspectFirstName = new Aspect(mockEvent, AspectType.FirstName, FIRST_NAME);
        Aspect aspectLastName = new Aspect(mockEvent, AspectType.LastName, LAST_NAME);
        Aspect aspectDateOfBirth = new Aspect(mockEvent, AspectType.DateOfBirth, DATE_OF_BIRTH);
        Aspect aspectApproximatedDateOfBirth = new Aspect(mockEvent, AspectType.ApproximateDateOfBirth, APPROXIMATE_DATE_OF_BIRTH);
        Aspect aspectGender = new Aspect(mockEvent, AspectType.Gender, MALE);
        Aspect aspectCellPhoneNumber = new Aspect(mockEvent, AspectType.CellPhoneNumber, CELLPHONE_NUMBER);
        Aspect aspectFacebookAccount = new Aspect(mockEvent, AspectType.FacebookAccount, FACE_BOOK_ACCOUNT);
        Aspect aspectPrivacyConsent = new Aspect(mockEvent, AspectType.PrivacyConsent, PRIVACY_CONSENT);
        Aspect aspectMarketingConsent = new Aspect(mockEvent, AspectType.MarketingConsent, MARKETING_CONSENT);
        Aspect aspectEmailAccount = new Aspect(mockEvent, AspectType.EmailAccount, EMAIL);
        Aspect aspectPostCode = new Aspect(mockEvent, AspectType.PostalCode, POSTALCODE);

        context.checking(new Expectations() {{
            allowing (mockEvent).compareTo(mockEvent);
            will(returnValue(0));
        }});

        Profile profile = new Profile();
        SortedSet<Aspect> sortedAspects = new TreeSet<>(Arrays.asList(aspectFirstName, aspectLastName, aspectDateOfBirth, aspectApproximatedDateOfBirth, aspectGender, aspectCellPhoneNumber, aspectFacebookAccount, aspectPrivacyConsent, aspectMarketingConsent, aspectEmailAccount, aspectPostCode));

        for(Aspect aspect : sortedAspects){
            aspect.setProfile(profile);
        }

        //when
        AspectCreationService aspectCreationService = new AspectCreationService();
        aspectCreationService.updateProfileForAspects(sortedAspects);

        //then
        assertThat(aspectFirstName.getProfile().getFirstName()).isEqualTo(FIRST_NAME);
        assertThat(aspectLastName.getProfile().getLastName()).isEqualTo(LAST_NAME);
        assertThat(aspectDateOfBirth.getProfile().getDateOfBirth()).isEqualTo(new LocalDate(DATE_OF_BIRTH));
        assertThat(aspectApproximatedDateOfBirth.getProfile().getApproximateDateOfBirth()).isEqualTo(new LocalDate(APPROXIMATE_DATE_OF_BIRTH));
        assertThat(aspectGender.getProfile().getGender()).isEqualTo(Profile.Gender.MALE);
        assertThat(aspectCellPhoneNumber.getProfile().getCellPhoneNumber()).isEqualTo(CELLPHONE_NUMBER);
        assertThat(aspectFacebookAccount.getProfile().getFacebookAccount()).isEqualTo(FACE_BOOK_ACCOUNT);
        assertThat(aspectPrivacyConsent.getProfile().getPrivacyConsent()).isEqualTo(Boolean.TRUE);
        assertThat(aspectMarketingConsent.getProfile().getMarketingConsent()).isEqualTo(Boolean.TRUE);
        assertThat(aspectEmailAccount.getProfile().getEmailAccount()).isEqualTo(EMAIL);
        assertThat(aspectPostCode.getProfile().getPostalCode()).isEqualTo(POSTALCODE);


    }
}