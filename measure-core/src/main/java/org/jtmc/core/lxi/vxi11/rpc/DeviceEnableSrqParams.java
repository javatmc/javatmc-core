package org.jtmc.core.lxi.vxi11.rpc;

import org.acplt.oncrpc.*;
import java.io.IOException;

public class DeviceEnableSrqParams implements XdrAble {
    public DeviceLink lid;
    public boolean enable;
    public byte [] handle;

    public DeviceEnableSrqParams() {
    }

    public DeviceEnableSrqParams(XdrDecodingStream xdr) throws OncRpcException, IOException {
        xdrDecode(xdr);
    }

    public void xdrEncode(XdrEncodingStream xdr) throws OncRpcException, IOException {
        lid.xdrEncode(xdr);
        xdr.xdrEncodeBoolean(enable);
        xdr.xdrEncodeDynamicOpaque(handle);
    }

    public void xdrDecode(XdrDecodingStream xdr) throws OncRpcException, IOException {
        lid = new DeviceLink(xdr);
        enable = xdr.xdrDecodeBoolean();
        handle = xdr.xdrDecodeDynamicOpaque();
    }

}