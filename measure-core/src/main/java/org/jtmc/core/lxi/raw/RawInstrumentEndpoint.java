package org.jtmc.core.lxi.raw;

import java.io.IOException;
import java.net.InetAddress;

import org.jtmc.core.lxi.LXIDiscovery.LXIInstrumentEndpoint;
import org.jtmc.core.visa.DeviceIdentifier;

public class RawInstrumentEndpoint extends LXIInstrumentEndpoint {

	public RawInstrumentEndpoint(InetAddress host, int port, DeviceIdentifier deviceIdentifier) {
		super(host, port, deviceIdentifier);
	}

	@Override
	public RawSocket connect() throws IOException {
		return new RawSocket(this.getHost(), this.getPort(), 0);
	}
	
}
