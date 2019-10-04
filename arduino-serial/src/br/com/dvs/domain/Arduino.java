package br.com.dvs.domain;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import br.com.dvs.exception.PortInitializationException;
import br.com.dvs.exception.SendDataException;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

/**
 * @author Danyllo
 */
public class Arduino implements SerialPortEventListener {

	private static Arduino singleton = null;
	
	private int rate;
	private String portName;

	private SerialPort port;

	private InputStream serialInp;
	private BufferedReader input;
	private OutputStream serialOut;
	
	private String lastInputValue;

	/**
	 * Arduino class Constructor method
	 * 
	 */
	public Arduino() {
	}

	/**
	 * Communication initialization method
	 * 
	 * @param port
	 *            - COM port that will be used to send data to arduino
	 * @param taxa
	 *            - Serial port throughput is usually 9600
	 *            
	 * For Windows:
	 * this.portController = new CtrlPortCOM(COM_PORT, TRANSMISSION_RATE);
	 * For Linux:
	 * this.portController = new CtrlPortaCOM("/dev/ttyUSB0",9600);
	 * 
	 * @throws PortInitializationException
	 */
	public void initialize(String portName, int rate) throws PortInitializationException {
		this.portName = portName;
		this.rate = rate;
		
		try {
			// defines a portId variable of type CommPortIdentifier to perform serial communication.
			CommPortIdentifier portId = CommPortIdentifier.getPortIdentifier(this.portName);
			
			// open COM port
			port = (SerialPort) portId.open("Serial communication", this.rate);
			
			serialInp = port.getInputStream();
			input = new BufferedReader(new InputStreamReader(serialInp));
			
			serialOut = port.getOutputStream();
			
			port.setSerialPortParams(this.rate, // serial port throughput
					SerialPort.DATABITS_8, // 10-bit rate 8 (push)
					SerialPort.STOPBITS_1, // 10-bit Rate 1 (receive)
					SerialPort.PARITY_NONE); // receive and send data
			
			// add event listeners
			port.addEventListener(this);
			port.notifyOnDataAvailable(true);
		}
		catch (Exception e) {
			// UnsupportedCommOperationException, PortInUseException, TooManyListenersException, IOException and NoSuchPortException 
			e.printStackTrace();
			throw new PortInitializationException(e.getMessage());
		}	
		catch (UnsatisfiedLinkError e) {
			e.printStackTrace();
		}
	}

	/**
	 * Handle an event on the serial port. Read the data and print it.
	 */
	public synchronized void serialEvent(SerialPortEvent oEvent) {
		if (oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
			try {
				this.lastInputValue= input.readLine();
				//System.out.println(lastInputValue);
			} 
			catch (Exception e) {
				System.err.println("Could not retrieve data from COM port");
				System.err.println(e.toString());
			}
		}
		// Ignore all the other eventTypes, but you should consider the other ones.
	}
	
	/**
	 * Method that closes communication with the serial port
	 */
	public void closePort() {
		try {
			serialInp.close();
			serialOut.close();
			
			port.removeEventListener();
			port.close();
		} 
		catch (IOException e) {
			System.err.println("Could not close COM port");
			System.err.println(e.toString());
		}
	}

	/**
	 * Send command to serial port
	 * 
	 * @param operation
	 *            - Operation that will be executed
	 * @throws SendDataException 
	 */
	public void execute(Operations operation) throws SendDataException {
		sendData(operation.getValue());
	}
	
	/**
	 * @param value
	 *            - Value to send by serial port
	 * @throws SendDataException 
	 */
	public void sendData(int value) throws SendDataException {
		try {
			serialOut.write(value);
		} 
		catch (IOException ex) {
			throw new SendDataException("Unable to send data: " + value );
		}
	}

	/**
	 * Return the last data sent by the serial port
	 * 
	 * @return the last data collected
	 */
	public String getLastInputValue() {
		return lastInputValue;
	}

	public void setLastInputValue(String lastInputValue) {
		this.lastInputValue = lastInputValue;
	}

	public static Arduino getSingleton() {
		if (singleton == null)
			singleton = new Arduino();
		
		return singleton;
	}
}
