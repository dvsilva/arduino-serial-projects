package br.com.dvs;

import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapResponse;
import org.eclipse.californium.core.coap.CoAP.Code;
import org.eclipse.californium.core.coap.Request;

public class MyCoapClient {

	public static void main(String[] args) {
		// TESTING SENSOR
		CoapClient client = new CoapClient("coap://127.0.0.1:5683/sensor");
		Request request = new Request(Code.GET);

		// Synchronously send the GET message (blocking call)
		CoapResponse coapResp = client.advanced(request);
		// The "CoapResponse" message contains the response.
		
		System.out.println(coapResp.getResponseText());
		//System.out.println(Utils.prettyPrint(coapResp));

		// TESTING ACTUATOR
		client = new CoapClient("coap://127.0.0.1:5683/actuator");
		request = new Request(Code.PUT);
		request.setPayload("turn_on");

		coapResp = client.advanced(request);
		System.out.println(coapResp.getResponseText());
		
		client = new CoapClient("coap://127.0.0.1:5683/actuator");
		request = new Request(Code.PUT);
		request.setPayload("turn_off");

		coapResp = client.advanced(request);
		System.out.println(coapResp.getResponseText());
	}
}
