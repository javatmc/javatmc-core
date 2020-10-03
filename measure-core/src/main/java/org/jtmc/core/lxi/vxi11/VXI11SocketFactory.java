package org.jtmc.core.lxi.vxi11;

import java.io.IOException;
import java.net.InetAddress;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jtmc.core.visa.exception.UnsupportedSocketException;
import org.jtmc.core.visa.factory.ISocketFactory;

/**
 * This factory class is capable of creating VXI-11 sockets for SCPI
 * communication
 * 
 * <p>
 * It supports the VISA resource string syntax for VXI-11 sockets.
 * <p>
 * Format: <i>TCPIP[board]::[host address][::Instrument Name][::INSTR]</i>
 */
public class VXI11SocketFactory implements ISocketFactory {

	private Pattern pattern = Pattern.compile("TCPIP(?<board>[0-9]*)::(?<host>[^:]{1,})(::?<instrument>[^:]{1,})?(::INSTR)?");

	@Override
	public boolean supports(String resourceString) {
		return pattern.matcher(resourceString).matches();
	}

	@Override
	public VXI11Socket create(String resourceString) throws IOException, UnsupportedSocketException {
		Matcher matcher = pattern.matcher(resourceString);
		if(!matcher.matches()) {
			throw new UnsupportedSocketException();
		}
		//int board = Integer.parseInt(matcher.group("board"));
		String host = matcher.group("host");
		String instrument = matcher.group("instrument");

		return new VXI11Socket(InetAddress.getByName(host), 0, instrument);
	}

    
}