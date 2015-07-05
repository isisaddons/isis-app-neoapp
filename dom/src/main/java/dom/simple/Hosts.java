package dom.simple;

import java.util.List;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.SemanticsOf;

@DomainService(repositoryFor = Host.class)
@DomainServiceLayout(
        menuOrder = "10"
)
public class Hosts {

    //region > create (action)
    @MemberOrder(sequence = "2")
    public Host create(
            final @ParameterLayout(named="Name") String name) {
        final Host obj = container.newTransientInstance(Host.class);
        obj.setName(name);
        container.persistIfNotAlready(obj);
        return obj;
    }
    //endregion


    @Action(
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            bookmarking = BookmarkPolicy.AS_ROOT
    )
    @MemberOrder(sequence = "1")
    public List<Host> listAll() {
        return container.allInstances(Host.class);
    }

    //region > injected services

    @javax.inject.Inject
    DomainObjectContainer container;
    //endregion
}
