package org.jtmc.core.lxi.vxi11.rpc;

import org.acplt.oncrpc.*;
import java.io.IOException;

public class DeviceDocmdResponse implements XdrAble {
    public DeviceErrorCode error;
    public byte [] data_out;

    public DeviceDocmdResponse() {
    }

    public DeviceDocmdResponse(XdrDecodingStream xdr)
           throws OncRpcException, IOException {
        xdrDecode(xdr);
    }

    public void xdrEncode(XdrEncodingStream xdr)
           throws OncRpcException, IOException {
        error.xdrEncode(xdr);
        xdr.xdrEncodeDynamicOpaque(data_out);
    }

    public void xdrDecode(XdrDecodingStream xdr) throws OncRpcException, IOException {
        error = new DeviceErrorCode(xdr);
        data_out = xdr.xdrDecodeDynamicOpaque();
    }

}