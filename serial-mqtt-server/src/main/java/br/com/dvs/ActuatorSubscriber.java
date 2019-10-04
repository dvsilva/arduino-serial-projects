package br.com.dvs;

import br.com.dvs.domain.Operations;
import br.com.dvs.mqtt.Subscriber;
import br.com.dvs.service.ArduinoService;

public class ActuatorSubscriber implements Subscriber {

	private ArduinoService service;
	private MqttController controller;

	public ActuatorSubscriber() {
		this.service = ArduinoService.getSingleton();
		this.controller = new MqttController();
	}

	public static void main(String[] args) {
		ActuatorSubscriber subscriber = new ActuatorSubscriber();
		subscriber.start();
	}

	private void start() {
		//System.out.println("Subscribing");
		controller.subscribe("/danyllo/actuator", (Subscriber) this);
	}

	public void executeCallback(String topic, String message) {
		System.out.println("Message arrived on " + topic + ": " + message);
		
		if (topic.equalsIgnoreCase("/danyllo/actuator")) {
			Operations op = Operations.valueOf(message.toUpperCase());
			System.out.println("Operations : " + op);
			service.execute(op);
		}
	}
}
