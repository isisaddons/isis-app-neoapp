package dom.simple;

import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.DatastoreIdentity;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Unique;
import javax.jdo.annotations.Version;
import javax.jdo.annotations.VersionStrategy;

import com.google.common.collect.Iterables;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.CollectionLayout;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.RenderType;
import org.apache.isis.applib.annotation.Title;

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
public class Host {

    // region > Name property
    private String name;

    @Column(allowsNull="false")
    @Title(sequence="1")
    @MemberOrder(sequence="1")
    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }
    //endregion

    // region > IpAddresses property
    private Set<IpAddress> ipAddresses = new TreeSet<>();

    @Column(allowsNull="false")
    @CollectionLayout(
            render = RenderType.EAGERLY
    )
    public Set<IpAddress> getIpAddresses() {
        return ipAddresses;
    }
    public void setIpAddresses(final Set<IpAddress> ipAddresses) {
        this.ipAddresses = ipAddresses;
    }
    //endregion

    //region > addIpAddress (action)
    @MemberOrder(name="ipAddresses", sequence = "1")
    @ActionLayout(named = "Add")
    public void addIpAddress(@ParameterLayout(named = "Address") String address){
        final IpAddress ipAddress = container.newTransientInstance(IpAddress.class);
        ipAddress.setAddress(address);
        this.ipAddresses.add(ipAddress);
        container.persistIfNotAlready(ipAddress);
    }

    public String validateAddIpAddress(final String address) {
        return Iterables.any(getIpAddresses(), input -> input.getAddress().equals(address))? "Already added": null;
    }
    //endregion

    //region > removeIpAddress (action)
    @MemberOrder(name="ipAddresses", sequence = "2")
    @ActionLayout(named = "Remove")
    public void removeIpAddress(IpAddress ipAddress){
        this.ipAddresses.remove(ipAddress);
    }

//    public String disableRemoveIpAddress() {
//        return getIpAddresses().isEmpty()? "No addresses to remove": null;
//    }
    public Collection<IpAddress> choices0RemoveIpAddress() {
        return getIpAddresses();
    }
    //endregion

    @javax.inject.Inject
    DomainObjectContainer container;
    //endregion

}
