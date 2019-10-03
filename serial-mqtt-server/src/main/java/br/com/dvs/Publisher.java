package br.com.dvs;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import br.com.dvs.service.ArduinoService;

public class Publisher {

	private ArduinoService service;
	private MqttController controller;
	
	public Publisher() {
		this.service = ArduinoService.getSingleton();
		this.controller = new MqttController();
	}
	
	public static void main(String[] args) {
		Publisher publisher = new Publisher();
		publisher.start();
	}
	
	private void start() {
		Timer timer = new Timer();
		timer.schedule(new UpdateTask(), 0, 1000);
	}

	private class UpdateTask extends TimerTask {

		@Override
		public void run() {
			// String sensorData = service.getLastData();
			int nextInt = new Random().nextInt(100);
			String sensorData = String.valueOf(nextInt) + "°";
			System.out.println("Publicando " + sensorData);
			controller.publish("/danyllo/sensors", sensorData);
		}
	}

}
