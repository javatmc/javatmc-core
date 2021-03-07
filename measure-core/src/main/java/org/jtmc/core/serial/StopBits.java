package org.jtmc.core.serial;

import com.fazecast.jSerialComm.SerialPort;

/**
 * StopBits represents the spacing after each UART byte.
 */
public enum StopBits {
  ONE(SerialPort.ONE_STOP_BIT, 1.0f), 
  ONE_AND_HALF(SerialPort.ONE_POINT_FIVE_STOP_BITS, 1.5f),
  TWO(SerialPort.TWO_STOP_BITS, 2.0f);

  private int id;

  private float bitcount;

  private StopBits(int id, float bitcount) {
    this.id = id;
    this.bitcount = bitcount;
  }

  public int getId() {
    return id;
  }

  public float getBitCount() {
    return bitcount;
  }

  /**
   * Returns the stopbits for the given bitcount.
   * 
   * @param bitcount Bitcount (floating)
   * @return Stopbits
   */
  public static StopBits from(float bitcount) {
    if (bitcount == 1.0f) {
      return StopBits.ONE;
    } else if (bitcount == 1.5f) {
      return StopBits.ONE_AND_HALF;
    } else if (bitcount == 2.0f) {
      return StopBits.TWO;
    }
    throw new IllegalArgumentException("Invalid stopbit value: " + bitcount);
  }

  /**
   * Returns the stopbits for the given bitcount.
   * 
   * @param bitcount Bitcount (string), one of '1', '1.5', '2'
   * @return Stopbits
   */
  public static StopBits from(String bitcount) {
    switch (bitcount) {
      case "1": return StopBits.ONE;
      case "1.5": return StopBits.ONE_AND_HALF;
      case "2": return StopBits.TWO;
      default:
        throw new IllegalArgumentException("Invalid stopbit value: " + bitcount);
    }
  }
}
