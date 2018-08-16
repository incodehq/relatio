package org.incode.eurocommercial.ecpcrm.dom.aspect;

import org.joda.time.LocalDate;

import org.incode.eurocommercial.ecpcrm.dom.profile.Profile;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum AspectType {
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
    Birthday() {
        @Override public void updateProfile(final Aspect aspect) {
            aspect.getProfile().setBirthdate(LocalDate.parse(aspect.getValue()));
        }
    },
    Gender() {
        @Override public void updateProfile(final Aspect aspect) {
            aspect.getProfile().setGender(Profile.Gender.valueOf(aspect.getValue().toUpperCase()));
        }
    },

    Access(false, true),
    RegisteredAt(false, true),
    MailConfirmedAt(false, true),

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
    GooglePlusAccount(true),
    TwitterAccount(true),
    LinkedInAccount(true),

    MacAddress(),

    City(),
    Address(),
    PostCode(),
    Country(),
    Province(),
    Belongings(),
    FullName(),
    Comune();

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
