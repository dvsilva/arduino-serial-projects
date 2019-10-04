package br.com.dvs.control;

import br.com.dvs.domain.Operations;
import br.com.dvs.service.ArduinoService;
import br.com.dvs.view.MainView;

/**
 * @author Danyllo
 */
public class CtrlProgram {

	private ArduinoService service;
	
	@SuppressWarnings("unused")
	private MainView view;

	/**
	 * CtrlProgram class Constructor method
	 * 
	 */
	public CtrlProgram() {		
		this.view = new MainView(this);
		this.service = ArduinoService.getSingleton();
	}

	/**
	 * Return the last data sent by the serial port
	 * 
	 * @return the last data collected
	 */
	public String getLastData() {
		return this.service.getLastData();
	}
	/**
	 * Send command to serial port
	 * 
	 * @param operation
	 *            - Operation that will be executed
	 */
	public void execute(Operations operation) {
		this.service.execute(operation);
	}

	public void terminate() {
		System.exit(0);
	}

	public ArduinoService getArduinoService() {
		return service;
	}
	
	public static void main(String[] args) {
		new CtrlProgram();
	}
}
