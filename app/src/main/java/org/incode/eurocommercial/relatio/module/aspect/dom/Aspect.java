package org.incode.eurocommercial.relatio.module.aspect.dom;

import java.util.Comparator;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.DatastoreIdentity;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Queries;
import javax.jdo.annotations.Query;

import org.joda.time.LocalDateTime;

import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.Where;

import org.incode.eurocommercial.relatio.module.event.dom.Event;
import org.incode.eurocommercial.relatio.module.profile.dom.Profile;

import lombok.Getter;
import lombok.Setter;

@PersistenceCapable(
        identityType = IdentityType.DATASTORE
)
@DatastoreIdentity(
        strategy = IdGeneratorStrategy.IDENTITY,
        column = "id")
@Queries({
        @Query(
                name = "findByType", language = "JDOQL",
                value = "SELECT "
                        + "FROM org.incode.eurocommercial.relatio.module.aspect.dom.Aspect "
                        + "WHERE type == :type "),
        @Query(
                name = "findByTypeAndValue", language = "JDOQL",
                value = "SELECT "
                        + "FROM org.incode.eurocommercial.relatio.module.aspect.dom.Aspect "
                        + "WHERE type == :type && value.toUpperCase() == :value.toUpperCase() "),
        @Query(
                name = "findByEvent", language = "JDOQL",
                value = "SELECT "
                        + "FROM org.incode.eurocommercial.relatio.module.aspect.dom.Aspect "
                        + "WHERE event == :event "),
        @Query(
                name = "findByEventAndType", language = "JDOQL",
                value = "SELECT "
                        + "FROM org.incode.eurocommercial.relatio.module.aspect.dom.Aspect "
                        + "WHERE event == :event && type == :type ")

})
@DomainObject(
        editing = Editing.DISABLED,
        objectType = "Aspect"
)
public class Aspect implements Comparable<Aspect> {

    public String title() {
        String profileTitle = getProfile() == null ? "" : "[" + getProfile().title() + "] ";
        return profileTitle + getType() + ": " + getValue();
    }

    @Column(allowsNull = "true", name = "profileId")
    @Getter @Setter
    @Property(hidden = Where.REFERENCES_PARENT)
    Profile profile;

    @Column(allowsNull = "false", name = "eventId")
    @Getter @Setter
    @Property(hidden = Where.REFERENCES_PARENT)
    private Event event;

    @Column(allowsNull = "false")
    @Getter @Setter
    private AspectType type;

    @Column(allowsNull = "false")
    @Getter @Setter
    private String value;

    @Column(allowsNull = "true")
    @Getter @Setter
    private LocalDateTime collectedAt;

    public Aspect() {}

    public Aspect(final Event event, final AspectType type, final String value) {
        this.event = event;
        this.type = type;
        this.value = value;
    }

    //region > compareTo, toString
    @Override
    public int compareTo(final Aspect other) {
        return Comparator.comparing(Aspect::getEvent).thenComparing(Aspect::getType).thenComparing(Aspect::getValue).compare(this, other);
    }
    //endregion

}
