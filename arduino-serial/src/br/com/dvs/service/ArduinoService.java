package br.com.dvs.service;



import br.com.dvs.domain.Arduino;
import br.com.dvs.domain.Operations;
import br.com.dvs.exception.PortInitializationException;
import br.com.dvs.exception.SendDataException;

/**
 * @author Danyllo
 */
public class ArduinoService {

	// Arduino COM port to connect
	private static final String COM_PORT = "COM4";
	// Transmission rate configured on Arduino
	private static final int TRANSMISSION_RATE = 9600;
	
	private Arduino arduino;

	private static ArduinoService singleton;

	/**
	 * CtrlProgram class Constructor method
	 * 
	 */
	public ArduinoService() {		
		initArduino();
	}

	/** 
	 * Method to initialize Arduino
	 */
	private void initArduino() {
		this.arduino = Arduino.getSingleton();
		
		try {
			this.arduino.initialize(COM_PORT, TRANSMISSION_RATE);
		} 
		catch (PortInitializationException e) {
			System.err.println("There was a problem initializing arduino: " + e.getMessage());
		}
	}

	/**
	 * Return the last data sent by the serial port
	 * 
	 * @return the last data collected
	 */
	public String getLastData() {
		return this.arduino.getLastInputValue();
	}
	
	/**
	 * Send command to serial port
	 * 
	 * @param operation
	 *            - Operation that will be executed
	 */
	public void execute(Operations operation) {
		try {
			this.arduino.execute(operation);
		} catch (SendDataException e) {
			System.err.println("There was a problem sending data to arduino: " + e.getMessage());
		}
	}

	public void terminate() {
		this.arduino.closePort();
		System.exit(0);
	}

	public Arduino getArduino() {
		return arduino;
	}

	public static ArduinoService getSingleton() {
		if (singleton == null)
			singleton = new ArduinoService();
		
		return singleton;
	}
}
