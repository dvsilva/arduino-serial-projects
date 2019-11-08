package br.com.dvs.resources;

import br.com.dvs.MqttController;
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

	public void start() {
		//System.out.println("Subscribing");
		controller.subscribe("/esri/actuator", (Subscriber) this);
	}

	public void executeCallback(String topic, String message) {
		System.out.println("Message arrived on " + topic + ": " + message);
		
		if (topic.equalsIgnoreCase("/esri/actuator")) {
			Operations op = Operations.valueOf(message.toUpperCase());
			System.out.println("Operations : " + op);
			service.execute(op);
		}
	}
}
