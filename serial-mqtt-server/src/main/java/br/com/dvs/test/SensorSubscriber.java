package br.com.dvs.test;

import br.com.dvs.MqttController;
import br.com.dvs.mqtt.Subscriber;

public class SensorSubscriber implements Subscriber {

	private MqttController controller;

	public SensorSubscriber() {
		this.controller = new MqttController();
	}

	public void start() {
		//System.out.println("Subscribing");
		controller.subscribe("/esri/sensor", (Subscriber) this);
	}

	public void executeCallback(String topic, String message) {
		System.out.println("Message arrived on " + topic + ": " + message);

		if (topic.equalsIgnoreCase("/esri/sensor")) {
			System.out.println("Sensor data recieved " + message);
		} 
	}
}
