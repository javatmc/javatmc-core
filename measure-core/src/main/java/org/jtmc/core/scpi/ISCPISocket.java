package org.jtmc.core.scpi;

import java.io.IOException;
import java.net.SocketTimeoutException;
import org.jtmc.core.visa.DeviceIdentifier;

/**
 * ISCPISocket is the standard interface for sending SCPI commands 
 * and receiving responses.
 */
public interface ISCPISocket {

  public static final long DEFAULT_TIMEOUT = 1000;

  /**
   * Sends SCPI commands to the device, how they're being sent is up to 
   * the socket implementation.
   * 
   * <p>The operation should be atomic, so either all commands should succeed 
   * or the device's state shall not change
   * 
   * @param commands SCPI commands to send
   * @throws IOException if there was an error communication with the device
   */
  public void send(SCPICommand... commands) throws IOException;

  /**
   * Receives a SCPI response from the device.
   * 
   * <p>The operation has to return {@code Optional.empty()} if the method 
   * timed out with no response.
   * 
   * @param timeout Time to wait for a response, if 0 then wait indefinitely
   * @return Device response as string
   * @throws IOException if there was an error communication with the device
   */
  public String receive(long timeout) 
        throws SocketTimeoutException, IOException;

  /**
   * Receives part of the response from the device, up until the 
   * given character count has been reached.
   * 
   * @param count Number of characters to receive
   * @param timeout Maximum time to wait for a response
   * @return Device response as string
   * @throws IOException if there was an error communication with the device
   */
  String receive(int count, long timeout) 
        throws SocketTimeoutException, IOException;

  /**
   * Sends a SCPI command to the device and then waits for a response.
   * 
   * @param command SCPI command to send
   * @param timeout Maximum time to wait for a response
   * @return Response to the SCPI command
   * @throws IOException if there was an error communication with the device
   */
  default String query(SCPICommand command, long timeout) 
      throws SocketTimeoutException, IOException {
    this.send(command);
    return this.receive(timeout);
  }

  /**
   * Returns the device identifier of the SCPI device this socket is connected 
   * to by sending an {@code *IDN?} query to the device.
   * 
   * @return Device Identifier
   * @throws SocketTimeoutException if the device didn't respond within 
   *                                the default timeout
   * @throws IOException if there was an error communicating with the device
   */
  default DeviceIdentifier getDeviceIdentifier() 
      throws SocketTimeoutException, IOException {
    try {
      return DeviceIdentifier.from(this.query(SCPI.idnQuery, DEFAULT_TIMEOUT));
    } catch (IllegalArgumentException e) {
      throw new IOException("Cannot resolve device identifier", e);
    }
  }

  /**
   * Resets the device by sending the standard SCPI reset command.
   * 
   * @see SCPI.resetDevice
   * @throws IOException if there was an error communication with the device
   */
  default void reset() throws IOException {
    this.send(SCPI.resetDevice);
  }

  /**
   * Clears the device status by sending the standard SCPI status clear command.
   * 
   * @throws IOException if there was an error communication with the device
   */
  default void clearStatus() throws IOException {
    this.send(SCPI.clearStatus);
  }

  /**
   * Sends an operation complete query to the device then waits for a response.
   * 
   * @param timeout Maximum time to wait for the operation to complete
   * @throws IOException if there was an error communication with the device
   */
  default void waitForOperation(long timeout) throws IOException {
    this.send(SCPI.opcQuery);
    this.receive(timeout);
  }

}
