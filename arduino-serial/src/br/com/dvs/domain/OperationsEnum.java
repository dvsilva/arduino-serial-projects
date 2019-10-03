package br.com.dvs.domain;

/**
 * @author Danyllo
 */
public enum OperationsEnum {

	TURN_ON(1), TURN_OFF(0);

	private int value;

	private OperationsEnum(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

}
