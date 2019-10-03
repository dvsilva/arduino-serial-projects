package br.com.dvs.exception;

public class SendDataException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public SendDataException(String errorMessage) {
		super(errorMessage);
	}
}