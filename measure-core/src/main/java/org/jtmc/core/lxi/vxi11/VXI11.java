package org.jtmc.core.lxi.vxi11;

import java.lang.reflect.Field;

/**
 * VXI-11 contains constants specified in the VXI-11 standard.
 * 
 * <p>List of contents:
 * <ul>
 * <li>
 * Program identifiers
 * <li>
 * Procedure numbers
 * <li>
 * Error codes
 * </ul>
 */
public class VXI11 {

  /**
   * Device core functionality.
   */
  public static class DeviceCore {
    public static final int PROGRAM = 0x0607AF;
    public static final int VERSION = 1;

    public static final int CREATE_LINK = 10;
    public static final int DEVICE_WRITE = 11;
    public static final int DEVICE_READ = 12;
    public static final int DEVICE_READSTB = 13;
    public static final int DEVICE_TRIGGER = 14;
    public static final int DEVICE_CLEAR = 15;
    public static final int DEVICE_REMOTE = 16;
    public static final int DEVICE_LOCAL = 17;
    public static final int DEVICE_LOCK = 18;
    public static final int DEVICE_UNLOCK = 19;
    public static final int DEVICE_ENABLE_SRQ = 20;
    public static final int DEVICE_DOCMD = 22;
    public static final int DESTROY_LINK = 23;
    public static final int CREATE_INTR_CHANNEL = 25;
    public static final int DESTROY_INTR_CHANNEL = 26;
  }

  /**
   * Interrupt functionality (optional).
   */
  public static class DeviceInterrupt {
    public static final int PROGRAM = 0x0607B1;
    public static final int VERSION = 1;

    public static final int DEVICE_INTR_SRQ = 30;
  }

  /**
   * Asynchronous functionality (optional).
   */
  public static class DeviceAsync {
    public static final int PROGRAM = 0x0607B0;
    public static final int VERSION = 1;

    public static final int DEVICE_ABORT = 1;
  }

  /**
   * Error codes.
   * 
   * <p>Found in VXI-11 Rev 1.0 specification Table B.2
   */
  public static class ErrorCode {
    public static final int NO_ERROR = 0;
    public static final int SYNTAX_ERROR = 2;
    public static final int DEVICE_NOT_ACCESSIBLE = 3;
    public static final int INVALID_LINK_IDENTIFIER = 4;
    public static final int PARAMETER_ERROR = 5;
    public static final int CHANNEL_NOT_ESTABLISHED = 6;
    public static final int OPERATION_NOT_SUPPORTED = 8;
    public static final int OUT_OF_RESOURCES = 9;
    public static final int DEVICE_LOCKED_BY_ANOTHER_LINK = 11;
    public static final int NO_LOCK_HELD_BY_THIS_LINK = 12;
    public static final int IO_TIMEOUT = 15;
    public static final int IO_ERROR = 17;
    public static final int INVALID_ADDRESS = 21;
    public static final int ABORT = 23;
    public static final int CHANNEL_ALREADY_ESTABLISHED = 29;

    /**
     * Returns the name of the error for the given code.
     * 
     * @param code Error code
     * @return Error name
     */
    public static String getErrorName(int code) {
      try {
        for (Field field : ErrorCode.class.getFields()) {
          if (field.getType() == int.class && field.getInt(null) == code) {
            return field.getName();
          }
        }
        return "UNKNOWN";
      } catch (IllegalAccessException e) {
        return "UNKNOWN";
      }
    }

    /**
     * Returns an error string consisting of the error name and error code.
     * 
     * @param code Error code
     * @return Error string in the format <i>'NAME (CODE)'</i>
     */
    public static String getErrorString(int code) {
      return getErrorName(code) + " (" + code + ")";
    }
  }
}
