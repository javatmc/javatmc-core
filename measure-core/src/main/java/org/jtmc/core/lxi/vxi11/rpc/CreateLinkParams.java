package org.jtmc.core.lxi.vxi11.rpc;

import org.acplt.oncrpc.*;
import java.io.IOException;

public class CreateLinkParams implements XdrAble {
    public int clientId;
    public boolean lockDevice;
    public int lock_timeout;
    public String device;

    public CreateLinkParams() {

    }

    public CreateLinkParams(int clientId, boolean lockDevice, int lock_timeout, String device) {
        this.clientId = clientId;
        this.lockDevice = lockDevice;
        this.lock_timeout = lock_timeout;
        this.device = device;
    }

    public CreateLinkParams(XdrDecodingStream xdr) throws OncRpcException, IOException {
        xdrDecode(xdr);
    }

    public void xdrEncode(XdrEncodingStream xdr) throws OncRpcException, IOException {
        xdr.xdrEncodeInt(clientId);
        xdr.xdrEncodeBoolean(lockDevice);
        xdr.xdrEncodeInt(lock_timeout);
        xdr.xdrEncodeString(device);
    }

    public void xdrDecode(XdrDecodingStream xdr) throws OncRpcException, IOException {
        clientId = xdr.xdrDecodeInt();
        lockDevice = xdr.xdrDecodeBoolean();
        lock_timeout = xdr.xdrDecodeInt();
        device = xdr.xdrDecodeString();
    }

}