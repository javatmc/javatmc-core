package org.jtmc.core.scpi.adapter;

import java.io.IOException;
import java.io.OutputStream;
import org.jtmc.core.scpi.ISCPISocket;
import org.jtmc.core.scpi.SCPICommand;
import org.jtmc.core.scpi.SCPISocketAdapter;

/**
 * SCPITracer is an adapter class that can be used to record SCPI communication.
 */
public class SCPITracer extends SCPISocketAdapter {

  private OutputStream outbound;

  private OutputStream inbound;

  /**
   * Constructs a new SCPI tracer given a SCPI socket and streams for recording
   * inbound and outbound activity.
   * 
   * @param adapter SCPI socket
   * @param inbound Output stream for inbound communication
   * @param outbound Output stream for outbound communication
   */
  public SCPITracer(ISCPISocket adapter, OutputStream inbound, OutputStream outbound) {
    super(adapter);
    this.outbound = outbound;
    this.inbound = inbound;
  }

  private void outbound(SCPICommand... commands) {
    try {
      //String out = ISCPISocket.concat(';', ' ', commands);
      String out = "";
      outbound.write(out.getBytes());
    } catch (IOException e) {
      //TODO: log
      //log.warn("Error writing outbound message.", e);
    }
  }

  private void inbound(String command) {
    try {
      inbound.write(command.getBytes());
    } catch (IOException e) {
      //TODO: log
      //log.warn("Error writing inbound message.", e);
    }
  }

  @Override
  public void send(SCPICommand... commands) throws IOException {
    super.send(commands);
    this.outbound(commands);
  }

  @Override
  public String receive(long timeout) throws IOException {
    String response = super.receive(timeout);
    this.inbound(response);
    return response;
  }

  @Override
  public SCPITracer clone(ISCPISocket socket) {
    return new SCPITracer(socket, this.inbound, this.outbound);
  }

}
