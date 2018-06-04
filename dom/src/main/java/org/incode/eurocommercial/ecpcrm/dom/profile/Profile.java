package org.incode.eurocommercial.ecpcrm.dom.profile;

import java.time.LocalDate;
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

import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.Collection;
import org.apache.isis.applib.annotation.CollectionLayout;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.Where;

import org.incode.eurocommercial.ecpcrm.dom.aspect.Aspect;

import lombok.Getter;
import lombok.Setter;

@PersistenceCapable(
        identityType = IdentityType.DATASTORE,
        schema = "Profile",
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
                        + "FROM org.incode.eurocommercial.ecpcrm.dom.profile.Profile "),
        @Query(
                name = "findByFullNameContains", language = "JDOQL",
                value = "SELECT "
                        + "FROM org.incode.eurocommercial.ecpcrm.dom.profile.Profile "
                        + "WHERE lastName.indexOf(:lastName) >= 0 ")
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
        String keyAspect = Optional.ofNullable(emailAccount)
                .orElse(Optional.ofNullable(phoneNumber)
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
    @Getter @Setter
    private LocalDate birthdate;

    @Column(allowsNull = "true")
    @Property()
    @Getter @Setter
    private Gender gender;

    @Column(allowsNull = "true")
    @Property()
    @Getter @Setter
    private String emailAccount;

    @Column(allowsNull = "true")
    @Property()
    @Getter @Setter
    private String facebookAccount;

    @Column(allowsNull = "true")
    @Property()
    @Getter @Setter
    private String phoneNumber;

    @Persistent(mappedBy = "profile", dependentElement = "false")
    @Collection
    @CollectionLayout(defaultView = "table")
    @Getter @Setter
    private SortedSet<Aspect> aspects = new TreeSet<>();

    //region > compareTo, toString
    @Override
    public int compareTo(final Profile other) {
        return Comparator.comparing(Profile::getEmailAccount)
                .thenComparing(Profile::getPhoneNumber)
                .thenComparing(Profile::getFacebookAccount)
                .compare(this, other);
    }

    //endregion

    public Profile updateFromAspects(){
        for (Aspect aspect :
                getAspects()) {
            aspect.getType().updateProfile(aspect);
        }
        return this;
    }

    public enum Gender {
        MALE,
        FEMALE,
        OTHER;
    }
}