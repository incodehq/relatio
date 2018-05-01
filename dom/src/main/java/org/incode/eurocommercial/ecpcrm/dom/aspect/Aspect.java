package org.incode.eurocommercial.ecpcrm.dom.aspect;

import java.util.Comparator;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Queries;
import javax.jdo.annotations.Query;

import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.Where;

import org.incode.eurocommercial.ecpcrm.dom.Profile.Profile;
import org.incode.eurocommercial.ecpcrm.dom.event.Event;

import lombok.Getter;
import lombok.Setter;

@PersistenceCapable(
        identityType = IdentityType.DATASTORE
)
@Queries({
        @Query(
                name = "findByTypeAndValue", language = "JDOQL",
                value = "SELECT "
                        + "FROM org.incode.eurocommercial.ecpcrm.dom.aspect.Aspect "
                        + "WHERE type == :type && value == :value "),
        @Query(
                name = "findByEvent", language = "JDOQL",
                value = "SELECT "
                        + "FROM org.incode.eurocommercial.ecpcrm.dom.aspect.Aspect "
                        + "WHERE event == :event "),
        @Query(
                name = "findByEventAndType", language = "JDOQL",
                value = "SELECT "
                        + "FROM org.incode.eurocommercial.ecpcrm.dom.aspect.Aspect "
                        + "WHERE event == :event && type == :type ")

})
@DomainObject(
        editing = Editing.DISABLED
)
public class Aspect implements Comparable<Aspect> {

    @Column(allowsNull = "false", name = "profileId")
    @Getter @Setter
    @Property(hidden = Where.REFERENCES_PARENT)
    private Profile profile;

    @Column(allowsNull = "false")
    @Getter @Setter
    private AspectType type;

    @Column(allowsNull = "false")
    @Getter @Setter
    private String value;

    @Column(allowsNull = "false", name = "eventId")
    @Getter @Setter
    @Property(hidden = Where.REFERENCES_PARENT)
    private Event event;

    public Aspect(){};

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

    @Override
    public String toString() {
        return getValue();
    }
    //endregion

}
