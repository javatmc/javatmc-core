package org.jtmc.siglent.factory;

import java.io.IOException;
import org.jtmc.core.device.ISocket;
import org.jtmc.core.scpi.ISCPISocket;
import org.jtmc.core.scpi.socket.RawSCPISocket;
import org.jtmc.core.visa.VisaDeviceFactory;
import org.jtmc.core.visa.VisaException;

/**
 * SiglentDeviceFactory is used for instantiating driver objects for Siglent
 * devices.
 */
public class SiglentDeviceFactory implements VisaDeviceFactory<ISCPISocket> {

  public static final String SIGLENT_MANUFACTURER_STRING = "Siglent Technologies";

  private SiglentScopeFactory scopeFactory;

  private SiglentFunctionGeneratorFactory functionGeneratorFactory;

  public SiglentDeviceFactory() {
    this.scopeFactory = new SiglentScopeFactory();
    this.functionGeneratorFactory = new SiglentFunctionGeneratorFactory();
  }

  @Override
  public boolean supports(ISocket socket) {
    return this.supports(new RawSCPISocket(socket));
  }

  /**
   * Returns whether this socket can be used with Siglent drivers.
   * 
   * @param scpiSocket SCPI socket
   * @return {@code true} if the SCPI device is supported
   */
  public boolean supports(ISCPISocket scpiSocket) {
    try {
      return scpiSocket.getDeviceIdentifier()
              .getManufacturer()
              .equals(SIGLENT_MANUFACTURER_STRING);
    } catch (IOException e) {
      return false;
    }
  }

  @Override
  public ISCPISocket create(ISocket socket) throws VisaException {
    return this.create(new RawSCPISocket(socket));
  }

  /**
   * Creates and returns the driver for the given instrument.
   */
  public ISCPISocket create(ISCPISocket scpiSocket) throws VisaException {
    if (scopeFactory.supports(scpiSocket)) {
      return scopeFactory.create(scpiSocket);
    } else if (functionGeneratorFactory.supports(scpiSocket)) {
      return functionGeneratorFactory.create(scpiSocket);
    }
    throw new VisaException(scpiSocket + " is not supported by " + this.getClass().getSimpleName());
  }
}
