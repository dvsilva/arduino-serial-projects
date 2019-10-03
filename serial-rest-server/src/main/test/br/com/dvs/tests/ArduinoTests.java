package br.com.dvs.tests;

import java.net.URI;
import java.net.URISyntaxException;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class ArduinoTests {

	private static final Integer SERVER_PORT = 8080;
	private static final String BASE_URL = "http://localhost:" + SERVER_PORT + "/";
	private static final String URL_SENSOR = BASE_URL + "sensor";
	private static final String URL_ACTUATOR = BASE_URL + "actuator";
	private static final String URL_ACTUATOR_TURN_ON = BASE_URL + "actuator/turn_on";
	private static final String URL_ACTUATOR_TURN_OFF = BASE_URL + "actuator/turn_off";
	
	@Test
	public void testGetSensorDataSuccess() throws URISyntaxException {
	    RestTemplate restTemplate = new RestTemplate();
	    
	    URI uri = new URI(URL_SENSOR);
	 
	    ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);
	     
	    //Verify request succeed
	    Assert.assertEquals("200", result.getStatusCode().toString());
	    Assert.assertEquals(true, result.getBody().contains("result"));
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Test
	public void testChangeActuatorTurnOnSuccess() throws URISyntaxException {
	    RestTemplate restTemplate = new RestTemplate();
	    
	    URI uri = new URI(URL_ACTUATOR_TURN_ON);

	    ResponseEntity<String> result = restTemplate.exchange(uri, HttpMethod.PUT, null, String.class);
	     
	    //Verify request succeed
	    Assert.assertEquals("200", result.getStatusCode().toString());
	    Assert.assertEquals(true, result.getBody().contains("result"));
	}

	@Test
	public void testChangeActuatorTurnOffSuccess() throws URISyntaxException {
	    RestTemplate restTemplate = new RestTemplate();
	    
	    URI uri = new URI(URL_ACTUATOR_TURN_OFF);

	    ResponseEntity<String> result = restTemplate.exchange(uri, HttpMethod.PUT, null, String.class);
	     
	    //Verify request succeed
	    Assert.assertEquals("200", result.getStatusCode().toString());
	    Assert.assertEquals(true, result.getBody().contains("result"));
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Test
	public void testChangeActuatorTurnOnSuccessOnBody() throws URISyntaxException {
	    RestTemplate restTemplate = new RestTemplate();
	    
	    URI uri = new URI(URL_ACTUATOR);

	    HttpHeaders headers = new HttpHeaders();
	    HttpEntity<String> requestEntity = new HttpEntity<String>("turn_on", headers);
	    ResponseEntity<String> result = restTemplate.exchange(uri, HttpMethod.PUT, requestEntity, String.class);
	     
	    //Verify request succeed
	    Assert.assertEquals("200", result.getStatusCode().toString());
	    Assert.assertEquals(true, result.getBody().contains("result"));
	}

	@Test
	public void testChangeActuatorTurnOffSuccessOnBody() throws URISyntaxException {
	    RestTemplate restTemplate = new RestTemplate();
	    
	    URI uri = new URI(URL_ACTUATOR);

	    HttpHeaders headers = new HttpHeaders();
	    HttpEntity<String> requestEntity = new HttpEntity<String>("turn_off", headers);
	    ResponseEntity<String> result = restTemplate.exchange(uri, HttpMethod.PUT, requestEntity, String.class);
	     
	    //Verify request succeed
	    Assert.assertEquals("200", result.getStatusCode().toString());
	    Assert.assertEquals(true, result.getBody().contains("result"));
	}
}
