package org.jtmc.core.scpi;

import java.io.IOException;
import java.net.SocketTimeoutException;

import org.jtmc.core.visa.DeviceIdentifier;

/**
 * ISCPISocket is the standard interface for sending SCPI commands and receiving responses
 */
public interface ISCPISocket {

	public final static long DEFAULT_TIMEOUT = 1000;

	/**
	 * Sends SCPI commands to the device, how they're being sent is up to the socket implementation
	 * <p>
	 * The operation should be atomic, so either all commands should succeed or the device's state shall not change
	 * 
	 * @param commands SCPI commands to send
	 * @throws IOException if 
	 */
	public void send(SCPICommand... commands) throws IOException;

	/**
	 * Receives a SCPI response from the device
	 * <p>
	 * The operation has to return {@code Optional.empty()} if the method timed out with no response
	 * 
	 * @param timeout Time to wait for a response, if 0 then wait indefinitely
	 * @return Device response as string
	 * @throws IOException If the 
	 */
	public String receive(long timeout) throws SocketTimeoutException, IOException;

	/**
	 * Receives part of the response from the device, up until the given character count has been reached
	 * @param count
	 * @param timeout
	 * @return
	 * @throws IOException
	 */
	String receive(int count, long timeout) throws SocketTimeoutException, IOException;

	/**
	 * Sends a SCPI command to the device and then waits for a response
	 * @param command
	 * @param timeout
	 * @return
	 * @throws IOException
	 */
	default String query(SCPICommand command, long timeout) throws SocketTimeoutException, IOException {
		this.send(command);
		return this.receive(timeout);
	}

	/**
	 * Returns the device identifier of the SCPI device this socket is connected to by sending
	 * an {@code *IDN?} query to the device
	 * 
	 * @return Device Identifier
	 * @throws SocketTimeoutException if the device didn't respond within the default timeout
	 * @throws IOException if there was an error communicating with the device
	 */
	default DeviceIdentifier getDeviceIdentifier() throws SocketTimeoutException, IOException {
		try {
			return DeviceIdentifier.from(this.query(SCPI.idnQuery, DEFAULT_TIMEOUT));
		} catch(IllegalArgumentException e) {
			throw new IOException("Cannot resolve device identifier", e);
		}
	}

	/**
	 * Resets the device by sending the standard SCPI reset command
	 * 
	 * @see SCPI.resetDevice
	 * @throws IOException
	 */
	default void reset() throws IOException {
		this.send(SCPI.resetDevice);
	}

	default void clearStatus() throws IOException {
		this.send(SCPI.clearStatus);
	}

	default void waitForOperation(long timeout) throws IOException {
		this.send(SCPI.opcQuery);
		this.receive(timeout);
	}
}