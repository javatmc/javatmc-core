package org.jtmc.core.visa.exception;

/**
 * UnsupportedDeviceException is thrown when the device factory is unable to
 * find a suitable driver for the given socket.
 */
public class UnsupportedDeviceException  extends Exception {

  private static final long serialVersionUID = -7900104889511905613L;

  public UnsupportedDeviceException() {
  }

  public UnsupportedDeviceException(String message) {
    super(message);
  }

  public UnsupportedDeviceException(Throwable cause) {
    super(cause);
  }

  public UnsupportedDeviceException(String message, Throwable cause) {
    super(message, cause);
  }

}
