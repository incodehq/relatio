package org.incode.eurocommercial.relatio.dom.aspect;

import org.joda.time.LocalDate;

import org.incode.eurocommercial.relatio.dom.profile.Profile;

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
    PostalCode(),
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

    RegisteredAt(false, true),
    MailConfirmedAt(false, true),

    //Legal
    PrivacyConsent(),
    MarketingConsent() {
        @Override public void updateProfile(final Aspect aspect) {
            aspect.getProfile().setMarketingConsent(aspect.getValue());
        }
    },

    GamePlayDateAndTime(),
    GameType()
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
