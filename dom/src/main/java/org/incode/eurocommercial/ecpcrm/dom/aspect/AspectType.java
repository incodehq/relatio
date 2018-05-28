package org.incode.eurocommercial.ecpcrm.dom.aspect;

import java.time.LocalDate;

import org.incode.eurocommercial.ecpcrm.dom.profile.Profile;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum AspectType {

    FirstName(false) {
        @Override public void updateProfile(final Aspect aspect) {
            aspect.getProfile().setFirstName(aspect.getValue());
        }
    },
    LastName(false) {
        @Override public void updateProfile(final Aspect aspect) {
            aspect.getProfile().setLastName(aspect.getValue());
        }
    },
    Age(false),
    Birthday(false) {
        @Override public void updateProfile(final Aspect aspect) {
            aspect.getProfile().setBirthdate(LocalDate.parse(aspect.getValue()));
        }
    },
    Gender(false) {
        @Override public void updateProfile(final Aspect aspect) {
            aspect.getProfile().setGender(Profile.Gender.valueOf(aspect.getValue().toUpperCase()));
        }
    },

    FirstAccess(false),
    LastAccess(false),
    RegisteredAt(false),
    MailConfirmedAt(false),

    EmailAccount(true) {
        @Override public void updateProfile(final Aspect aspect) {
            aspect.getProfile().setEmailAccount(aspect.getValue());
        }
    },
    PhoneNumber(true) {
        @Override public void updateProfile(final Aspect aspect) {
            aspect.getProfile().setPhoneNumber(aspect.getValue());
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

    MacAddress(false),

    City(false),
    Address(false),
    PostCode(false),
    Country(false),
    Province(false);

    @Getter
    private boolean key;

    public void updateProfile(Aspect aspect){}
}
