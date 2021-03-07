package org.jtmc.core.lxi;

import java.io.IOException;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import org.jtmc.core.visa.VisaException;
import org.jtmc.core.visa.instrument.InstrumentDiscovery;

/**
 * LXIDiscovery is an interface for discovering LXI devices on the network.
 */
public interface LXIDiscovery extends InstrumentDiscovery {

  /**
   * Returns LXI devices found through the given network interface.
   * 
   * @param networkInterface Network Interface
   * @return LXI Instrument Endpoints
   * @throws IOException if there was an error during device discovery
   */
  Set<? extends LXIInstrumentEndpoint> discover(
      NetworkInterface networkInterface) throws IOException;

  /**
   * Returns LXI devices found through all of the available network interfaces.
   * 
   * @return LXI Instrument Endpoints
   * @throws VisaException if there was an error during device discovery
   */
  @Override
  default Set<LXIInstrumentEndpoint> discover() throws VisaException {
    try {
      Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
      Set<LXIInstrumentEndpoint> endpoints = new HashSet<>();
      while (interfaces.hasMoreElements()) {
        try {
          NetworkInterface intf = interfaces.nextElement();
          endpoints.addAll(discover(intf));
        } catch (IOException e) {
          //TODO: log exception
        }
      }
      return endpoints;
    } catch (SocketException e) {
      throw new VisaException(e);
    } 
  }

}
