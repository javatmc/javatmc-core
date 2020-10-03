package org.jtmc.core.visa.mock;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.Collection;

import org.assertj.core.util.Arrays;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * RawSocketTest
 */
@RunWith(Parameterized.class)
public class MockSocketFactoryTest {

	@Parameters(name = "{0}")
	public static Collection<?> data() {
		return Arrays.asList(new Object[][] { 
			{ "MOCK::TestDevice", "TestDevice", null, false, true },
            { "MOCK::TestDevice::INSTR", "TestDevice", null, false, true },
            { "MOCK::TestDevice::wavegen", "TestDevice", "wavegen", true, true },
			{ "MOCK::TestDevice::wavegen::INSTR", "TestDevice", "wavegen", true, true }, 

			{ "MOCK::TestDevice::wavegen::ABC", null, null, false, false },
            { "MOCK::TestDevice::INSTR::INSTR", null, null, false, false },
            { "MOCK::TestDevice::wavegen::wavegen", null, null, false, false },
		});
	}

	private String input;

	private String className;

	private String instrumentName;

	private boolean instrumentNamePresent;

	private boolean support;

	public MockSocketFactoryTest(String input, String className, String instrumentName, boolean instrumentNamePresent, boolean support) {
        this.input = input;
        this.className = className;
        this.instrumentName = instrumentName;
        this.instrumentNamePresent = instrumentNamePresent;
        this.support = support;
    }

	@Test
	public void test() throws IOException {
        MockSocketFactory factory = new MockSocketFactory();
		boolean factorySupports = factory.supports(input);
		assertEquals(support, factory.supports(input));
		if(!factorySupports) {
			return;
		}

		MockSocket socket = factory.create(input);
		assertEquals(className, socket.getClassName());
		if(socket.getInstrumentName() == null && !instrumentNamePresent) {
            return;
        }
		assertEquals(instrumentName, socket.getInstrumentName());
	}
	
}