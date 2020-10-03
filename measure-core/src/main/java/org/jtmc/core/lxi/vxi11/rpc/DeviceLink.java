package org.jtmc.core.lxi.vxi11.rpc;

import org.acplt.oncrpc.*;
import java.io.IOException;

public class DeviceLink implements XdrAble {

    public int value;

    public DeviceLink() {
    }

    public DeviceLink(int value) {
        this.value = value;
    }

    public DeviceLink(XdrDecodingStream xdr) throws OncRpcException, IOException {
        xdrDecode(xdr);
    }

    public void xdrEncode(XdrEncodingStream xdr) throws OncRpcException, IOException {
        xdr.xdrEncodeInt(value);
    }

    public void xdrDecode(XdrDecodingStream xdr) throws OncRpcException, IOException {
        value = xdr.xdrDecodeInt();
    }

}