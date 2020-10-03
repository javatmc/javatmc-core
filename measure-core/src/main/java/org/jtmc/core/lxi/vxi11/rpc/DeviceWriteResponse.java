package org.jtmc.core.lxi.vxi11.rpc;

import org.acplt.oncrpc.*;
import java.io.IOException;

public class DeviceWriteResponse implements XdrAble {
    public DeviceErrorCode error;
    public int size;

    public DeviceWriteResponse() {
    }

    public DeviceWriteResponse(XdrDecodingStream xdr) throws OncRpcException, IOException {
        xdrDecode(xdr);
    }

    public void xdrEncode(XdrEncodingStream xdr) throws OncRpcException, IOException {
        error.xdrEncode(xdr);
        xdr.xdrEncodeInt(size);
    }

    public void xdrDecode(XdrDecodingStream xdr) throws OncRpcException, IOException {
        error = new DeviceErrorCode(xdr);
        size = xdr.xdrDecodeInt();
    }

    public int getError() {
        return error.value;
    }

}