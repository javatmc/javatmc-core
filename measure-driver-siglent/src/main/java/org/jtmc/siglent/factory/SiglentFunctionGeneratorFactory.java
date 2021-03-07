package org.jtmc.siglent.factory;

import java.io.IOException;
import org.jtmc.core.scpi.ISCPISocket;
import org.jtmc.core.visa.DeviceIdentifier;
import org.jtmc.core.visa.VisaException;
import org.jtmc.siglent.driver.SDGDriver;
import org.jtmc.siglent.info.SiglentFunctionGeneratorModel;

/**
 * SiglentFunctionGeneratorFactory is used to instantiate drivers for 
 * Siglent Function generators.
 */
public class SiglentFunctionGeneratorFactory {

  /**
   * Returns whether this factory is able to initialize a driver for the socket.
   * 
   * @param scpiSocket SCPI Socket
   * @return {@code true} if this factory can create a driver
   */
  public boolean supports(ISCPISocket scpiSocket) {
    try {
      SiglentFunctionGeneratorModel.create(scpiSocket.getDeviceIdentifier().getModel());
      return true;
    } catch (IllegalArgumentException | IOException e) {
      return false;
    }
  }

  /**
   * Creates a driver object for the given SCPI socket.
   * 
   * @param scpiSocket SCPI Socket
   * @return SCPI Socket
   * @throws VisaException if there was an error creating the driver
   */
  public ISCPISocket create(ISCPISocket scpiSocket) throws VisaException {
    try {
      DeviceIdentifier deviceIdentifier = scpiSocket.getDeviceIdentifier();
      String model = deviceIdentifier.getModel();
      SiglentFunctionGeneratorModel fgenModel = SiglentFunctionGeneratorModel.create(model);

      return new SDGDriver(scpiSocket, deviceIdentifier, fgenModel);
    } catch (IOException e) {
      throw new VisaException(e);
    }
  }

}
