package com.packtpub.wildflycookbook;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import java.util.Properties;

public class JMSProducer {

    private static final Integer NUM_OF_MESSAGES = 10;
    private static final String CONNECTION_FACTORY = "jms/RemoteConnectionFactory";
    private static final String DESTINATION = "jms/queue/test";
    private static final String DEFAULT_USERNAME = "jmsuser";
    private static final String DEFAULT_PASSWORD = "jmsuser.2015";
    private static final String INITIAL_CONTEXT_FACTORY = "org.jboss.naming.remote.client.InitialContextFactory";
    private static final String PROVIDER_URL = "http-remoting://localhost:8180";

    public static void main(String[] args) throws Exception {
        final StringBuilder msg = new StringBuilder();
        if (args != null && args.length > 0) {
            for (String arg : args) {
                msg.append(arg);
                msg.append(" ");
            }
        } else {
            msg.append("A message by WildFly Cookbook");
        }

        ConnectionFactory connectionFactory = null;
        Connection connection = null;
        Session session = null;
        MessageProducer producer = null;
        MessageConsumer consumer = null;
        Destination destination = null;
        TextMessage message = null;
        Context context = null;

        try {
            // Set up the context for the JNDI lookup
            final Properties env = new Properties();
            env.put(Context.INITIAL_CONTEXT_FACTORY, INITIAL_CONTEXT_FACTORY);
            env.put(Context.PROVIDER_URL, System.getProperty(Context.PROVIDER_URL, PROVIDER_URL));
            env.put(Context.SECURITY_PRINCIPAL, System.getProperty("username", DEFAULT_USERNAME));
            env.put(Context.SECURITY_CREDENTIALS, System.getProperty("password", DEFAULT_PASSWORD));
            context = new InitialContext(env);

            String connectionFactoryString = System.getProperty("connection.factory", CONNECTION_FACTORY);
            System.out.println("Attempting to acquire connection factory \"" + connectionFactoryString + "\"");
            connectionFactory = (ConnectionFactory) context.lookup(connectionFactoryString);
            System.out.println("Found connection factory \"" + connectionFactoryString + "\" in JNDI");

            String destinationString = System.getProperty("destination", DESTINATION);
            System.out.println("Attempting to acquire destination \"" + destinationString + "\"");
            destination = (Destination) context.lookup(destinationString);
            System.out.println("Found destination \"" + destinationString + "\" in JNDI");

            // Create the JMS connection, session, producer, and consumer
            connection = connectionFactory.createConnection(System.getProperty("username", DEFAULT_USERNAME), System.getProperty("password", DEFAULT_PASSWORD));
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            producer = session.createProducer(destination);
            // consumer = session.createConsumer(destination);
            connection.start();

            System.out.println("Sending messages with content: " + msg.toString());

            // Send the specified number of messages
            for (int i = 0; i < NUM_OF_MESSAGES; i++) {
                message = session.createTextMessage(i+msg.toString());
                producer.send(message);
                System.out.println("\t\tSending messages with content ["+i+"]: " + msg.toString());
                Thread.sleep(1000);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (context != null) {
                context.close();
            }

            // closing the connection takes care of the session, producer, and consumer
            if (connection != null) {
                connection.close();
            }
        }

    }
}
