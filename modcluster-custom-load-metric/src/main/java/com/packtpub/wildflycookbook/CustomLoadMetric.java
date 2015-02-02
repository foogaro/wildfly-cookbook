package com.packtpub.wildflycookbook;

import org.jboss.modcluster.container.Engine;
import org.jboss.modcluster.load.metric.impl.AbstractLoadMetric;

public class CustomLoadMetric extends AbstractLoadMetric {

	private int alertConnection;
	private int maxCapacity;

    public int getAlertConnection() {
        return alertConnection;
    }

    public void setAlertConnection(int alertConnection) {
        this.alertConnection = alertConnection;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    @Override
    public double getLoad(Engine engine) throws Exception {
    	final double usedConnections = getUsedConnectionsFromConnectionManager();
    	
    	if (usedConnections > alertConnection) {
    		return maxCapacity;
    	} else {
    		return usedConnections;
    	}
    }
    
    private double getUsedConnectionsFromConnectionManager() {
    	//TODO: implementare la logica per il recupero del numero di connessioni utilizzate.
    	return 0d;
    }
}