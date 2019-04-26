package org.incode.eurocommercial.relatio.app.services.restful.v1;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;

import org.incode.eurocommercial.relatio.canonical.profile.v1.ProfileDto;
import org.incode.eurocommercial.relatio.module.profile.dom.Profile;

@DomainService(
        nature = NatureOfService.DOMAIN
)
public class ProfileDtoFactory extends DtoFactoryAbstract {

    @Programmatic
    public ProfileDto newDto(final Profile profile){
        final ProfileDto dto = new ProfileDto();

        dto.setUuid(profile.getUuid());
        dto.setFirstName(profile.getFirstName());
        dto.setLastName(profile.getLastName());
        dto.setDateOfBirth(asXMLGregorianCalendar(profile.getDateOfBirth()));
        dto.setApproximateDateOfBirth(asXMLGregorianCalendar(profile.getApproximateDateOfBirth()));
        dto.setGender(toDto(profile.getGender()));
        dto.setCellPhoneNumber(profile.getCellPhoneNumber());
        dto.setFacebookAccount(profile.getFacebookAccount());
        dto.setPrivacyConsent(falseIfNullForBoolean(profile.getPrivacyConsent()));
        dto.setMarketingConsent(falseIfNullForBoolean(profile.getMarketingConsent()));
        dto.setThirdPartyConsent(falseIfNullForBoolean(profile.getThirdPartyConsent()));
        dto.setEmailAccount(profile.getEmailAccount());
        dto.setPostalCode(profile.getPostalCode());
        dto.setDogOwner(falseIfNullForBoolean(profile.getDogOwner()));
        dto.setParent(falseIfNullForBoolean(profile.getParent()));

        return dto;
    }

    private Boolean falseIfNullForBoolean(Boolean bool){
        return bool == null ? false : bool;
    }

    private org.incode.eurocommercial.relatio.canonical.profile.v1.Gender toDto(final Profile.Gender gender) {
        return org.incode.eurocommercial.relatio.canonical.profile.v1.Gender.fromValue(gender.name());
    }
}
