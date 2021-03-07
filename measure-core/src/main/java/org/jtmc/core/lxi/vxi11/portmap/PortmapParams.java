package org.jtmc.core.lxi.vxi11.portmap;

import java.io.IOException;

import org.acplt.oncrpc.OncRpcException;
import org.acplt.oncrpc.XdrAble;
import org.acplt.oncrpc.XdrDecodingStream;
import org.acplt.oncrpc.XdrEncodingStream;

/**
 * PortmapParams contains request information for Portmap requests.
 */
public class PortmapParams implements XdrAble {

  private int program;

  private int version;

  private int protocol;

  private int port;

  private int data = 0;

  /**
   * Constructs a new PortmapParam request container.
   * 
   * @param program Program ID to discover
   * @param version Program Version number
   * @param protocol Underlying protocol (TCP or UDP)
   * @param port Port number
   */
  public PortmapParams(int program, int version, int protocol, int port) {
    this.program = program;
    this.version = version;
    this.protocol = protocol;
    this.port = port;
  }

  @Override
  public void xdrEncode(XdrEncodingStream xdr) throws OncRpcException, IOException {
    xdr.xdrEncodeInt(program);
    xdr.xdrEncodeInt(version);
    xdr.xdrEncodeInt(protocol);
    xdr.xdrEncodeInt(port);
    xdr.xdrEncodeInt(data);
  }

  @Override
  public void xdrDecode(XdrDecodingStream xdr) throws OncRpcException, IOException {
    program = xdr.xdrDecodeInt();
    version = xdr.xdrDecodeInt();
    protocol = xdr.xdrDecodeInt();
    port = xdr.xdrDecodeInt();
    data = xdr.xdrDecodeInt();
  }

}
