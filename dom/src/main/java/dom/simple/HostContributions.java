package dom.simple;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.InvokeOn;
import org.apache.isis.applib.annotation.InvokedOn;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.services.actinvoc.ActionInvocationContext;

@DomainService(
        nature = NatureOfService.VIEW_CONTRIBUTIONS_ONLY
)
public class HostContributions {

    @Action(
            invokeOn = InvokeOn.OBJECT_AND_COLLECTION
    )
    public Object delete(
            final Host host) {
        hostRepository.delete(host);
        return actionInvocationContext.getInvokedOn() == InvokedOn.COLLECTION && actionInvocationContext.isLast()
                ? hostRepository.listAll()
                : null;
    }

    @javax.inject.Inject
    HostRepository hostRepository;

    @javax.inject.Inject
    ActionInvocationContext actionInvocationContext;

}
