package org.jtmc.cli.scpi;

import java.io.File;
import java.util.List;
import java.util.concurrent.Callable;
import org.jtmc.cli.visa.VISASocketOption;
import org.jtmc.core.device.ISocket;
import org.jtmc.core.scpi.SCPICommand;
import org.jtmc.core.scpi.socket.RawSCPISocket;
import picocli.CommandLine.ArgGroup;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

/**
 * SCPISend is a command that can be used to send SCPI commands to instruments.
 */
@Command(name = "scpi-send")
public class SCPISend implements Callable<Integer> {
  
  @ArgGroup(exclusive = true)
  Input input;

  @ArgGroup(exclusive = true)
  VISASocketOption socketOption;
  
  static class Input {
    @Option(names = {"--commands", "-c"}, required = false)
    List<String> commands;

    @Option(names = {"--script"}, required = false)
    File script;

    @Option(names = {"--interactive", "-i"}, required = true)
    boolean interactive;
  }

  @Override
  public Integer call() throws Exception {
    try (ISocket socket = socketOption.getSocket()) {
      RawSCPISocket scpiSocket = new RawSCPISocket(socket);
      for (String command : input.commands) {
        SCPICommand scpiCommand = new SCPICommand(command);
        scpiSocket.send(scpiCommand);
        if (scpiCommand.isQuery()) {
          System.out.println(scpiSocket.receive(1000));
        }
      }
      return 0;
    }
  }
}
