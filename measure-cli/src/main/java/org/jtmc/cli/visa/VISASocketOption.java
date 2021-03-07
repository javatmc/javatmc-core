package org.jtmc.cli.visa;

import java.io.IOException;
import org.jtmc.core.device.ISocket;
import picocli.CommandLine.ArgGroup;
import picocli.CommandLine.Option;

/**
 * VISASocketOption is a composite argument group that holds the underlying
 * socket implementations' options.
 */
public class VISASocketOption {

  @ArgGroup(exclusive = false)
  RawSocketOption rawSocket;

  @ArgGroup(exclusive = false)
  SerialSocketOption serialSocket;

  @ArgGroup(exclusive = false)
  VXI11SocketOption vxi11Socket;

  @Option(names = { "--resource" })
  String resourceString;

  /**
   * Returns the underlying socket implementation.
   * @return Socket
   * @throws IOException if there was an error opening the socket
   */
  public ISocket getSocket() throws IOException {
    if (rawSocket != null) {
      return rawSocket.getSocket();
    } else if (serialSocket != null) {
      return serialSocket.getSocket();
    } else if (vxi11Socket != null) {
      return vxi11Socket.getSocket();
    }
    return null;
  }
}
