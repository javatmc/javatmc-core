package org.jtmc.core.scpi.mock;

import java.io.IOException;

import org.jtmc.core.scpi.SCPI;
import org.jtmc.core.scpi.SCPICommand;
import org.jtmc.core.visa.DeviceIdentifier;
import org.jtmc.core.visa.mock.MockSocket;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

/**
 * MockSCPISocketTest
 */
@RunWith(BlockJUnit4ClassRunner.class)
public class MockSCPISocketTest {

	@Test
	public void testIdn() throws IOException {
		TestDevice device = new TestDevice();
		String response = device.query(SCPI.idnQuery, 1000);
		DeviceIdentifier id = DeviceIdentifier.from(response);

		Assert.assertEquals("Test", id.getManufacturer());
		Assert.assertEquals("DEV1", id.getModel());
		Assert.assertEquals("ABC123", id.getSerialNumber());
		Assert.assertEquals("1.0.0", id.getFirmwareVersion());
	}

	@Test
	public void testBasicCommand() throws IOException {
		TestDevice device = new TestDevice();
		SCPICommand response = new SCPICommand(device.query(SCPICommand.builder().query("BASIC").build(), 1000));

		Assert.assertEquals(true, response.hasParameter("PARAM"));
		Assert.assertEquals("VALUE", response.getParameter("PARAM").get());
	}

	@Test
	public void testBasicParameter() throws IOException {
		TestDevice device = new TestDevice();
		SCPICommand response = new SCPICommand(device.query(SCPICommand.builder().query("BASIC2").with("PARAM", "INPUT").build(), 1000));

		Assert.assertEquals(true, response.hasParameter("PARAM"));
		Assert.assertEquals("INPUT", response.getParameter("PARAM").get());
	}

	/*
	@Test
	public void testCommandPathInt() throws IOException {
		TestDevice device = new TestDevice();
		device.connect();
		SCPICommand response = new SCPICommand(device.query(SCPICommand.builder().command("C1:CMD").build(), 1000).get());

		Assert.assertEquals(true, response.hasParameter("PARAM"));
		Assert.assertEquals("VALUE", response.getParameter("PARAM").get());

		device.close();
	}
	*/
	
	public static class TestDevice extends MockSCPISocket {

		public TestDevice() {
			super(new MockSocket("TestSocket"), DeviceIdentifier.from("Test", "DEV1", "ABC123", "1.0.0"));
		}

		@OnCommand("BASIC\\?")
		public SCPICommand basic() {
			return SCPICommand.builder().command("BASIC").with("PARAM", "VALUE").build();
		}

		@OnCommand("BASIC2\\?")
		public SCPICommand basic(SCPICommand in) {
			return SCPICommand.builder().command("BASIC2").with("PARAM", in.getParameter("PARAM").get()).build();
		}

		@OnCommand("C(?<id>[0-9]{1}):CMD")
		public SCPICommand channelCommand(int id) {
			return SCPICommand.builder().command("C" + id + ":CMD").with("PARAM", "VALUE").build();
		}

		@Override
		public void onReset() {

		}

		@Override
		public void onNotMapped(SCPICommand command) {
			throw new IllegalArgumentException();
		}

	}
	
}