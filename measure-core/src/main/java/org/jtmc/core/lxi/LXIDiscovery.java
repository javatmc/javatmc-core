package org.jtmc.core.lxi;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import org.jtmc.core.visa.DeviceIdentifier;
import org.jtmc.core.visa.VisaException;
import org.jtmc.core.visa.instrument.InstrumentDiscovery;
import org.jtmc.core.visa.instrument.InstrumentEndpoint;

/**
 * LXIDiscovery is the interface for discovering LXI devices on the network
 */
public interface LXIDiscovery extends InstrumentDiscovery {

	/**
	 * Returns LXI devices found through the given network interface
	 * 
	 * @param networkInterface Network Interface
	 * @return LXI Instrument Endpoints
	 * @throws IOException if there was an error during device discovery
	 */
	Set<? extends LXIInstrumentEndpoint> discover(NetworkInterface networkInterface) throws IOException;

	/**
	 * Returns LXI devices found through all of the available network interfaces
	 * @return LXI Instrument Endpoints
	 * @throws VisaException if there was an error during device discovery
	 */
	@Override
	default Set<LXIInstrumentEndpoint> discover() throws VisaException {
		try {
			Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
			Set<LXIInstrumentEndpoint> endpoints = new HashSet<>();
			while(interfaces.hasMoreElements()) {
				try {
					NetworkInterface intf = interfaces.nextElement();
					endpoints.addAll(
						discover(intf));
				} catch (IOException e) {
					//TODO: log exception
				}
			}
			return endpoints;
		} catch(SocketException e) {
			throw new VisaException(e);
		} 
	}

	/**
	 * LXI Instrument Endpoints identify a host on the network and a port through
	 * which the instrument is accessible
	 */
	public abstract static class LXIInstrumentEndpoint implements InstrumentEndpoint {
		
		private final InetAddress host;

		private final int port;

		private final DeviceIdentifier deviceIdentifier;

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
		 * Returns the Device identifier of this instrument, when the device identifier cannot be resolved
		 * it will return DeviceIdentifier.UNKNOWN
		 * 
		 * @return Device identifier
		 */
		public DeviceIdentifier getDeviceIdentifier() {
			return deviceIdentifier;
		}

		@Override
		public boolean equals(Object obj) {
			if(this == obj) {
				return true;
			}
			if(obj == null) {
				return false;
			}
			LXIInstrumentEndpoint endpoint = (LXIInstrumentEndpoint) obj;
			return Objects.equals(this.host, endpoint.host) && Objects.equals(this.port, endpoint.port);
		}

		@Override
		public int hashCode() {
			return Objects.hash(this.host, this.port);
		}

	}
}