package org.jtmc.core.lxi.raw;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.net.InetAddress;
import java.nio.ByteBuffer;

import org.jtmc.core.visa.DeviceIdentifier;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

/**
 * RawSocketTest
 */
@RunWith(BlockJUnit4ClassRunner.class)
public class RawSocketTest extends RawSocketTestBase {

	public RawSocketTest() {
		super(50000);
	}

	@Test
	public void testConstructorValid() throws IOException {
		RawSocket socket = new RawSocket("127.0.0.1", 50000, 0);
		assertEquals("127.0.0.1", socket.getHost().getHostAddress());
		assertEquals(50000, socket.getPort());
		assertEquals(0, socket.getBoard());
		socket.close();
	}

	@Test
	public void testResourceString() throws IOException {
		RawSocket socket = new RawSocket("127.0.0.1", 50000, 0);
		assertEquals("TCP0::127.0.0.1::50000::SOCKET", socket.getResourceString());
		socket.close();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructorInvalidPortNegative() throws IOException {
		RawSocket socket = new RawSocket("localhost", -1, 0);
		socket.close();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructorInvalidPortTooHigh() throws IOException {
		RawSocket socket = new RawSocket("localhost", 65536, 0);
		socket.close();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructorInvalidBoard() throws IOException {
		RawSocket socket = new RawSocket("localhost", 50000, -1);
		socket.close();
	}

	@Test
	public void testConnectionLifeCycle() throws IOException {
		RawSocket socket = new RawSocket("localhost", 50000, 0);
		assertEquals(true, socket.isConnected());

		socket.close();
		assertEquals(false, socket.isConnected());
	}

	@Test
	public void testConnectionIdempotent() throws IOException {
		RawSocket socket = new RawSocket("localhost", 50000, 0);
		assertEquals(true, socket.isConnected());

		socket.close();
		assertEquals(false, socket.isConnected());
		socket.close();
		assertEquals(false, socket.isConnected());
	}

	@Test
	public void testCommunicationReadAmount() throws IOException {
		RawSocket socket = new RawSocket("localhost", 50000);
		socket.send(ByteBuffer.wrap("message".getBytes()));
		String response = new String(socket.receive("message".length(), 1000).array());
		assertEquals("message", response);
		socket.close();
	}

	@Test
	public void testCommunicationReadUntil() throws IOException {
		RawSocket socket = new RawSocket("localhost", 50000);
		socket.send(ByteBuffer.wrap("message\n".getBytes()));
		String response = new String(socket.receive('\n', 1000).array());
		assertEquals("message", response);

		socket.send(ByteBuffer.wrap("msg1\nmsg2\n".getBytes()));
		String response1 = new String(socket.receive('\n', 1000).array());
		assertEquals("msg1", response1);
		String response2 = new String(socket.receive('\n', 1000).array());
		assertEquals("msg2", response2);

		socket.close();
	}

	@Test
	public void testRawInstrumentEndpoint() throws IOException {
		RawInstrumentEndpoint endpoint = new RawInstrumentEndpoint(InetAddress.getByName("localhost"), 50000, DeviceIdentifier.UNKNOWN);
		RawSocket socket = endpoint.connect();

		assertEquals(true, socket.isConnected());
		socket.close();
	}

}