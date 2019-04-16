package org.incode.eurocommercial.relatio.module.profile.dom;

import java.util.Comparator;
import java.util.Optional;
import java.util.SortedSet;
import java.util.TreeSet;

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

import org.joda.time.LocalDate;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.Collection;
import org.apache.isis.applib.annotation.CollectionLayout;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.Publishing;
import org.apache.isis.applib.annotation.Where;
import org.apache.isis.applib.services.eventbus.ActionDomainEvent;

import org.incode.eurocommercial.relatio.module.aspect.dom.Aspect;

import lombok.Getter;
import lombok.Setter;

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
                        + "FROM org.incode.eurocommercial.relatio.module.profile.dom.Profile "
        ),
        @Query(
                name = "findByUuid", language = "JDOQL",
                value = "SELECT "
                        + "FROM org.incode.eurocommercial.relatio.module.profile.dom.Profile "
                        + "WHERE uuid == :uuid "
        ),
        @Query(
                name = "findByFullNameContains", language = "JDOQL",
                value = "SELECT "
                        + "FROM org.incode.eurocommercial.relatio.module.profile.dom.Profile "
                        + "WHERE lastName.indexOf(:lastName) >= 0 "
        ),
        @Query(
                name = "findByAspectType", language = "JDOQL",
                value = "SELECT "
                        + "FROM org.incode.eurocommercial.relatio.module.profile.dom.Profile "
                        + "WHERE this.aspects.contains(aspect) && aspect.type == :aspectType "
                        + "VARIABLES org.incode.eurocommercial.relatio.module.aspect.dom.Aspect aspect "
        ),
        @Query(
                name = "findByAspectTypeAndValue", language = "JDOQL",
                value = "SELECT "
                        + "FROM org.incode.eurocommercial.relatio.module.profile.dom.Profile "
                        + "WHERE this.aspects.contains(aspect) && aspect.type == :aspectType && aspect.value == :aspectValue "
                        + "VARIABLES org.incode.eurocommercial.relatio.module.aspect.dom.Aspect aspect "
        )
})
@Unique(name = "Profile_fullName_UNQ", members = { "uuid" })
@DomainObject(
        editing = Editing.DISABLED,
        objectType = "Profile"
)
@DomainObjectLayout(
        bookmarking = BookmarkPolicy.AS_ROOT
)
public class Profile implements Comparable<Profile> {

    public String title() {
        String keyAspect = Optional.ofNullable(emailAccount)
                .orElse(Optional.ofNullable(cellPhoneNumber)
                        .orElse(facebookAccount));
        return "[" + keyAspect + "] " + firstName + " " + lastName;
    }

    @Column(allowsNull = "false")
    @Property(hidden = Where.ALL_TABLES)
    @Getter @Setter
    private String uuid;

    @Column(allowsNull = "true")
    @Property()
    @Getter @Setter
    private String firstName;

    @Column(allowsNull = "true")
    @Property()
    @Getter @Setter
    private String lastName;

    @Column(allowsNull = "true")
    @Property()
    @Persistent
    @Getter @Setter
    private LocalDate dateOfBirth;

    @Column(allowsNull = "true")
    @Property()
    @Persistent
    @Getter @Setter
    private LocalDate approximateDateOfBirth;

    @Column(allowsNull = "true")
    @Property()
    @Getter @Setter
    private Gender gender;

    @Column(allowsNull = "true")
    @Property()
    @Getter @Setter
    private String cellPhoneNumber;

    @Column(allowsNull = "true")
    @Property()
    @Getter @Setter
    private String facebookAccount;

    @Column(allowsNull = "true")
    @Property()
    @Getter @Setter
    private Boolean privacyConsent;

    @Column(allowsNull = "true")
    @Property()
    @Getter @Setter
    private Boolean marketingConsent;

    @Column(allowsNull = "true")
    @Property()
    @Getter @Setter
    private Boolean thirdPartyConsent;

    @Column(allowsNull = "true")
    @Property()
    @Getter @Setter
    private String emailAccount;

    @Column(allowsNull = "true")
    @Property()
    @Getter @Setter
    private String postalCode;

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

    public static class CreateDomainEvent extends ActionDomainEvent<Profile> {}

    @Action(domainEvent = Profile.CreateDomainEvent.class, publishing = Publishing.ENABLED)
    public Profile updateToMailChimp() {
        return this;
    }

    public String disableUpdateToMailChimp() {
        return getThirdPartyConsent() == null | getThirdPartyConsent() == Boolean.FALSE ? "This profile does not have third party consent enabled." : null;
    }

    public Profile updateFromAspects() {
        for (Aspect aspect : getAspects()) {
            aspect.getType().updateProfile(aspect);
        }
        return this;
    }

    public enum Gender {
        MALE("male"),
        FEMALE("female"),
        OTHER("other");

        @Getter
        private String value;

        Gender(final String value) {
            this.value = value;
        }
    }
}
