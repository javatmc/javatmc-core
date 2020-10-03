package org.jtmc.core.lxi.vxi11.rpc;

import org.acplt.oncrpc.*;
import java.io.IOException;

public class CreateLinkResponse implements XdrAble {
    public DeviceErrorCode error;
    public DeviceLink lid;
    public short abortPort;
    public int maxRecvSize;

    public CreateLinkResponse() {
    }

    public CreateLinkResponse(XdrDecodingStream xdr) throws OncRpcException, IOException {
        xdrDecode(xdr);
    }

    public void xdrEncode(XdrEncodingStream xdr) throws OncRpcException, IOException {
        error.xdrEncode(xdr);
        lid.xdrEncode(xdr);
        xdr.xdrEncodeShort(abortPort);
        xdr.xdrEncodeInt(maxRecvSize);
    }

    public void xdrDecode(XdrDecodingStream xdr) throws OncRpcException, IOException {
        error = new DeviceErrorCode(xdr);
        lid = new DeviceLink(xdr);
        abortPort = xdr.xdrDecodeShort();
        maxRecvSize = xdr.xdrDecodeInt();
    }

    public int getLinkId() {
        return lid.value;
    }

}