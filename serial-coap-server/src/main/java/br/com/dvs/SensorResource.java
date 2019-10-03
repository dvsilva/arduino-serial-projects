package br.com.dvs;

import java.util.Timer;
import java.util.TimerTask;

import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.coap.CoAP.ResponseCode;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import org.eclipse.californium.core.server.resources.CoapExchange;

import br.com.dvs.service.ArduinoService;

public class SensorResource extends CoapResource {
	
	private ArduinoService service;
	private String sensorData;

	public SensorResource(String name, ArduinoService service) {
		super(name);
		this.service = service;
		
		this.setObservable(true); // enable observing
		this.getAttributes().setObservable(); // mark observable in the Link-Format

		// schedule a periodic update task, otherwise let events call changed()
		Timer timer = new Timer();
		timer.schedule(new UpdateTask(), 0, 1000);
	}

	private class UpdateTask extends TimerTask {

		@Override
		public void run() {
			sensorData = service.getLastData();
			//sensorData = "32°";
			changed(); // notify all observers
		}
	}

	@Override
	public void handleGET(CoapExchange exchange) {
		exchange.respond(ResponseCode.CONTENT, this.sensorData, MediaTypeRegistry.TEXT_PLAIN);
	}
}
