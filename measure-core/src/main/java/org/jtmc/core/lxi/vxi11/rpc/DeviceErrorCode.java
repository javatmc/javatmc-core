package org.jtmc.core.lxi.vxi11.rpc;

import org.acplt.oncrpc.*;
import java.io.IOException;

public class DeviceErrorCode implements XdrAble {

    public int value;

    public DeviceErrorCode() {
    }

    public DeviceErrorCode(int value) {
        this.value = value;
    }

    public DeviceErrorCode(XdrDecodingStream xdr) throws OncRpcException, IOException {
        xdrDecode(xdr);
    }

    public void xdrEncode(XdrEncodingStream xdr) throws OncRpcException, IOException {
        xdr.xdrEncodeInt(value);
    }

    public void xdrDecode(XdrDecodingStream xdr) throws OncRpcException, IOException {
        value = xdr.xdrDecodeInt();
    }

}