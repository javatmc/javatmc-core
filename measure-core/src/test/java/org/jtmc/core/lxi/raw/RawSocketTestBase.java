package org.jtmc.core.lxi.raw;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.junit.After;
import org.junit.Before;

/**
 * RawSocketBase
 */
public class RawSocketTestBase {

	private final int port;

	private ServerSocket server;

	public RawSocketTestBase(int port) {
		this.port = port;
	}

	@Before
	public void setupServer() throws IOException {
		server = new ServerSocket(port);

		Thread accept = new Thread(() -> {
			try {
				Socket s = server.accept();
				while(!s.isClosed()) {
					int b = s.getInputStream().read();
					s.getOutputStream().write(b);
					s.getOutputStream().flush();
				}
			} catch (IOException e) {}
		});
		accept.start();
	}

	@After
	public void teardownServer() throws IOException {
		server.close();
	}
}