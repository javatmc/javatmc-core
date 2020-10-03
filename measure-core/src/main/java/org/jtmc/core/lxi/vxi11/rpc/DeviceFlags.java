package org.jtmc.core.lxi.vxi11.rpc;

import org.acplt.oncrpc.*;
import java.io.IOException;

public class DeviceFlags implements XdrAble {

    public final static int TERMCHRSET_BIT = 7;

    public final static int END_BIT = 3;

    public final static int WAITLOCK_BIT = 0;

    public int value;

    public DeviceFlags() {
    }

    public DeviceFlags(int value) {
        this.value = value;
    }

    public DeviceFlags(boolean termchar, boolean end, boolean waitlock) {
        this.value = termination(termchar) | end(end) | waitlock(waitlock);
    }

    public int termination(boolean enabled) {
        return enabled ? (1 << TERMCHRSET_BIT): 0;
    }

    public int end(boolean enabled) {
        return enabled ? (1 << END_BIT): 0;
    }

    public int waitlock(boolean enabled) {
        return enabled ? (1 << WAITLOCK_BIT): 0;
    }

    public DeviceFlags(XdrDecodingStream xdr) throws OncRpcException, IOException {
        xdrDecode(xdr);
    }

    public void xdrEncode(XdrEncodingStream xdr)
           throws OncRpcException, IOException {
        xdr.xdrEncodeInt(value);
    }

    public void xdrDecode(XdrDecodingStream xdr)
           throws OncRpcException, IOException {
        value = xdr.xdrDecodeInt();
    }

}