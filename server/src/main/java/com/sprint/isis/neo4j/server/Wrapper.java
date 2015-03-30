package com.sprint.isis.neo4j.server;

import javax.annotation.PostConstruct;
import javax.jdo.Transaction;

import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;
import org.neo4j.kernel.GraphDatabaseAPI;
import org.neo4j.server.WrappingNeoServerBootstrapper;
import org.neo4j.server.configuration.Configurator;
import org.neo4j.server.configuration.ServerConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("deprecation")
public class Wrapper {
	
	WrappingNeoServerBootstrapper bootstrapper;
	Logger log = LoggerFactory.getLogger(Wrapper.class);
	private IsisJdoSupport isisJdoSupport;

	public void injectIsisJdoSupport(IsisJdoSupport isisJdoSupport) {
	    this.isisJdoSupport = isisJdoSupport;
	}

	@PostConstruct
	  public void init() {
		javax.jdo.PersistenceManager pm = isisJdoSupport.getJdoPersistenceManager();
		Transaction transaction = pm.currentTransaction();
		transaction.commit();
		pm.getDataStoreConnection().close();
		GraphDatabaseAPI graphDBService = (GraphDatabaseAPI)pm.getDataStoreConnection().getNativeConnection();
		
		Configurator config = new ServerConfigurator(graphDBService);
		bootstrapper = new WrappingNeoServerBootstrapper(graphDBService, config);
		Integer serverStatus = bootstrapper.start();
		log.info(String.format("NEO4J Server Status: %s", serverStatus));
		transaction.begin();
	  }
}
