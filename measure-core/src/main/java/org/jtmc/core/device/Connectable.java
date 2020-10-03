package org.jtmc.core.device;

/**
 * Connectable is any device that has a binary state of connection, either connected or disconnected
 */
public interface Connectable extends AutoCloseable {

	/**
	 * Returns whether or not the device is connected
	 * 
	 * @return {@code true} if the device is connected
	 */
	public boolean isConnected();

	/**
	 * Disconnects the device
	 * 
	 * <p>This operation should be idempotent, meaning that calling it 
	 * multiple times shouldn't affect the state of the connection beyond the first call</b>
	 */
	@Override
	public void close();
}