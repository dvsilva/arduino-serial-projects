package br.com.dvs.exception;

public class PortInitializationException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public PortInitializationException(String errorMessage) {
		super(errorMessage);
	}
}