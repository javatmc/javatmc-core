package org.jtmc.core.visa;

/**
 * VisaException
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

	public VisaException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}