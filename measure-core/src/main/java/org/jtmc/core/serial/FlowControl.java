package org.jtmc.core.serial;

import static com.fazecast.jSerialComm.SerialPort.FLOW_CONTROL_CTS_ENABLED;
import static com.fazecast.jSerialComm.SerialPort.FLOW_CONTROL_DISABLED;
import static com.fazecast.jSerialComm.SerialPort.FLOW_CONTROL_RTS_ENABLED;
import static com.fazecast.jSerialComm.SerialPort.FLOW_CONTROL_XONXOFF_IN_ENABLED;
import static com.fazecast.jSerialComm.SerialPort.FLOW_CONTROL_XONXOFF_OUT_ENABLED;

/**
 * FlowControl is used to communicate which party can transmit at the given
 * time in a shared serial bus.
 */
public enum FlowControl {

  /**
   * Flow control is disabled.
   */
  NONE(FLOW_CONTROL_DISABLED),

  /**
   * Hardware flow control is enabled.
   */
  RTS_CTS(FLOW_CONTROL_RTS_ENABLED | FLOW_CONTROL_CTS_ENABLED),

  /**
   * Software flow control is enabled.
   */
  XON_XOFF(FLOW_CONTROL_XONXOFF_IN_ENABLED | FLOW_CONTROL_XONXOFF_OUT_ENABLED);

  private int value;

  private FlowControl(int value) {
    this.value = value;
  }

  public int getValue() {
    return value;
  }

}
