package org.incode.eurocommercial.relatio.module.aspect.dom;

import org.joda.time.LocalDate;

import org.incode.eurocommercial.relatio.module.profile.dom.Profile;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum AspectType {
    //Identity
    FirstName() {
        @Override public void updateProfile(final Aspect aspect) {
            aspect.getProfile().setFirstName(aspect.getValue());
        }
    },
    LastName() {
        @Override public void updateProfile(final Aspect aspect) {
            aspect.getProfile().setLastName(aspect.getValue());
        }
    },
    MinimumAge(),
    DateOfBirth() {
        @Override public void updateProfile(final Aspect aspect) {
            aspect.getProfile().setDateOfBirth(LocalDate.parse(aspect.getValue()));
        }
    },
    YearOfBirth(),
    ApproximateDateOfBirth() {
        @Override public void updateProfile(final Aspect aspect) {
            aspect.getProfile().setApproximateDateOfBirth(LocalDate.parse(aspect.getValue()));
        }
    },
    Gender() {
        @Override public void updateProfile(final Aspect aspect) {
            aspect.getProfile().setGender(Profile.Gender.valueOf(aspect.getValue().toUpperCase()));
        }
    },
    EmailAccount(true) {
        @Override public void updateProfile(final Aspect aspect) {
            aspect.getProfile().setEmailAccount(aspect.getValue());
        }
    },
    HomePhoneNumber(),
    GeneralPhoneNumber(),
    CellPhoneNumber(true) {
        @Override public void updateProfile(final Aspect aspect) {
            aspect.getProfile().setCellPhoneNumber(aspect.getValue());
        }
    },
    FacebookAccount(true) {
        @Override public void updateProfile(final Aspect aspect) {
            aspect.getProfile().setFacebookAccount(aspect.getValue());
        }
    },

    // Address
    City(),
    Address(),
    PostalCode(){
        @Override public void updateProfile(final Aspect aspect) {
            aspect.getProfile().setPostalCode(aspect.getValue());
        }
    },
    Country(),
    Province(),
    Belongings(),
    FullName(),
    Comune(), // TODO: Very Italian, should we change?
    Localita(), // TODO: Very Italian, should we change?
    BusinessName(),

    // Social Accounts
    GooglePlusAccount(true),
    TwitterAccount(true),
    LinkedInAccount(true),
    LiveAccount(true),
    InstagramAccount(true),


    // Interactions
    Access(false, true),
    MacAddress(),
    DateCollected(false, true),
    Source(),

    RegisteredAt(false, true),
    MailConfirmedAt(false, true),

    //Legal
    PrivacyConsent(){
        @Override public void updateProfile(final Aspect aspect) {
            aspect.getProfile().setPrivacyConsent(Boolean.valueOf((aspect.getValue())));
        }
    },
    MarketingConsent() {
        @Override public void updateProfile(final Aspect aspect) {
            aspect.getProfile().setMarketingConsent(Boolean.valueOf((aspect.getValue())));
        }
    },
    ProfilingConsent(),
    ThirdPartyConsent(),
    PrivacyConsentParty(),
    MarketingConsentParty(),
    ProfilingConsentParty(),
    ThirdPartyConsentParty(),

    // Game play
    GamePlayDateAndTime(false, true),
    GameType(),


    // Attributes
    Employee(),
    DogOwner(),
    Parent(),
    FamilySize(),
    OnlineShopper(),
    CarOwner(),
    TransportUsed(),
    CentreRestroomsUsed(),
    CentreRestroomsRating(),
    WifiUser(),
    WifiRating(),
    PlaygroundUser(),
    PlaygroundRating(),
    NurseryUser(),
    NurseryRating(),
    RelaxAreaUser(),
    RelaxAreaRating(),
    InfopointUser(),
    InfopointRating(),
    CarwashUser(),
    CarwashRating(),
    AgeGroup(),
    reasonToVisitMall(),
    ratingOverallShoppingMall(),
    foodPrefenceForDinner(),

    // Device
    DeviceId(),



    ;

    @Getter
    private boolean key;

    @Getter
    private boolean collectionDate;

    AspectType(final boolean key) {
        this(key, false);
    }

    AspectType() {
        this(false, false);
    }

    public void updateProfile(Aspect aspect){}
}
