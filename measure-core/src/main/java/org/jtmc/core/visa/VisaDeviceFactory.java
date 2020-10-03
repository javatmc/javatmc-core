package org.jtmc.core.visa;

import org.jtmc.core.device.ISocket;
import org.jtmc.core.visa.exception.UnsupportedSocketException;

/**
 * VisaDeviceFactory
 */
public interface VisaDeviceFactory<T> {

	/**
	 * Indicates that this factory is capable of wrapping the given Socket in a driver
	 * @param socket Socket
	 * @return {@code true} when the factory may be able to create some driver using the given Socket
	 */
	boolean supports(ISocket socket);

	/**
	 * Creates and returns a driver object that wraps the given
	 * 
	 * <p>Implementations can assume that create is only called after verifying the return
	 * value of support
	 * 
	 * @param socket Socket
	 * @return Driver
	 * @throws UnsupportedSocketException if attempting to create the driver failed because
	 * 			the socket turned out to be unsuitable
	 * @throws VisaException if there was an error creating the driver
	 */
	T create(ISocket socket) throws UnsupportedSocketException, VisaException;
}