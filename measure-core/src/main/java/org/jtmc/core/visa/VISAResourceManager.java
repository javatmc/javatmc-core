package org.jtmc.core.visa;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import org.jtmc.core.device.ISocket;
import org.jtmc.core.visa.exception.UnsupportedSocketException;
import org.jtmc.core.visa.factory.ISocketFactory;

/**
 * VISAResourceManager can be used to connect instruments.
 */
public class VISAResourceManager implements AutoCloseable {

  private ISocketFactory socketFactory;

  private List<VisaDeviceFactory<?>> factories;

  private transient List<ISocket> sockets;

  /**
   * Creates a new VISAResourceManager with the given socket factory and device factories.
   * @param socketFactory Factory for creating sockets
   * @param factories Device factories
   */
  public VISAResourceManager(
        final ISocketFactory socketFactory,
        List<VisaDeviceFactory<?>> factories) {
    this.socketFactory = socketFactory;
    this.factories = new LinkedList<>(factories);
    this.sockets = new LinkedList<>();
  }

  public ISocket connect(String resourceString) throws IOException, VisaException {
    return socketFactory.create(resourceString);
  }

  /**
   * Connects a new instrument using the given resource string and casting it to the
   * required type.
   * 
   * @param <T> Preferred return type
   * @param resourceString VISA Resource string
   * @param klass Class of the return type
   * @return Device driver
   * @throws IOException if there was an error connecting to the device
   * @throws VisaException if there was an error creating a driver for the device
   */
  public <T> T connect(String resourceString, Class<T> klass) throws IOException, VisaException {
    ISocket socket = this.connect(resourceString);

    ArrayList<Object> candidates = new ArrayList<>(factories.size());
    candidates.add(socket);
    
    for (VisaDeviceFactory<?> factory : factories) {
      try {
        if (factory.supports(socket)) {
          Object dev = factory.create(socket);
          candidates.add(dev);
        }
      } catch (UnsupportedSocketException e) {
        e.printStackTrace();
      }
    }
    
    try {
      return findCandidate(candidates, klass);
    } catch (NoSuchElementException e) {
      throw new VisaException("No candidate found for type " + klass);
    }
  }

  private static <T> T findCandidate(Collection<Object> candidates, Class<T> klass)
        throws NoSuchElementException {
    for (Object obj : candidates) {
      if (klass.isAssignableFrom(obj.getClass())) {
        //TODO: handle ambiguity, prefer equal type
        return klass.cast(obj);
      }
    }
    throw new NoSuchElementException();
  }    

  @Override
  public void close() {
    this.sockets.forEach(ISocket::close);
  }

}
