package org.incode.eurocommercial.relatio.dom.aspect;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum AspectType {
    //Identity
    FirstName() {
        @Override public void updateProfile(final Aspect aspect) {
            aspect.getProfile().setFirstNameAspect(aspect);
        }
    },
    LastName() {
        @Override public void updateProfile(final Aspect aspect) {
            aspect.getProfile().setLastNameAspect(aspect);
        }
    },
    MinimumAge(),
    DateOfBirth() {
        @Override public void updateProfile(final Aspect aspect) {
            aspect.getProfile().setDateOfBirthAspect(aspect);
        }
    },
    ApproximateDateOfBirth() {
        @Override public void updateProfile(final Aspect aspect) {
            aspect.getProfile().setApproximateDateOfBirthAspect(aspect);
        }
    },
    Gender() {
        @Override public void updateProfile(final Aspect aspect) {
            aspect.getProfile().setGenderAspect(aspect);
        }
    },
    EmailAccount(true) {
        @Override public void updateProfile(final Aspect aspect) {
            aspect.getProfile().setEmailAccountAspect(aspect);
        }
    },
    HomePhoneNumber(),
    CellPhoneNumber(true) {
        @Override public void updateProfile(final Aspect aspect) {
            aspect.getProfile().setCellPhoneNumberAspect(aspect);
        }
    },
    FacebookAccount(true) {
        @Override public void updateProfile(final Aspect aspect) {
            aspect.getProfile().setFacebookAccountAspect(aspect);
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
    PrivacyConsent(){
        @Override public void updateProfile(final Aspect aspect) {
            aspect.getProfile().setPrivacyConsentAspect(aspect);
        }
    },
    MarketingConsent() {
        @Override public void updateProfile(final Aspect aspect) {
            aspect.getProfile().setMarketingConsentAspect(aspect);
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
