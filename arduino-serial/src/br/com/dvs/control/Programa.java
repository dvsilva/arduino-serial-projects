package br.com.dvs.control;

import br.com.dvs.domain.Arduino;
import br.com.dvs.domain.OperationsEnum;
import br.com.dvs.exception.SendDataException;

public class Programa {

	public static void main(String[] args) {
		try {
			Arduino arduino = Arduino.getSingleton();
			arduino.execute(OperationsEnum.TURN_ON);
		} 
		catch (SendDataException e) {
			e.printStackTrace();
		}
	}
}
