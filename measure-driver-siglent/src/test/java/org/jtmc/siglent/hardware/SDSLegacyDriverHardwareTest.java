package org.jtmc.siglent.hardware;

import java.io.IOException;
import java.net.InetAddress;

import org.jtmc.core.instrument.Oscilloscope;
import org.jtmc.core.lxi.vxi11.VXI11Socket;
import org.jtmc.core.scpi.socket.RawSCPISocket;
import org.jtmc.core.util.Units;
import org.jtmc.core.visa.exception.InstrumentException;
import org.jtmc.siglent.driver.SDSLegacyDriver;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

@RunWith(BlockJUnit4ClassRunner.class)
public class SDSLegacyDriverHardwareTest {

	private final static String IP = "192.168.2.2";

	@Test
	public void testScopeDriver() throws IOException, InstrumentException {
		
		try(VXI11Socket socket = new VXI11Socket(InetAddress.getByName(IP))) {
			Oscilloscope scope = new SDSLegacyDriver(new RawSCPISocket(socket));

			scope.acquisition().setTimespan(Units.milli(14));
			scope.acquisition().setTimeOffset(0);
			scope.getAnalogInput(2).setEnabled(true);
			scope.getAnalogInput(2).setProbeAttenuation(10);
			scope.getAnalogInput(2).setRange(3.1);
			scope.getAnalogInput(2).setOffset(-1.5);
		}
	}
}