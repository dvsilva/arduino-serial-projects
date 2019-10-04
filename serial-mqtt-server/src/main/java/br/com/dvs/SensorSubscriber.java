package br.com.dvs;

import br.com.dvs.mqtt.Subscriber;

public class SensorSubscriber implements Subscriber {

	private MqttController controller;

	public SensorSubscriber() {
		this.controller = new MqttController();
	}

	public static void main(String[] args) {
		SensorSubscriber subscriber = new SensorSubscriber();
		subscriber.start();
	}

	private void start() {
		//System.out.println("Subscribing");
		controller.subscribe("/danyllo/sensor", (Subscriber) this);
	}

	public void executeCallback(String topic, String message) {
		System.out.println("Message arrived on " + topic + ": " + message);

		if (topic.equalsIgnoreCase("/danyllo/sensor")) {
			System.out.println("Sensor data recieved " + message);
		} 
	}
}
