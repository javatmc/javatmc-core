package org.jtmc.core.lxi.mdns;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.jmdns.JmDNS;
import javax.jmdns.ServiceEvent;
import javax.jmdns.ServiceListener;
import org.jtmc.core.lxi.LXIDiscovery;
import org.jtmc.core.lxi.LXIInstrumentEndpoint;
import org.jtmc.core.lxi.raw.RawInstrumentEndpoint;
import org.jtmc.core.lxi.vxi11.VXI11InstrumentEndpoint;
import org.jtmc.core.visa.DeviceIdentifier;

/**
 * MDNSDiscovery implements LXI instrument discovery using the mDNS protocol.
 */
public class MDNSDiscovery implements LXIDiscovery {

  private int timeout = 1000;

  private List<MDNSServiceType> serviceTypes;

  public MDNSDiscovery() {
    this(MDNSServiceType.LXI);
  }

  public MDNSDiscovery(MDNSServiceType... types) {
    this(Arrays.asList(types));
  }

  public MDNSDiscovery(List<MDNSServiceType> types) {
    this.serviceTypes = new ArrayList<>(types);
  }

  @Override
  public Set<LXIInstrumentEndpoint> discover(NetworkInterface networkInterface) throws IOException {
    try (JmDNS jmdns = JmDNS.create(networkInterface.getInterfaceAddresses().get(0).getAddress())) {
      Set<LXIInstrumentEndpoint> devices = new HashSet<>();
      ServiceListener callback = new ServiceListener() {
      
        @Override
        public void serviceResolved(ServiceEvent event) {
          LXIInstrumentEndpoint endpoint = convertToEndpoint(event);
          if (endpoint != null) {
            devices.add(endpoint);
          }
        }
      
        @Override
        public void serviceRemoved(ServiceEvent event) {
          
        }
      
        @Override
        public void serviceAdded(ServiceEvent event) {

        }
      };
      serviceTypes.forEach(type -> jmdns.addServiceListener(type.fqdn(), callback));
      
      Thread.sleep(timeout);

      return devices;
    } catch (InterruptedException e) {
      throw new IOException("MDNS discovery was interrupted.");
    }
  }

  /**
   * Converts an mDNS service discovery event into a connectable
   * LXI Instrument endpoint.
   * 
   * @param event mDNS discovery event
   * @return LXI Instrument endpoint
   */
  private static LXIInstrumentEndpoint convertToEndpoint(ServiceEvent event) {
    InetAddress host = event.getInfo().getInetAddresses()[0];
    int port = event.getInfo().getPort();
    DeviceIdentifier deviceIdentifier = MDNS.getDeviceIdentifier(event.getInfo());

    if (event.getType().equals(MDNSServiceType.VXI11.fqdn())) {
      return new VXI11InstrumentEndpoint(host, port, deviceIdentifier);
    } else if (event.getType().equals(MDNSServiceType.SCPI_RAW.fqdn())) {
      return new RawInstrumentEndpoint(host, port, deviceIdentifier);
    }
    return null;
  }

}
