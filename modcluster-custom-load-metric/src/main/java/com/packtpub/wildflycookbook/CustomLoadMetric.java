package com.packtpub.wildflycookbook;

import org.jboss.modcluster.container.Connector;
import org.jboss.modcluster.container.Context;
import org.jboss.modcluster.container.Engine;
import org.jboss.modcluster.container.Host;
import org.jboss.modcluster.load.metric.impl.AbstractLoadMetric;
import org.jboss.modcluster.load.metric.impl.ActiveSessionsLoadMetric;

import java.util.Iterator;

/**
 * Created by foogaro on 26/11/14.
 */
public class CustomLoadMetric extends ActiveSessionsLoadMetric {

    private int fixLoad;
    private int fixLoad;

    public int getFixLoad() {
        return fixLoad;
    }

    public void setFixLoad(int fixLoad) {
        this.fixLoad = fixLoad;
    }

    @Override
    public double getLoad(Engine engine) throws Exception {
        final double loadAS = super.getLoad(engine);

        if (loadAS <= 1000-200)
            return loadAS;
        else
            return 1000;


        System.out.println("");
        System.out.println("returning a fixed load factor of " + fixLoad);
        final double busyLoad = getBusyLoad(engine);
        System.out.println("busyLoad -> " + busyLoad);

        int sessions = 0;
        Iterator i$ = engine.getHosts().iterator();

        while(i$.hasNext()) {
            Host host = (Host)i$.next();

            Context context;
            for(Iterator i$1 = host.getContexts().iterator(); i$1.hasNext(); sessions += context.getActiveSessionCount()) {
                context = (Context)i$1.next();
                System.out.println("context.getPath() -> " + context.getPath());
            }
        }
        System.out.println("sessions -> " + sessions);



        final double kindOfRandom = Math.random()*100;
        System.out.println("kindOfRandom -> " + kindOfRandom);

        if (kindOfRandom < 30) {
            System.out.println("returning a load factor of '-1'");
            return -1;
        } else if (kindOfRandom < 60) {
            System.out.println("returning a load factor of '0'");
            return 0;
        } else {
            System.out.println("returning busy load factor");
            return busyLoad;
        }
    }

    private double getBusyLoad(Engine engine) throws Exception {
        double busy = 0.0D;
        double max = 0.0D;
        boolean useCapacity = false;

        System.out.println("Getting busy...");
        int maxThreads;
        for(Iterator i$ = engine.getConnectors().iterator(); i$.hasNext(); max += (double)maxThreads) {
            Connector connector = (Connector)i$.next();
            busy += (double)connector.getBusyThreads();
            maxThreads = connector.getMaxThreads();
            if(maxThreads == -1) {
                useCapacity = true;
            }
        }

        return useCapacity?busy:busy / max;
    }

}
