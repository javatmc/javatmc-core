package org.jtmc.core.serial;

import com.fazecast.jSerialComm.SerialPort;

/**
 * Parity is used to detect errors in Serial communication.
 */
public enum Parity {

  /**
   * Parity is not used.
   */
  NONE(SerialPort.NO_PARITY),

  /**
   * The parity bit is 1 in case of odd number of 1's in the data.
   */
  EVEN(SerialPort.EVEN_PARITY),

  /**
   * The parity bit is 1 in case of even number of 1's in the data.
   */
  ODD(SerialPort.ODD_PARITY),

  /**
   * The parity bit is always 1.
   */
  MARK(SerialPort.MARK_PARITY),

  /**
   * The parity bit is always 0.
   */
  SPACE(SerialPort.SPACE_PARITY);

  private int id;

  private Parity(int id) {
    this.id = id;
  }

  public int getId() {
    return id;
  }

  /**
   * Converts a serial port's parity configuration letter into
   * the actual parity configuration.
   * 
   * @param c Parity configuration letter (one of N,E,O,M,S)
   * @return Serial parity configuration
   */
  public static Parity valueOf(char c) {
    switch (c) {
      case 'N': return Parity.NONE;
      case 'E': return Parity.EVEN;
      case 'O': return Parity.ODD;
      case 'M': return Parity.MARK;
      case 'S': return Parity.SPACE;
      default: throw new IllegalArgumentException("Invalid parity letter: " + c);
    }
  }

}
