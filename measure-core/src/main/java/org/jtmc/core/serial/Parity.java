package org.jtmc.core.serial;

import com.fazecast.jSerialComm.SerialPort;

public enum Parity {
	NONE(SerialPort.NO_PARITY),
	EVEN(SerialPort.EVEN_PARITY),
	ODD(SerialPort.ODD_PARITY),
	MARK(SerialPort.MARK_PARITY),
	SPACE(SerialPort.SPACE_PARITY);

	private int id;

	private Parity(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public static Parity valueOf(char c) {
		switch(c) {
			case 'N': return Parity.NONE;
			case 'E': return Parity.EVEN;
			case 'O': return Parity.ODD;
			case 'M': return Parity.MARK;
			case 'S': return Parity.SPACE;
			default: throw new IllegalArgumentException("Invalid parity setting: " + c);
		}
	}
}