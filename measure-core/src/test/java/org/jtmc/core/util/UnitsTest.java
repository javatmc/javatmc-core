package org.jtmc.core.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

@RunWith(BlockJUnit4ClassRunner.class)
public class UnitsTest {

	@Test
	public void testStringConversion() {
		assertEquals("10ns", Units.auto(Units.nano(10), 0, "s"));
		assertEquals("10us", Units.auto(Units.micro(10), 0, "s"));
		assertEquals("10ms", Units.auto(Units.milli(10), 0, "s"));
		assertEquals("10s", Units.auto(10, 0, "s"));

		assertEquals("-10ns", Units.auto(Units.nano(-10), 0, "s"));
		assertEquals("-10us", Units.auto(Units.micro(-10), 0, "s"));
		assertEquals("-10ms", Units.auto(Units.milli(-10), 0, "s"));
		assertEquals("-10s", Units.auto(-10, 0, "s"));
	}
}