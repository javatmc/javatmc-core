package org.jtmc.core.visa.exception;

import org.jtmc.core.visa.VisaException;

/**
 * UnsupportedSocketException
 */
public class UnsupportedSocketException extends VisaException {

	private static final long serialVersionUID = -7900104889511905613L;

	public UnsupportedSocketException() {
	}

	public UnsupportedSocketException(String message) {
		super(message);
	}

	public UnsupportedSocketException(Throwable cause) {
		super(cause);
	}

	public UnsupportedSocketException(String message, Throwable cause) {
		super(message, cause);
	}

}