package org.jtmc.core.lxi.vxi11;

import java.io.IOException;
import java.net.InetAddress;
import org.jtmc.core.lxi.LXIInstrumentEndpoint;
import org.jtmc.core.visa.DeviceIdentifier;

/**
 * VXI11InstrumentEndpoint is used to instantiate VXI-11 connections
 * to instruments.
 */
public class VXI11InstrumentEndpoint extends LXIInstrumentEndpoint {

  public VXI11InstrumentEndpoint(
      InetAddress host, 
      int port, 
      DeviceIdentifier deviceIdentifier) {
    super(host, port, deviceIdentifier);
  }

  @Override
  public VXI11Socket connect() throws IOException {
    return new VXI11Socket(this.getHost(), this.getPort());
  }

}
