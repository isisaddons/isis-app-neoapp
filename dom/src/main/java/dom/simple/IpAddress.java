package dom.simple;

import javax.inject.Inject;
import javax.jdo.annotations.Column;
import javax.jdo.annotations.DatastoreIdentity;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Unique;
import javax.jdo.annotations.Version;
import javax.jdo.annotations.VersionStrategy;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.Title;
import org.apache.isis.applib.util.ObjectContracts;

@PersistenceCapable(identityType=IdentityType.DATASTORE)
@DatastoreIdentity(
        strategy= IdGeneratorStrategy.IDENTITY,
         column="id")
@Version(
        strategy=VersionStrategy.VERSION_NUMBER,
        column="version")
@Unique(name="IP_ADDRESS_ADDRESS_UNQ", members = {"address"})
@DomainObject(
        objectType = "IP_ADDRESS"
)
@DomainObjectLayout(
        bookmarking = BookmarkPolicy.AS_ROOT
)

public class IpAddress implements Comparable<IpAddress>, ObjectWithPersistedTitle {

    //region > title (implementing ObjectWithPersistedTitle)
    private String title;

    @Column(allowsNull = "false")
    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void setTitle(final String title) {
        this.title = title;
    }
    //endregion {

    // region > Address property
    private String address;

    @Column(allowsNull="false")
    @Title(sequence="1")
    public String getAddress() {
        return address;
    }

    public void setAddress(final String address) {
        this.address = address;
    }
    //endregion

    //region > host (property)
    private Host host;

    @Column(allowsNull = "false")
    public Host getHost() {
        return host;
    }

    public void setHost(final Host host) {
        this.host = host;
    }
    //endregion

    //region > compareTo
    @Override
    public int compareTo(IpAddress other) {
        return ObjectContracts.compare(this, other, "address");
    }
    //endregion

    //region > injected services
    @Inject
    @SuppressWarnings("unused")
    private DomainObjectContainer container;
    //endregion

}
