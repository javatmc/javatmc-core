package org.jtmc.core.lxi;

import java.net.InetAddress;
import java.util.Objects;
import org.jtmc.core.visa.DeviceIdentifier;
import org.jtmc.core.visa.instrument.InstrumentEndpoint;

/**
 * LXI Instrument Endpoints identify a host on the network and a port through
 * which the instrument is accessible.
 */
public abstract class LXIInstrumentEndpoint implements InstrumentEndpoint {
  
  private final InetAddress host;

  private final int port;

  private final DeviceIdentifier deviceIdentifier;

  /**
   * Constructs a new LXI Instrument Endpoint by specifying it's network address and port.
   * 
   * @param host Instrument's host address
   * @param port Port number
   * @param deviceIdentifier DeviceIdentifier
   */
  public LXIInstrumentEndpoint(InetAddress host, int port, DeviceIdentifier deviceIdentifier) {
    this.host = host;
    this.port = port;
    this.deviceIdentifier = deviceIdentifier;
  }

  public InetAddress getHost() {
    return host;
  }

  public int getPort() {
    return port;
  }

  /**
   * Returns the Device identifier of this instrument, 
   * if the device identifier cannot be resolved it returns 
   * DeviceIdentifier.UNKNOWN
   * 
   * @return Device identifier
   */
  public DeviceIdentifier getDeviceIdentifier() {
    return deviceIdentifier;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    LXIInstrumentEndpoint endpoint = (LXIInstrumentEndpoint) obj;
    return Objects.equals(this.host, endpoint.host) 
        && Objects.equals(this.port, endpoint.port);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.host, this.port);
  }

}
