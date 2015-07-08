package neoapp.dom.services.titling;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.jdo.listener.InstanceLifecycleEvent;
import javax.jdo.listener.StoreLifecycleListener;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;

/**
 * Ensures that any entities that implement {@link ObjectWithPersistedTitle} have their persisted title
 * updated to be their title as per {@link DomainObjectContainer#titleOf(Object) the framework}.
 */
@RequestScoped
@DomainService(nature = NatureOfService.VIEW_CONTRIBUTIONS_ONLY)
public class TitlingService {

    @PostConstruct
    public void init() {
        isisJdoSupport.getJdoPersistenceManager().addInstanceLifecycleListener(
            new StoreLifecycleListener() {
                @Override
                public void preStore(final InstanceLifecycleEvent event) {
                    final Object persistentInstance = event.getPersistentInstance();
                    if (persistentInstance instanceof ObjectWithPersistedTitle) {
                        final ObjectWithPersistedTitle objectWithPersistedTitle = (ObjectWithPersistedTitle) persistentInstance;
                        objectWithPersistedTitle.setTitle(container.titleOf(objectWithPersistedTitle));
                    }
                }
                @Override
                public void postStore(final InstanceLifecycleEvent event) {
                }
            }, null);
    }
    @Inject
    private IsisJdoSupport isisJdoSupport;
    @Inject
    private DomainObjectContainer container;
}
