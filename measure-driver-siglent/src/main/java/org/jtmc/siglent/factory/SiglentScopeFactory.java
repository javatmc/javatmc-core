package org.jtmc.siglent.factory;

import java.io.IOException;
import org.jtmc.core.scpi.ISCPISocket;
import org.jtmc.core.visa.DeviceIdentifier;
import org.jtmc.core.visa.VisaException;
import org.jtmc.siglent.driver.SDSE11ADriver;
import org.jtmc.siglent.driver.SDSLegacyDriver;
import org.jtmc.siglent.info.SiglentOscilloscopeModel;

/**
 * SiglentScopeFactory is used to instantiate drivers for Siglent Oscilloscopes.
 */
public class SiglentScopeFactory {

  /**
   * Returns whether this factory is able to initialize a driver for the socket.
   * 
   * @param scpiSocket SCPI Socket
   * @return {@code true} if this factory can create a driver
   */
  public boolean supports(ISCPISocket scpiSocket) {
    try {
      SiglentOscilloscopeModel.create(scpiSocket.getDeviceIdentifier().getModel());
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
      SiglentOscilloscopeModel scopeInfo = SiglentOscilloscopeModel.create(model);
      //TODO: verify IDN response of the SDS2000X Plus series, the postfix may be P or Plus
      if (scopeInfo.getSeries() == 5
          || scopeInfo.getSeries() == 6
          || (scopeInfo.getSeries() == 2 && scopeInfo.getPostfix().equals("X+"))) {
        
        return new SDSE11ADriver(scpiSocket, deviceIdentifier, scopeInfo);
      }
      return new SDSLegacyDriver(scpiSocket, deviceIdentifier, scopeInfo);
    } catch (IOException e) {
      throw new VisaException(e);
    }
  }
}
