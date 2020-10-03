package org.jtmc.core.serial;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jtmc.core.visa.factory.ISocketFactory;

/**
 * SerialSocketFactory
 */
public class SerialSocketFactory implements ISocketFactory {

	private Pattern pattern = Pattern.compile("ASRL(?<port>[0-9]*)::INSTR");

	@Override
	public boolean supports(String connectionInfo) {
		return pattern.matcher(connectionInfo).matches();
	}

	@Override
	public SerialSocket create(String connectionInfo) throws IOException {
		Matcher matcher = pattern.matcher(connectionInfo);
		if(matcher.matches()) {
			int port = Integer.parseInt(matcher.group("port"));
			//TODO: serial configuration
			return new SerialSocket(getPortPath(port), 9600);
		}
		throw new IllegalArgumentException(connectionInfo + " doesn't match " + pattern.pattern());
	}

	/**
	 * 
	 * @param port
	 * @return
	 */
	public static String getPortPath(int port) {
		String os = System.getProperty("os.name");
		if(os.startsWith("Windows")) {
			return "COM" + port;
		} else if(os.contains("nix") || os.contains("nux")) {
			return "/dev/ttyS" + port;
		}
		return null;
	}

	public static int getPortNumber(String portPath) {
		return 0;
	}
}