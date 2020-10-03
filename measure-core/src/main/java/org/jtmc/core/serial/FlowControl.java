package org.jtmc.core.serial;

import com.fazecast.jSerialComm.SerialPort;

public enum FlowControl {
	NONE(SerialPort.FLOW_CONTROL_DISABLED),
	RTS_CTS(SerialPort.FLOW_CONTROL_RTS_ENABLED | SerialPort.FLOW_CONTROL_CTS_ENABLED),
	XON_XOFF(SerialPort.FLOW_CONTROL_XONXOFF_IN_ENABLED | SerialPort.FLOW_CONTROL_XONXOFF_OUT_ENABLED);

	private int value;

	private FlowControl(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}