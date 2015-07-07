package neoapp.dom.module.host;

import java.util.List;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.SemanticsOf;

@DomainService(
        nature = NatureOfService.VIEW
)
@DomainServiceLayout(
        menuOrder = "10",
        menuBar = DomainServiceLayout.MenuBar.PRIMARY,
        named = "Hosts"
)
public class HostsMenu {

    //region > create (action)
    public Host create(
            final @ParameterLayout(named="Name") String name) {
        return hostRepository.create(name);
    }
    //endregion

    //region > listAll (action)
    @Action(
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            bookmarking = BookmarkPolicy.AS_ROOT
    )
    @MemberOrder(sequence = "3")
    public List<Host> listAll() {
        return hostRepository.listAll();
    }
    //endregion

    //region > injected services
    @javax.inject.Inject
    DomainObjectContainer container;
    @javax.inject.Inject
    HostRepository hostRepository;
    //endregion
}
