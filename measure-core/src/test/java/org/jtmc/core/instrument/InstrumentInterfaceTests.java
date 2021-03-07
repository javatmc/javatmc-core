package org.jtmc.core.instrument;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

@RunWith(BlockJUnit4ClassRunner.class)
public class InstrumentInterfaceTests {
	
	@Test
	public void testDCPowerSupply() {
		TestDCPowerSupply psu = new TestDCPowerSupply();
		assertEquals(psu.getPowerOutputs().size(), 2);

		psu.getPowerOutput(0);
		psu.getPowerOutput(1);

		psu.getPowerOutput("1");
		psu.getPowerOutput("2");
	}
}
