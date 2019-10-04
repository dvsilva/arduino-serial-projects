package br.com.dvs;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import br.com.dvs.mqtt.Subscriber;

public class MqttController implements MqttCallback {

	private static final String SERVER = "tcp://localhost:1883";
	
	private Subscriber subscriber;
	
	public void publish(String topic, String message){
		
		try {
			String clientId = MqttAsyncClient.generateClientId();
	       
			//System.out.println("pub cliid: " + clientId + " - " + topic);
			MqttClient client = new MqttClient(SERVER, clientId);
	        client.connect();
	        
	        MqttMessage mqttMessage = new MqttMessage();
	        mqttMessage.setPayload(message.getBytes());
	        client.publish(topic, mqttMessage);
	        
	        client.disconnect();
	        System.out.println("publish finished");
	    } 
		catch (MqttException e) {
	        e.printStackTrace();
	    }
	}
	
	public void connectionLost(Throwable cause) {
	    // TODO Auto-generated method stub
	}

	public void messageArrived(String topic, MqttMessage message) throws Exception {
		subscriber.executeCallback(topic, message.toString());
	}

	public void deliveryComplete(IMqttDeliveryToken token) {
	    // TODO Auto-generated method stub
	}
	
	public void subscribe(String topic, Subscriber subscriber){
		this.subscriber = subscriber;
		
		try {
			String clientId = MqttAsyncClient.generateClientId();
			//System.out.println("sub cliid: " + clientId + " - " + topic);
			
			MqttClient client = new MqttClient(SERVER, clientId);
	        client.connect();
	        client.setCallback(this);
	        client.subscribe(topic);
	        
	        System.out.println("subscribe finished");
	    } 
		catch (MqttException e) {
	        e.printStackTrace();
	    }
	}
}