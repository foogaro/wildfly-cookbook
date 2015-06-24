package com.packtpub.demo.cluster.mdb;

import java.io.Serializable;
import java.util.HashMap;

import javax.ejb.MessageDriven;
import javax.ejb.ActivationConfigProperty;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

@MessageDriven(activationConfig = {
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
		@ActivationConfigProperty(propertyName = "destination", propertyValue = "queue/DistributedQueue"),
		@ActivationConfigProperty(propertyName = "maxSession", propertyValue = "1") })
public class MessageDrivenBean implements MessageListener {

	public void onMessage(Message message) {
		int count = 1;
		try {
			ObjectMessage objectMessage = (ObjectMessage) message;
			Serializable object = objectMessage.getObject();
			@SuppressWarnings("unchecked")
			HashMap<String, Serializable> map = (HashMap<String, Serializable>) object;
			String text = (String) map.get("message");
			count = (Integer) map.get("count");
			long delay = (Long) map.get("delay");
			System.out.println(count + ": " + text);
			Thread.sleep(delay);
		} catch (Exception e) {
			System.out.println("handle message " + count + " met " + e.getMessage());
		} 
	}

}
