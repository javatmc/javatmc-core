package org.jtmc.core.hardware.serial;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.concurrent.TimeoutException;

import org.jtmc.core.serial.FlowControl;
import org.jtmc.core.serial.Parity;
import org.jtmc.core.serial.SerialSocket;
import org.jtmc.core.serial.StopBits;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;


/**
 * Hardware tests of Serial sockets
 * 
 * 
 */
@RunWith(BlockJUnit4ClassRunner.class)
public class SerialSocketTest {

	private final static String PORT = "COM3";

	@Test
	public void testSerialSocketConstructor() throws IOException, TimeoutException {
		//System.err.println(System.getProperty("java.library.path"));
		try(SerialSocket socket = new SerialSocket(PORT, 9600, 8, Parity.NONE, StopBits.ONE, FlowControl.NONE)) {
			socket.send(ByteBuffer.wrap(new byte[]{1, 2, 3, 4}));
			//ByteBuffer response = socket.receive(0, 1000);
			//assertEquals(response.position(), 4);

			//assertEquals(new byte[]{1, 2, 3, 4}, response.array());
		}
	}
	
	@Test
	public void testSerialSocketCreateViaStringParams() throws IOException {
		try(SerialSocket socket = SerialSocket.create(PORT, "9600/8/N/1")) {
			socket.send(ByteBuffer.wrap(new byte[]{1, 2, 3, 4}));
			//ByteBuffer response = socket.receive(0, 1000);
			//assertEquals(response.position(), 4);
			//assertEquals(new byte[]{1, 2, 3, 4}, response.array());
		}
	}
}