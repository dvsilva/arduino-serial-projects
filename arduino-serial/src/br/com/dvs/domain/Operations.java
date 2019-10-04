package br.com.dvs.domain;

/**
 * @author Danyllo
 */
public enum Operations {
	
	TURN_OFF(1),
	TURN_ON(2);

	private int value;

	private Operations(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

}
