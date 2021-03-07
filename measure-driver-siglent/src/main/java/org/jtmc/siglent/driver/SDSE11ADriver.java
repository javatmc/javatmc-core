package org.jtmc.siglent.driver;

import org.jtmc.core.scpi.ISCPISocket;
import org.jtmc.core.scpi.SCPISocketAdapter;
import org.jtmc.core.visa.DeviceIdentifier;
import org.jtmc.siglent.info.SiglentOscilloscopeModel;

/**
 * Siglent Oscilloscope driver for the newer scopes using a standard SCPI driver.
 */
public class SDSE11ADriver extends SCPISocketAdapter {

  public SDSE11ADriver(
        ISCPISocket adapter,
        DeviceIdentifier deviceIdentifier,
        SiglentOscilloscopeModel scopeInfo) {
    super(adapter, deviceIdentifier);
    //TODO: implement
  }

}
