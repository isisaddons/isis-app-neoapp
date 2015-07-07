package com.sprint.isis.neo4j.server;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.jdo.Transaction;

import org.neo4j.kernel.GraphDatabaseAPI;
import org.neo4j.server.WrappingNeoServerBootstrapper;
import org.neo4j.server.configuration.Configurator;
import org.neo4j.server.configuration.ServerConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;

@DomainService(
        nature = NatureOfService.DOMAIN
)
public class Wrapper {

    @SuppressWarnings("deprecation")
    WrappingNeoServerBootstrapper bootstrapper;
    Logger log = LoggerFactory.getLogger(Wrapper.class);

    @SuppressWarnings("deprecation")
    @PostConstruct
    public void init() {

        javax.jdo.PersistenceManager pm = isisJdoSupport.getJdoPersistenceManager();
        Transaction transaction = pm.currentTransaction();
        transaction.commit();
        pm.getDataStoreConnection().close();

        GraphDatabaseAPI graphDBService = (GraphDatabaseAPI) pm.getDataStoreConnection().getNativeConnection();

        Configurator config = new ServerConfigurator(graphDBService);
        bootstrapper = new WrappingNeoServerBootstrapper(graphDBService, config);

        Integer serverStatus = bootstrapper.start();
        log.info(String.format("NEO4J Server Status: %s", serverStatus));
        transaction.begin();
    }

    @PreDestroy
    public void shutdown() {
        // unused...
    }

    @Inject
    private IsisJdoSupport isisJdoSupport;

}
