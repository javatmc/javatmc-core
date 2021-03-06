package org.jtmc.cli.visa;

import java.io.IOException;
import org.jtmc.core.serial.FlowControl;
import org.jtmc.core.serial.Parity;
import org.jtmc.core.serial.SerialSocket;
import org.jtmc.core.serial.StopBits;
import picocli.CommandLine.ArgGroup;
import picocli.CommandLine.Option;

/**
 * SerialSocketOption holds the arguments of a SerialSocket.
 */
public class SerialSocketOption {
  @Option(names = { "--serial-port" })
  String port;

  @ArgGroup(exclusive = false)
  SerialSocketDetailedConfiguration socket;

  /**
   * Returns the configured Serial socket.
   * @return Serial socket
   * @throws IOException if there was an error opening the socket
   */
  public SerialSocket getSocket() throws IOException {
    return new SerialSocket(
                    port,
                    socket.baudrate,
                    socket.databits,
                    socket.parity,
                    socket.stopbits,
                    FlowControl.NONE);
  }

  /**
   * SerialSocketDetailedConfiguration is an argument group that allows configuring
   * the SerialSocket's options individually.
   */
  public static class SerialSocketDetailedConfiguration {
    @Option(names = {"--serial-baudrate"}, defaultValue = "9600")
    int baudrate;

    @Option(names = {"--serial-databits"}, defaultValue = "8")
    int databits;

    @Option(names = {"--serial-parity"}, defaultValue = "NONE")
    Parity parity;

    @Option(names = {"--serial-stopbits"}, defaultValue = "1")
    StopBits stopbits;
    
  }
}
