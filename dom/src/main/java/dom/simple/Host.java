package dom.simple;

import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.DatastoreIdentity;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.Unique;
import javax.jdo.annotations.Version;
import javax.jdo.annotations.VersionStrategy;

import com.google.common.collect.Iterables;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.Title;
import org.apache.isis.applib.services.actinvoc.ActionInvocationContext;
import org.apache.isis.applib.util.ObjectContracts;

@PersistenceCapable(identityType= IdentityType.DATASTORE)
@DatastoreIdentity(
        strategy= IdGeneratorStrategy.IDENTITY,
        column="id")
@Version(
        strategy=VersionStrategy.VERSION_NUMBER,
        column="version")
@Unique(name="HOST_NAME_UNQ", members = {"name"})
@DomainObject(
        objectType = "HOST"
)
@DomainObjectLayout(
        bookmarking = BookmarkPolicy.AS_ROOT
)
public class Host implements Comparable<Host>, ObjectWithPersistedTitle {

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
    //endregion

    // region > Name (property)
    private String name;

    @Column(allowsNull="false")
    @Title(sequence="1")
    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }
    //endregion

    // region > IpAddresses (collection)
    private Set<IpAddress> ipAddresses = new TreeSet<>();

    //@Column(allowsNull="false") // not needed, I think....
    @Persistent(mappedBy = "host")
    public Set<IpAddress> getIpAddresses() {
        return ipAddresses;
    }
    public void setIpAddresses(final Set<IpAddress> ipAddresses) {
        this.ipAddresses = ipAddresses;
    }
    //endregion

    //region > addIpAddress (action)
    public Host addIpAddress(@ParameterLayout(named = "Address") String address){
        final IpAddress ipAddress = container.newTransientInstance(IpAddress.class);
        ipAddress.setAddress(address);
        this.ipAddresses.add(ipAddress);
        container.persistIfNotAlready(ipAddress);
        return this;
    }

    public String validateAddIpAddress(final String address) {
        return Iterables.any(getIpAddresses(), input -> input.getAddress().equals(address))? "Already added": null;
    }
    //endregion

    //region > removeIpAddress (action)
    @MemberOrder(name="ipAddresses", sequence = "2")
    public void removeIpAddress(IpAddress ipAddress){
        this.ipAddresses.remove(ipAddress);
        container.removeIfNotAlready(ipAddress);
    }

    public String disableRemoveIpAddress(IpAddress ipAddress) {
        return getIpAddresses().isEmpty()? "No addresses to remove": null;
    }
    public Collection<IpAddress> choices0RemoveIpAddress() {
        return getIpAddresses();
    }
    //endregion

    //region > helpers
    @Programmatic
    public void clearIpAddresses() {
        final Set<IpAddress> ipAddresses = getIpAddresses();
        for (IpAddress ipAddress : ipAddresses) {
            removeIpAddress(ipAddress);
        }
    }
    //endregion

    //region > compareTo
    @Override
    public int compareTo(final Host other) {
        return ObjectContracts.compare(this, other, "name");
    }
    //endregion

    //region > injected services
    @javax.inject.Inject
    DomainObjectContainer container;
    @javax.inject.Inject
    ActionInvocationContext actionInvocationContext;

    //endregion

}
