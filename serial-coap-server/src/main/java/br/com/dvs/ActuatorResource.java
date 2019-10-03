package br.com.dvs;

import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.coap.CoAP.ResponseCode;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import org.eclipse.californium.core.server.resources.CoapExchange;

import br.com.dvs.domain.OperationsEnum;
import br.com.dvs.service.ArduinoService;

public class ActuatorResource extends CoapResource {

	private ArduinoService service;

	public ActuatorResource(String name, ArduinoService service) {
		super(name);
		
		this.service = service;
		
		this.setObservable(true); // enable observing
		this.getAttributes().setObservable(); // mark observable in the Link-Format
	}
	
	@Override
	public void handlePUT(CoapExchange exchange) {
		String request = exchange.getRequestText();
		System.out.println("Request : " + request);

		OperationsEnum op = OperationsEnum.valueOf(request.toUpperCase());
		System.out.println("OperationsEnum : " + op);
		
		service.execute(op);
		
		this.changed(); // notify all observers
		
		// retorna mensagem de sucesso
		String response = getName() + " has been successful configured";
		exchange.respond(ResponseCode.CONTENT, "{ message: " + response + " }", MediaTypeRegistry.TEXT_PLAIN);
	}

}