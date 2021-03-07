package org.jtmc.core.visa.exception;

/**
 * InstrumentException is thrown when there's an error in controlling the instrument.
 */
public class InstrumentException extends Exception {

  private static final long serialVersionUID = 3954823646769129524L;

  public InstrumentException() {
  }

  public InstrumentException(String message) {
    super(message);
  }

  public InstrumentException(Throwable cause) {
    super(cause);
  }

  public InstrumentException(String message, Throwable cause) {
    super(message, cause);
  }

}
