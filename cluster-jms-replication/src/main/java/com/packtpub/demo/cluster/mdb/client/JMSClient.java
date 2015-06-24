package com.packtpub.demo.cluster.mdb.client;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Properties;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class JMSClient {
	
	private Context getContext() throws NamingException {

		final Properties env = new Properties();
		env.put(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.naming.remote.client.InitialContextFactory");
		env.put(Context.PROVIDER_URL, "http-remoting://127.0.0.1:8080");
		env.put(Context.SECURITY_PRINCIPAL,"democlient");
		env.put(Context.SECURITY_CREDENTIALS,"password1!");

		return new InitialContext(env);
	}
	
	private Context context;

	private void execute() throws Exception {

		context = getContext();
		
		ConnectionFactory connectionFactory = null;
        Connection connection = null;
        Session session = null;
        MessageProducer producer = null;
        Destination destination = null;
        
        try {
			connectionFactory = (ConnectionFactory) context.lookup("jms/RemoteConnectionFactory");
			System.out.println("Acquiring connection factory success, " + connectionFactory);
			
			destination = (Destination) context.lookup("jms/queue/DistributedQueue");
			System.out.println("Acquiring destination success, " + destination);
			
			connection = connectionFactory.createConnection("democlient", "password1!");
			System.out.println("Creating connection success, " + connection);
			
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			System.out.println("Creating session success, " + session);
			
            producer = session.createProducer(destination);
            System.out.println("Creating producer success, " + producer);
            
            connection.start();
            
            HashMap<String, Serializable> map = new HashMap<String, Serializable>();
            map.put( "delay", new Long(1000 * 10) );
            map.put( "message", "WildFly 9 HornetQ Messaging High Available" );
            System.out.println("Send 10 messages to DistributedQueue");
            for (int index = 1; index <= 10; index++) {
				map.put( "count", index );
				ObjectMessage objectMessage = session.createObjectMessage();
				objectMessage.setObject( map );
				producer.send( objectMessage );
			}
            
            System.out.println("JMSClient exit");
		} catch (Exception e) {
			throw e;
		} finally {

			if (producer != null) {
				producer.close();
			}

			if (session != null) {
				session.close();
			}

			if (connection != null) {
				connection.close();
			}
		}

	}
	
	public static void main(String[] args) throws Exception {
		new JMSClient().execute();
	}

}
