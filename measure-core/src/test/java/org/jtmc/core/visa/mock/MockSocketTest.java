package org.jtmc.core.visa.mock;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.concurrent.TimeoutException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

/**
 * MockSocketTest
 */
@RunWith(BlockJUnit4ClassRunner.class)
public class MockSocketTest {

	@Test
	public void testConstructorDefault() {
		MockSocket socket = new MockSocket("TestDevice", "wavegen");
		assertEquals(true, socket.isConnected());
		assertEquals("TestDevice", socket.getClassName());
		assertEquals("wavegen", socket.getInstrumentName());

		socket.close();
	}

	@Test
	public void testConstructorNoInstrumentName() {
		MockSocket socket = new MockSocket("TestDevice");
		assertEquals(true, socket.isConnected());
		assertEquals("TestDevice", socket.getClassName());
		assertEquals(false, socket.getInstrumentName() != null);

		socket.close();
	}

	@Test
	public void testResourceString() {
		MockSocket socket = new MockSocket("TestDevice", "inst0");
		assertEquals("MOCK::TestDevice::inst0::INSTR", socket.getResourceString());

		socket.close();
	}

	@Test
	public void testResourceStringNoInstrumentName() {
		MockSocket socket = new MockSocket("TestDevice");
		assertEquals("MOCK::TestDevice::INSTR", socket.getResourceString());

		socket.close();
	}

	@Test
	public void testConnectionLifecycle() {
		MockSocket socket = new MockSocket("TestDevice", "wavegen");
		assertEquals(true, socket.isConnected());

		socket.close();
		assertEquals(false, socket.isConnected());
	}

	@Test
	public void testConnectionIdempotent() {
		MockSocket socket = new MockSocket("TestDevice", "wavegen");
		assertEquals(true, socket.isConnected());

		socket.close();
		assertEquals(false, socket.isConnected());
		socket.close();
		assertEquals(false, socket.isConnected());
	}

	@Test
	public void testCommunication() throws IOException, TimeoutException {
		MockSocket socket = new MockSocket("TestDevice", "wavegen");
		socket.send(ByteBuffer.allocate(1));
		socket.receive(1, 0);
		socket.close();
	}
	
}