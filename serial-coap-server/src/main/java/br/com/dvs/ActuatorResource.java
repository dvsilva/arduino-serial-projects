package br.com.dvs;

import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.coap.CoAP.ResponseCode;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import org.eclipse.californium.core.server.resources.CoapExchange;

import br.com.dvs.domain.Operations;
import br.com.dvs.domain.State;
import br.com.dvs.service.ArduinoService;

public class ActuatorResource extends CoapResource {

	private ArduinoService service;
	private State state;

	public ActuatorResource(String name, ArduinoService service) {
		super(name);
		
		this.state = State.LOW;
		this.service = service;
		
		this.setObservable(true); // enable observing
		this.getAttributes().setObservable(); // mark observable in the Link-Format
	}

	@Override
	public void handleGET(CoapExchange exchange) {
		exchange.respond(ResponseCode.CONTENT, "{ state: " + state + " }", MediaTypeRegistry.TEXT_PLAIN);
	}
	
	@Override
	public void handlePUT(CoapExchange exchange) {
		String request = exchange.getRequestText();
		//System.out.println("Request : " + request);

		Operations op = Operations.valueOf(request.toUpperCase());
		//System.out.println("Operation : " + op);
		
		service.execute(op);
		
		this.state = Operations.TURN_ON == op ? State.HIGH : State.LOW;
		this.changed(); // notify all observers
		
		// retorna mensagem de sucesso
		String response = getName() + " has been successful configured";
		System.out.println(response);
		exchange.respond(ResponseCode.CONTENT, "{ message: " + response + " }", MediaTypeRegistry.TEXT_PLAIN);
	}

}