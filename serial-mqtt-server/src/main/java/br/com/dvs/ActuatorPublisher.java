package br.com.dvs;

import br.com.dvs.mqtt.Publisher;

public class ActuatorPublisher implements Publisher {

	private MqttController controller;

	public ActuatorPublisher() {
		this.controller = new MqttController();
	}

	public static void main(String[] args) {
		ActuatorPublisher publisher = new ActuatorPublisher();
		publisher.doPublish();
	}

	public void doPublish() {
		controller.publish("/danyllo/actuator", "turn_off");
	}

}
