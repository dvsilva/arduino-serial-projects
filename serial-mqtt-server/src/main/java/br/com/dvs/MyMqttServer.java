package br.com.dvs;

import br.com.dvs.resources.ActuatorSubscriber;
import br.com.dvs.resources.SensorPublisher;

public class MyMqttServer {

	public static void main(String[] args) {
		ActuatorSubscriber subscriber = new ActuatorSubscriber();
		subscriber.start();
		
		SensorPublisher publisher = new SensorPublisher();
		publisher.start();
	}
}