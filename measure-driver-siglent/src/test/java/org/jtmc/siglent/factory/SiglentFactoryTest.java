package org.jtmc.siglent.factory;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.jtmc.core.scpi.mock.TestSCPISocket;
import org.jtmc.core.visa.DeviceIdentifier;
import org.jtmc.core.visa.VisaException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

@RunWith(BlockJUnit4ClassRunner.class)
public class SiglentFactoryTest {

	private SiglentDeviceFactory factory = new SiglentDeviceFactory();

	@Test
	public void testFactorySupportsDevice() {
		TestSCPISocket scpiSocket = new TestSCPISocket(
				DeviceIdentifier.from(
					SiglentDeviceFactory.SIGLENT_MANUFACTURER_STRING,
					"ABC123",
					"ABC123",
					"1.0.0"));

		assertTrue(factory.supports(scpiSocket));
	}

	@Test
	public void testFactoryUnsupportedDevice() {
		TestSCPISocket scpiSocket = new TestSCPISocket(
				DeviceIdentifier.from(
					"Other Manufacturer", 
					"ABC123",
					"ABC123",
					"1.0.0"));

		assertFalse(factory.supports(scpiSocket));
	}

	@Test(expected = VisaException.class)
	public void testFactoryCreateUnsupportedDevice() throws VisaException {
		TestSCPISocket scpiSocket = new TestSCPISocket(
				DeviceIdentifier.from(
					SiglentDeviceFactory.SIGLENT_MANUFACTURER_STRING,
					"ABC123",
					"ABC123",
					"1.0.0"));
	
		factory.create(scpiSocket);
	}

	@Test(expected = VisaException.class)
	public void testFactoryCreateSupportedDevice() throws VisaException {
		TestSCPISocket scpiSocket = new TestSCPISocket(
				DeviceIdentifier.from(
					SiglentDeviceFactory.SIGLENT_MANUFACTURER_STRING,
					"SDS1104XE",
					"ABC123",
					"1.0.0"));
	
		factory.create(scpiSocket);
	}
}