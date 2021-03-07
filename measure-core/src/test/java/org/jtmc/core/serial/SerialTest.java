package org.jtmc.core.serial;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

@RunWith(BlockJUnit4ClassRunner.class)
public class SerialTest {
	
	@Test
	public void testParityValueOfChar() {
		assertEquals(Parity.NONE, Parity.valueOf('N'));
		assertEquals(Parity.ODD, Parity.valueOf('O'));
		assertEquals(Parity.EVEN, Parity.valueOf('E'));
		assertEquals(Parity.MARK, Parity.valueOf('M'));
		assertEquals(Parity.SPACE, Parity.valueOf('S'));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testParityValueOfCharInvalid() {
		Parity.valueOf('A');
	}
}
