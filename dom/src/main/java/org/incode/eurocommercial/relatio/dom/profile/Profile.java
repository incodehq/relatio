package org.incode.eurocommercial.relatio.dom.profile;

import static java.util.Objects.isNull;
import lombok.Getter;
import lombok.Setter;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.Collection;
import org.apache.isis.applib.annotation.CollectionLayout;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.annotation.Where;
import org.incode.eurocommercial.relatio.dom.aspect.Aspect;
import org.joda.time.LocalDate;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.DatastoreIdentity;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.Queries;
import javax.jdo.annotations.Query;
import javax.jdo.annotations.Unique;
import javax.jdo.annotations.Version;
import javax.jdo.annotations.VersionStrategy;
import java.util.Comparator;
import java.util.Optional;
import java.util.SortedSet;
import java.util.TreeSet;

@PersistenceCapable(
        identityType = IdentityType.DATASTORE,
        table = "Profile"
)
@DatastoreIdentity(
        strategy = IdGeneratorStrategy.IDENTITY,
        column = "id")
@Version(
        strategy = VersionStrategy.VERSION_NUMBER,
        column = "version")
@Queries({
        @Query(
                name = "find", language = "JDOQL",
                value = "SELECT "
                        + "FROM org.incode.eurocommercial.relatio.dom.profile.Profile "
        ),
        @Query(
                name = "findByUuid", language = "JDOQL",
                value = "SELECT "
                        + "FROM org.incode.eurocommercial.relatio.dom.profile.Profile "
                        + "WHERE uuid == :uuid "
        ),
        @Query(
                name = "findByFullNameContains", language = "JDOQL",
                value = "SELECT "
                        + "FROM org.incode.eurocommercial.relatio.dom.profile.Profile "
                        + "WHERE lastName.indexOf(:lastName) >= 0 "
        ),
        @Query(
                name = "findByAspectType", language = "JDOQL",
                value = "SELECT "
                        + "FROM org.incode.eurocommercial.relatio.dom.profile.Profile "
                        + "WHERE this.aspects.contains(aspect) && aspect.type == :aspectType "
                        + "VARIABLES org.incode.eurocommercial.relatio.dom.aspect.Aspect aspect "
        ),
        @Query(
                name = "findByAspectTypeAndValue", language = "JDOQL",
                value = "SELECT "
                        + "FROM org.incode.eurocommercial.relatio.dom.profile.Profile "
                        + "WHERE this.aspects.contains(aspect) && aspect.type == :aspectType && aspect.value == :aspectValue "
                        + "VARIABLES org.incode.eurocommercial.relatio.dom.aspect.Aspect aspect "
        )
})
@Unique(name = "Profile_fullName_UNQ", members = { "uuid" })
@DomainObject(
        editing = Editing.DISABLED
)
@DomainObjectLayout(
        bookmarking = BookmarkPolicy.AS_ROOT
)
public class Profile implements Comparable<Profile> {

    public String title() {
        String keyAspect = Optional.ofNullable(getEmailAccount())
                .orElse(Optional.ofNullable(getCellPhoneNumber())
                        .orElse(getFacebookAccount()));
        return "[" + keyAspect + "] " + getFirstName() + " " + getLastName();
    }

    @Column(allowsNull = "false")
    @Property(hidden = Where.ALL_TABLES)
    @Getter @Setter
    private String uuid;

    @PropertyLayout(hidden = Where.EVERYWHERE)
    @Column(allowsNull = "true")
    @Property()
    @Getter @Setter
    private Aspect firstNameAspect;

    public String getFirstName(){
        return isNull(getFirstNameAspect()) ? null : getFirstNameAspect().getValue();
    }

    @PropertyLayout(hidden = Where.EVERYWHERE)
    @Column(allowsNull = "true")
    @Property()
    @Getter @Setter
    private Aspect lastNameAspect;

    public String getLastName(){
        return isNull(getLastNameAspect()) ? null : getLastNameAspect().getValue();
    }

    @PropertyLayout(hidden = Where.EVERYWHERE)
    @Column(allowsNull = "true")
    @Property()
    @Persistent
    @Getter @Setter
    private Aspect dateOfBirthAspect;

    public LocalDate getDateOfBirth(){
        return isNull(getDateOfBirthAspect()) ? null : LocalDate.parse(getDateOfBirthAspect().getValue());
    }

    @PropertyLayout(hidden = Where.EVERYWHERE)
    @Column(allowsNull = "true")
    @Property()
    @Persistent
    @Getter @Setter
    private Aspect approximateDateOfBirthAspect;

    public LocalDate getApproximateDateOfBirth(){
        return isNull(getApproximateDateOfBirthAspect()) ? null : LocalDate.parse(getApproximateDateOfBirthAspect().getValue());
    }

    @PropertyLayout(hidden = Where.EVERYWHERE)
    @Column(allowsNull = "true")
    @Property()
    @Getter @Setter
    private Aspect genderAspect;

    public Gender getGender(){
        return isNull(getGenderAspect()) ? null : Gender.valueOf(getGenderAspect().getValue().toUpperCase());
    }

    @PropertyLayout(hidden = Where.EVERYWHERE)
    @Column(allowsNull = "true")
    @Property()
    @Getter @Setter
    private Aspect cellPhoneNumberAspect;

    public String getCellPhoneNumber(){
        return isNull(getCellPhoneNumberAspect()) ? null : getCellPhoneNumberAspect().getValue();
    }

    @PropertyLayout(hidden = Where.EVERYWHERE)
    @Column(allowsNull = "true")
    @Property()
    @Getter @Setter
    private Aspect facebookAccountAspect;

    public String getFacebookAccount(){
        return isNull(getFacebookAccountAspect()) ? null : getFacebookAccountAspect().getValue();
    }

    @PropertyLayout(hidden = Where.EVERYWHERE)
    @Column(allowsNull = "true")
    @Property()
    @Getter @Setter
    private Aspect privacyConsentAspect;

    public Boolean getPrivacyConsent(){
        return isNull(getPrivacyConsentAspect()) ? null : Boolean.valueOf(getPrivacyConsentAspect().getValue());
    }

    @PropertyLayout(hidden = Where.EVERYWHERE)
    @Column(allowsNull = "true")
    @Property()
    @Getter @Setter
    private Aspect marketingConsentAspect;

    public Boolean getMarketingConsent(){
        return isNull(getMarketingConsentAspect()) ? null : Boolean.valueOf(getMarketingConsentAspect().getValue());
    }

    @PropertyLayout(hidden = Where.EVERYWHERE)
    @Column(allowsNull = "true")
    @Property()
    @Getter @Setter
    private Aspect emailAccountAspect;

    public String getEmailAccount(){
        return isNull(getEmailAccountAspect()) ? null : getEmailAccountAspect().getValue();
    }

    @Persistent(mappedBy = "profile", dependentElement = "false")
    @Collection
    @CollectionLayout(defaultView = "table")
    @Getter @Setter
    private SortedSet<Aspect> aspects = new TreeSet<>();

    //region > compareTo, toString
    @Override
    public int compareTo(final Profile other) {
        return Comparator.comparing(Profile::getEmailAccount)
                .thenComparing(Profile::getCellPhoneNumber)
                .thenComparing(Profile::getFacebookAccount)
                .compare(this, other);
    }

    //endregion

    public Profile updateFromAspects() {
        for (Aspect aspect : getAspects()) {
            aspect.getType().updateProfile(aspect);
        }
        return this;
    }

    public enum Gender {
        MALE,
        FEMALE,
        OTHER
    }
}
