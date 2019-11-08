package br.com.dvs.resources;

import java.util.Timer;
import java.util.TimerTask;

import br.com.dvs.MqttController;
import br.com.dvs.mqtt.Publisher;
import br.com.dvs.service.ArduinoService;

public class SensorPublisher implements Publisher {

	private ArduinoService service;
	private MqttController controller;

	public SensorPublisher() {
		this.service = ArduinoService.getSingleton();
		this.controller = new MqttController();
	}

	public static void main(String[] args) {
		SensorPublisher publisher = new SensorPublisher();
		publisher.start();
	}

	public void start() {
		Timer timer = new Timer();
		timer.schedule(new UpdateTask(), 0, 1000);
	}

	private class UpdateTask extends TimerTask {
		@Override
		public void run() {
			doPublish();
		}
	}

	public void doPublish() {
		String sensorData = service.getLastData();
		// int nextInt = new Random().nextInt(100);
		// String sensorData = String.valueOf(nextInt) + "°";
		// System.out.println("Publishing " + sensorData);
		controller.publish("/esri/sensor", sensorData);
	}

}