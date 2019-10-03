package br.com.dvs;

import br.com.dvs.domain.OperationsEnum;
import br.com.dvs.service.ArduinoService;

public class Subscriber {

	private ArduinoService service;
	private MqttController controller;

	public Subscriber() {
		this.service = ArduinoService.getSingleton();
		this.controller = new MqttController();
	}

	public static void main(String[] args) {
		Subscriber subscriber = new Subscriber();
		subscriber.start();
	}

	private void start() {
		System.out.println("Realizando subscribe");
		controller.subscribe("/danyllo/actuator", this);
	}

	public void executeCallback(String topic, String message) {
		System.out.println("Message arrived on " + topic + ": " + message);

		OperationsEnum op = OperationsEnum.valueOf(message.toUpperCase());
		System.out.println("OperationsEnum : " + op);
		service.execute(op);
	}
}
