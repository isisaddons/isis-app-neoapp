package dom.simple;

import java.util.List;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;

@DomainService(
        nature = NatureOfService.DOMAIN,
        repositoryFor = Host.class
)
public class HostRepository {

    @Programmatic
    public Host create(final String name) {
        final Host obj = container.newTransientInstance(Host.class);
        obj.setName(name);
        container.persistIfNotAlready(obj);
        return obj;
    }

    @Programmatic
    public void delete(
            final Host host) {
        host.clearIpAddresses();
        container.removeIfNotAlready(host);
    }

    @Programmatic
    public List<Host> listAll() {
        return container.allInstances(Host.class);
    }

    @javax.inject.Inject
    DomainObjectContainer container;

}
