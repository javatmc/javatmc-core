package org.jtmc.core.lxi.vxi11.rpc;

import org.acplt.oncrpc.*;
import java.io.IOException;

public class DeviceLockParams implements XdrAble {
    public DeviceLink lid;
    public DeviceFlags flags;
    public int lock_timeout;

    public DeviceLockParams() {
    }

    public DeviceLockParams(XdrDecodingStream xdr) throws OncRpcException, IOException {
        xdrDecode(xdr);
    }

    public void xdrEncode(XdrEncodingStream xdr) throws OncRpcException, IOException {
        lid.xdrEncode(xdr);
        flags.xdrEncode(xdr);
        xdr.xdrEncodeInt(lock_timeout);
    }

    public void xdrDecode(XdrDecodingStream xdr) throws OncRpcException, IOException {
        lid = new DeviceLink(xdr);
        flags = new DeviceFlags(xdr);
        lock_timeout = xdr.xdrDecodeInt();
    }

}