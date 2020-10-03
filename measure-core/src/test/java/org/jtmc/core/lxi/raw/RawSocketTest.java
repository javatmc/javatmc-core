package org.jtmc.core.lxi.raw;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

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
        socket.close();
        assertEquals(false, socket.isConnected());
    }

}