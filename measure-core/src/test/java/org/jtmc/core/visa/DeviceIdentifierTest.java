package org.jtmc.core.visa;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

/**
 * LXIDeviceInfoTest
 */
@RunWith(BlockJUnit4ClassRunner.class)
public class DeviceIdentifierTest {

	@Test
	public void testFromWithSplit() {
		DeviceIdentifier info = DeviceIdentifier.from("Siglent Technologies,SDG1032X,SDG1XCAD2RABC123,1.01.01.33R1");
		assertEquals("Siglent Technologies", info.getManufacturer());
		assertEquals("SDG1032X", info.getModel());
		assertEquals("SDG1XCAD2RABC123", info.getSerialNumber());
		assertEquals("1.01.01.33R1", info.getFirmwareVersion());
	}

	@Test
	public void testFromWithoutSplit() {
		DeviceIdentifier info = DeviceIdentifier.from("Siglent Technologies","SDG1032X","SDG1XCAD2RABC123","1.01.01.33R1");
		assertEquals("Siglent Technologies", info.getManufacturer());
		assertEquals("SDG1032X", info.getModel());
		assertEquals("SDG1XCAD2RABC123", info.getSerialNumber());
		assertEquals("1.01.01.33R1", info.getFirmwareVersion());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInvalidInfo() {
		DeviceIdentifier.from("Siglent Technologies,SDG1032X,SDG1XCAD2RABC123");
	}
	
}