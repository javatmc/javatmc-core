package org.jtmc.core.lxi.vxi11.portmap;

import java.io.IOException;
import org.acplt.oncrpc.OncRpcException;
import org.acplt.oncrpc.XdrAble;
import org.acplt.oncrpc.XdrDecodingStream;
import org.acplt.oncrpc.XdrEncodingStream;

/**
 * PortmapResponse contains response information for Portmap responses.
 */
public class PortmapResponse implements XdrAble {

  private int port;

  public void xdrEncode(XdrEncodingStream xdr) throws OncRpcException, IOException {
    xdr.xdrEncodeInt(port);
  }

  public void xdrDecode(XdrDecodingStream xdr) throws OncRpcException, IOException {
    port = xdr.xdrDecodeInt();
  }
  
  /**
   * Returns the port number for the given response.
   * 
   * @return Port number
   */
  public int getPort() {
    return port;
  }
}
