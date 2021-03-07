package org.jtmc.core.visa;

/**
 * VisaException is thrown when there's an error connecting to a device
 * using the VISA Resource Manager.
 */
public class VisaException extends Exception {

  private static final long serialVersionUID = 4917695447853517964L;

  public VisaException() {
  }

  public VisaException(String message) {
    super(message);
  }

  public VisaException(Throwable cause) {
    super(cause);
  }

  public VisaException(String message, Throwable cause) {
    super(message, cause);
  }

}
