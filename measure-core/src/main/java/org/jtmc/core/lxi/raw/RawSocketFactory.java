package org.jtmc.core.lxi.raw;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jtmc.core.visa.exception.UnsupportedSocketException;
import org.jtmc.core.visa.factory.ISocketFactory;

/**
 * This factory class is capable of creating Raw TCP sockets for SCPI
 * communication.
 * 
 * <p>It supports the VISA resource string syntax for raw tcp sockets.
 * 
 * <p>Format: <b>TCPIP[board]::[host address]::[port]::SOCKET</b>
 */
public class RawSocketFactory implements ISocketFactory {

  private static final Pattern pattern = Pattern.compile(
      "TCPIP(?<board>[0-9]*)::(?<host>.{1,})::(?<port>[0-9]{1,5})::SOCKET");

  @Override
  public boolean supports(String connectionInfo) {
    return pattern.matcher(connectionInfo).matches();
  }

  @Override
  public RawSocket create(String resourceString) 
      throws IOException, UnsupportedSocketException {

    Matcher matcher = pattern.matcher(resourceString);
    if (!matcher.matches()) {
      throw new UnsupportedSocketException();
    }
    String host = matcher.group("host");
    int port = Integer.parseInt(matcher.group("port"));
    int board = 0;
    if (!matcher.group("board").trim().isEmpty()) {
      board = Integer.parseInt(matcher.group("board"));
    }
    return new RawSocket(host, port, board);
  }

}
