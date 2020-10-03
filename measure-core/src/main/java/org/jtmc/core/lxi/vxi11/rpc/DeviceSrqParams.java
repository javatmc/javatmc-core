package org.jtmc.core.lxi.vxi11.rpc;

import org.acplt.oncrpc.*;
import java.io.IOException;

public class DeviceSrqParams implements XdrAble {
    public byte [] handle;

    public DeviceSrqParams() {
    }

    public DeviceSrqParams(XdrDecodingStream xdr) throws OncRpcException, IOException {
        xdrDecode(xdr);
    }

    public void xdrEncode(XdrEncodingStream xdr) throws OncRpcException, IOException {
        xdr.xdrEncodeDynamicOpaque(handle);
    }

    public void xdrDecode(XdrDecodingStream xdr) throws OncRpcException, IOException {
        handle = xdr.xdrDecodeDynamicOpaque();
    }

}