package org.jtmc.core.scpi.socket;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import org.jtmc.core.device.ISocket;
import org.jtmc.core.scpi.ISCPISocket;
import org.jtmc.core.scpi.SCPICommand;

/**
 * RawSCPISocket implements the most common way of sending SCPI commands
 * to instruments
 * 
 * <p>By default it will send SCPI commands as is with newline terminations appended
 * after each command
 * 
 * <p>When sending multiple commands it will use semicolon to separate commands
 */
public class RawSCPISocket implements ISCPISocket {

	public final static char DEFAULT_TERMINATION = '\n';

	public final static char DEFAULT_SEPARATOR = ';';

	private final char termination;

	private final char separator;

	private final ISocket socket;

	private final Charset charset;

	/**
	 * Constructs a RawSCPISocket with the default termination and separator characters
	 * 
	 * @param socket Socket to the instrument
	 */
	public RawSCPISocket(final ISocket socket) {
		this(socket, DEFAULT_TERMINATION, DEFAULT_SEPARATOR);
	}

	/**
	 * Constructs a RawSCPISocket with the provided termination and separator characters
	 * 
	 * @param socket Socket to the instrument
	 * @param termination Character used to terminate all commands
	 * @param separator Character used to separate commands
	 */
	public RawSCPISocket(final ISocket socket, final char termination, final char separator) {
		this(socket, termination, separator, StandardCharsets.ISO_8859_1);
	}

	/**
	 * Constructs a RawSCPISocket with the default termination and separator characters
	 * 
	 * @param socket Socket to the instrument
	 * @param termination Character used to terminate all commands
	 * @param separator Character used to separate commands
	 * @param charset Character encoding to use
	 */
	public RawSCPISocket(final ISocket socket, final char termination, final char separator, final Charset charset) {
		this.socket = socket;
		this.termination = termination;
		this.separator = separator;
		this.charset = charset;
	}

	@Override
	public void send(SCPICommand... commands) throws IOException {
		if(commands.length == 0) {
			return;
		}
		ByteBuffer output = ByteBuffer.wrap(concat(this.termination, this.separator, commands).getBytes(charset));
		socket.send(output);
	}

	@Override
	public String receive(long timeout) throws SocketTimeoutException, IOException {
		ByteBuffer response = socket.receive(this.termination, timeout);
		return new String(response.array(), charset);
	}

	@Override
	public String receive(int count, long timeout) throws SocketTimeoutException, IOException {
		ByteBuffer response = socket.receive(count, timeout);
		return new String(response.array(), charset);
	}
	
	static String concat(char termination, char separator, SCPICommand... commands) {
		StringBuilder builder = new StringBuilder();
		for(int i=0;i<commands.length;i++) {
			builder.append(commands[i].toString());
			if(i != commands.length - 1) {
				builder.append(separator);
			}
		}
		builder.append(termination);
		return builder.toString();
	}
}