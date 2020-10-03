package org.jtmc.core.serial;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortIOException;
import com.fazecast.jSerialComm.SerialPortTimeoutException;

import org.jtmc.core.device.ISocket;

/**
 * SerialSocket
 */
public class SerialSocket implements ISocket {

	private SerialPort socket;

	/**
	 * Constructs a Serial socket with the provided baudrate and default 8 bit data length,
	 * no parity and a single stopbit. Flow control is disabled.
	 * 
	 * @param port Port identifier, on Windows COMn, on Unix /dev/ttySn
	 * @param baudrate Baudrate
	 * @throws IOException if there was an error opening the socket
	 */
	public SerialSocket(String port, int baudrate) throws IOException {
		this(port, baudrate, 8, Parity.NONE, StopBits.ONE);
	}

	/**
	 * Constructs a Serial socket with the provided parameters. Flow control is disabled.
	 * 
	 * @param port Port identifier, on Windows COMn, on Unix /dev/ttySn
	 * @param baudrate Baudrate
	 * @param databits Number of databits, valid values are {@code 5-8}
	 * @param parity Parity bit (None, Even, Odd, Mark, Space)
	 * @param stopbits Stop bit count
	 * @throws IOException if there was an error opening the socket
	 */
	public SerialSocket(String port, int baudrate, int databits, Parity parity, StopBits stopbits) throws IOException {
		this(port, baudrate, databits, parity, stopbits, FlowControl.NONE);
	}

	/**
	 * Constructs a Serial socket with the provided parameters.
	 * 
	 * @param port Port identifier, on Windows COMn, on Unix /dev/ttySn
	 * @param baudrate Baudrate
	 * @param databits Number of databits, valid values are {@code 5-8}
	 * @param parity Parity bit (None, Even, Odd, Mark, Space)
	 * @param stopbits Stop bit count
	 * @param flowControl Flow control mode
	 * @throws IOException if there was an error opening the socket
	 */
	public SerialSocket(String port, int baudrate, int databits, Parity parity, StopBits stopbits, FlowControl flowControl) throws IOException {
		this.socket = SerialPort.getCommPort(port);
		this.socket.openPort();
		this.socket.setBaudRate(baudrate);
		this.socket.setNumDataBits(databits);
		this.socket.setParity(parity.getId());
		this.socket.setNumStopBits(stopbits.getId());
		this.socket.setFlowControl(flowControl.getValue());
	}

	private final static Pattern CONFIG_PATTERN = Pattern.compile("(?<baudrate>[0-9]+)/(?<databits>[0-9])/(?<parity>[NOEMS])/(?<stopbits>[12])");

	/**
	 * Constructs a SerialSocket using a string configuration
	 * 
	 * <p>Format: Baudrate/Databits/Parity/Stopbits
	 * 
	 * <p>Baudrate: any integer
	 * <p>Databits: integer, 5-8
	 * <p>Parity: N (None), O (Odd), E (Even), M (Mark), S (Space)
	 * 
	 * <p>Example: 9600/8/N/1
	 * 
	 * @param port Port identifier, on Windows COMn, on Unix /dev/ttySn
	 * @param params Configuration as string
	 * @return Serial socket
	 * @throws IOException
	 */
	public static SerialSocket create(String port, String params) throws IOException {
		Matcher matcher = CONFIG_PATTERN.matcher(params);
		if(!matcher.matches()) {
			throw new IllegalArgumentException("Invalid serial socket configuration: " + params);
		}
		int baudrate = Integer.parseInt(matcher.group("baudrate"));
		int databits = Integer.parseInt(matcher.group("databits"));
		String parity = matcher.group("parity");
		int stopbits = Integer.parseInt(matcher.group("stopbits"));

		return new SerialSocket(port, baudrate, databits, Parity.valueOf(parity.charAt(0)), StopBits.valueOf(stopbits));
	}

	@Override
	public void close() {
		socket.closePort();
	}

	@Override
	public boolean isConnected() {
		return this.socket != null && this.socket.isOpen();
	}

	@Override
	public String getResourceString() {
		return "ASRL" + SerialSocketFactory.getPortNumber(this.socket.getSystemPortName());
	}

	@Override
	public void send(ByteBuffer message) throws IOException, SerialPortIOException, SerialPortTimeoutException {
		if(socket == null) {
			throw new IOException("Socket is closed.");
		}
		if(!message.hasArray()) {
			throw new IllegalArgumentException("Message empty.");
		}
		socket.getOutputStream().write(message.array());
	}

	@Override
	public ByteBuffer receive(int count, long timeout) throws IOException, SocketTimeoutException {
		//int previousTimeout = socket.getSerialPortTimeout();
		//socket.setSerialPortTimeout((int)timeout);
		//socket.setComPortTimeouts(newTimeoutMode, newReadTimeout, newWriteTimeout);

		byte[] input = new byte[count];
		socket.getInputStream().read(input);

		//socket.setSerialPortTimeout(previousTimeout);

		return ByteBuffer.wrap(input);
	}

	@Override
	public ByteBuffer receive(char delimiter, long timeout) throws IOException, SocketTimeoutException {
		try {
			//int previousTimeout = socket.getSerialPortTimeout();
			//socket.setSerialPortTimeout((int)timeout);
			
			ArrayList<Byte> input = new ArrayList<>();
			byte in = (byte) socket.getInputStream().read();
			while(in != delimiter) {
				input.add(in);
				in = (byte) socket.getInputStream().read();
			}
			//socket.setSerialPortTimeout(previousTimeout);
			ByteBuffer bytes = ByteBuffer.allocate(input.size());
			for(byte b : input) {
				bytes.put(b);
			}
			return bytes;
		} catch(SerialPortTimeoutException e) {
			throw new SocketTimeoutException(e.getMessage());
		}
	}

}