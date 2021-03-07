package org.jtmc.core.visa.factory;

import java.io.IOException;
import org.jtmc.core.device.ISocket;
import org.jtmc.core.visa.exception.UnsupportedSocketException;

/**
 * SocketFactory is a factory capable of creating Sockets using a VISA resource string
 * 
 * <p>The factory may have internal default configurations, such as baud rates
 * for serial implementations. It's recommended to have a no-args constructor
 * with these defaults, setters for certain parameters and an all-args constructor.
 */
public interface ISocketFactory {

  /**
   * Returns whether this factory is capable of creating a socket given the resource string.
   * 
   * @param resourceString VISA Resource string
   * @return {@code true} if the factory can create a socket from the resource string 
   */
  boolean supports(String resourceString);

  /**
   * Creates a socket using the given resource string.
   * 
   * @param resourceString VISA Resource string
   * @return Socket
   * @throws IOException if the creation failed because of a communication error
   * @throws UnsupportedSocketException if the factory is unable to create the given socket
   */
  ISocket create(String resourceString) throws IOException, UnsupportedSocketException;
}
