package org.jtmc.core.lxi.mdns;

import java.io.IOException;
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
import org.jtmc.core.lxi.raw.RawInstrumentEndpoint;
import org.jtmc.core.lxi.vxi11.VXI11InstrumentEndpoint;

/**
 * MDNSDiscovery
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
		try(JmDNS jmdns = JmDNS.create(networkInterface.getInterfaceAddresses().get(0).getAddress())) {
			Set<LXIInstrumentEndpoint> devices = new HashSet<>();
			ServiceListener callback = new ServiceListener(){
			
				@Override
				public void serviceResolved(ServiceEvent event) {
					LXIInstrumentEndpoint endpoint = getEndpoint(event);
					if(endpoint != null) {
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

	private static LXIInstrumentEndpoint getEndpoint(ServiceEvent event) {
		if(event.getType().equals(MDNSServiceType.VXI11.fqdn())) {
			return new VXI11InstrumentEndpoint(event.getInfo().getInetAddresses()[0], event.getInfo().getPort(), MDNS.resolveDeviceIdentifier(event.getInfo()));
		}
		else if(event.getType().equals(MDNSServiceType.SCPI_RAW.fqdn())) {
			return new RawInstrumentEndpoint(event.getInfo().getInetAddresses()[0], event.getInfo().getPort(), MDNS.resolveDeviceIdentifier(event.getInfo()));
		}
		return null;
	}

}