package org.jtmc.core.lxi.raw;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.nio.ByteBuffer;
import java.util.ArrayList;

import org.jtmc.core.device.ISocket;

/**
 * RawSocket implements a generic TCP/IP socket for sending and receiving byte streams
 */
public class RawSocket implements ISocket {

	private final InetAddress host;

	private final int port;

	private final int board;

	private transient Socket socket;

	public RawSocket(String host, int port) throws IOException {
		this(host, port, 0);
	}

	public RawSocket(String host, int port, int board) throws IOException {
		this(InetAddress.getByName(host), port, board);
	}

	public RawSocket(InetAddress host, int port, int board) throws IOException {
		if(board < 0) {
			throw new IllegalArgumentException("Board number must be positive: " + board);
		}
		this.host = host;
		this.port = port;
		this.board = board;
		socket = new Socket(host, port);
	}
	
	public int getBoard() {
		return board;
	}

	public InetAddress getHost() {
		return host;
	}

	public int getPort() {
		return port;
	}

	@Override
	public String getResourceString() {
		return String.format("TCP%d::%s::%d::SOCKET", board, host.getHostAddress(), port);
	}

	@Override
	public String toString() {
		return String.format("RawSocket[host=%s, port=%d, board=%d]", host.getHostAddress(), port, board);
	}

	@Override
	public void send(ByteBuffer message) throws IOException {
		if(socket == null) {
			throw new IOException("Socket not connected");
		}
		if(!message.hasArray()) {
			throw new IOException("Message is empty.");
		}
		socket.getOutputStream().write(message.array());
	}

	@Override
	public ByteBuffer receive(final int count, final long timeout) throws IOException, SocketTimeoutException {
		int previousTimeout = socket.getSoTimeout();
		try {
			socket.setSoTimeout((int)timeout);

			ByteBuffer buffer = ByteBuffer.allocate(count);
			int read = 0;
			while(read < count) {
				buffer.put((byte)socket.getInputStream().read());
				read++;
			}
			return buffer;
		} finally {
			socket.setSoTimeout((int)previousTimeout);
		}
	}

	@Override
	public ByteBuffer receive(final char delimiter, final long timeout) throws IOException, SocketTimeoutException {
		int previousTimeout = socket.getSoTimeout();
		try {
			socket.setSoTimeout((int)timeout);
			
			ArrayList<Byte> input = new ArrayList<>();
			byte in = (byte) socket.getInputStream().read();
			while(in != delimiter) {
				input.add(in);
				in = (byte) socket.getInputStream().read();
			}
			ByteBuffer bytes = ByteBuffer.allocate(input.size());
			for(byte b : input) {
				bytes.put(b);
			}
			return bytes;
		} finally {
			socket.setSoTimeout((int)previousTimeout);
		}
	}

	@Override
	public void close() {
		try {
			socket.close();
		} catch (IOException | NullPointerException e) {
			//TODO: warn
		} finally {
			socket = null;
		}
	}

	@Override
	public boolean isConnected() {
		return socket != null && !socket.isClosed();
	}
	
}