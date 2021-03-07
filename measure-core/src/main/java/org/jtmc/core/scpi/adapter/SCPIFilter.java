package org.jtmc.core.scpi.adapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.function.Predicate;
import java.util.stream.Stream;
import org.jtmc.core.scpi.ISCPISocket;
import org.jtmc.core.scpi.SCPICommand;
import org.jtmc.core.scpi.SCPISocketAdapter;

/**
 * SCPIFilter is used to block certain commands from being sent to the device.
 */
public class SCPIFilter extends SCPISocketAdapter {

  private final Predicate<SCPICommand> allow;

  /**
   * Constructs a new SCPI filter using a socket and a filter predicate.
   * 
   * @param adapter SCPI socket to pass call to
   * @param allow A predicate for allowing commands, returns {@code true} if the command is allowed
   */
  public SCPIFilter(ISCPISocket adapter, Predicate<SCPICommand> allow) {
    super(adapter);
    this.allow = allow;
  }

  @Override
  public void send(SCPICommand... commands) throws IOException {
    ArrayList<SCPICommand> filtered = new ArrayList<>();
    Stream.of(commands).filter(allow).forEach(filtered::add);
    SCPICommand[] out = new SCPICommand[filtered.size()];
    out = filtered.toArray(out);
    super.send(out);
  }

  @Override
  public SCPIFilter clone(ISCPISocket socket) {
    return new SCPIFilter(socket, this.allow);
  }

}
