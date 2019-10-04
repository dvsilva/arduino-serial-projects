package br.com.dvs.mqtt;

public interface Subscriber {
	
	public void executeCallback(String topic, String message);

}
