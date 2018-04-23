package org.incode.eurocommercial.ecpcrm.dom.event;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.DatastoreIdentity;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Queries;
import javax.jdo.annotations.Query;
import javax.jdo.annotations.Unique;
import javax.jdo.annotations.Version;
import javax.jdo.annotations.VersionStrategy;

import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.Property;

import lombok.Getter;
import lombok.Setter;

@PersistenceCapable(
        identityType = IdentityType.DATASTORE,
        schema = "dom",
        table = "ImportEvent"
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
                        + "FROM org.incode.eurocommercial.ecpcrm.dom.event.ImportEvent "),
        @Query(
                name = "findByDataContains", language = "JDOQL",
                value = "SELECT "
                        + "FROM org.incode.eurocommercial.ecpcrm.dom.event.ImportEvent "
                        + "WHERE data.indexOf(:data) >= 0 "),
        @Query(
                name = "findByData", language = "JDOQL",
                value = "SELECT "
                        + "FROM org.incode.eurocommercial.ecpcrm.dom.event.ImportEvent "
                        + "WHERE data == :data ")
})
@Unique(name = "ImportEvent_data_UNQ", members = { "data" })
@DomainObject(
        editing = Editing.DISABLED
)
@DomainObjectLayout(
        bookmarking = BookmarkPolicy.AS_ROOT
)
public class ImportEvent implements Comparable<ImportEvent> {

    @Column(allowsNull = "false")
    @Property()
    @Getter @Setter
    private String data;

    @Override
    public int compareTo(final ImportEvent other) {

        // For now, would be better to compare using a key
        return this.getData().compareTo(other.getData());
    }

}
