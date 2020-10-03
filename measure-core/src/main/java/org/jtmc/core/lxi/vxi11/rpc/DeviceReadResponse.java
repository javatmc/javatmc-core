package org.jtmc.core.lxi.vxi11.rpc;

import org.acplt.oncrpc.*;
import java.io.IOException;

public class DeviceReadResponse implements XdrAble {

    public final static int EOI_SET_BIT = 2;

    public final static int TERM_CHAR_SEEN_BIT = 1;

    public final static int REQUESTED_COUNT_REACHED_BIT = 0;

    public DeviceErrorCode error;
    public int reason;
    public byte [] data;

    public DeviceReadResponse() {
    }

    public DeviceReadResponse(XdrDecodingStream xdr) throws OncRpcException, IOException {
        xdrDecode(xdr);
    }

    public void xdrEncode(XdrEncodingStream xdr) throws OncRpcException, IOException {
        error.xdrEncode(xdr);
        xdr.xdrEncodeInt(reason);
        xdr.xdrEncodeDynamicOpaque(data);
    }

    public void xdrDecode(XdrDecodingStream xdr) throws OncRpcException, IOException {
        error = new DeviceErrorCode(xdr);
        reason = xdr.xdrDecodeInt();
        data = xdr.xdrDecodeDynamicOpaque();
    }

    public int getError() {
        return error.value;
    }

    public boolean isEOISet() {
        return (reason & (1 << EOI_SET_BIT)) != 0;
    }

    public boolean isTermCharSeen() {
        return (reason & (1 << TERM_CHAR_SEEN_BIT)) != 0;
    }

    public boolean isRequestCountReached() {
        return (reason & (1 << REQUESTED_COUNT_REACHED_BIT)) != 0;
    }

}