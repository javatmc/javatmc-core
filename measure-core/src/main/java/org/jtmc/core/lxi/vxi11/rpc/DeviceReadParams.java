package org.jtmc.core.lxi.vxi11.rpc;

import org.acplt.oncrpc.*;
import java.io.IOException;

public class DeviceReadParams implements XdrAble {
    public DeviceLink lid;
    public int requestSize;
    public int io_timeout;
    public int lock_timeout;
    public DeviceFlags flags;
    public byte termChar;

    public DeviceReadParams() {
    }

    public DeviceReadParams(DeviceLink lid, int requestSize, int io_timeout, int lock_timeout, DeviceFlags flags, byte termChar) {
        this.lid = lid;
        this.requestSize = requestSize;
        this.io_timeout = io_timeout;
        this.lock_timeout = lock_timeout;
        this.flags = flags;
        this.termChar = termChar;
    }

    public DeviceReadParams(XdrDecodingStream xdr) throws OncRpcException, IOException {
        xdrDecode(xdr);
    }

    public void xdrEncode(XdrEncodingStream xdr) throws OncRpcException, IOException {
        lid.xdrEncode(xdr);
        xdr.xdrEncodeInt(requestSize);
        xdr.xdrEncodeInt(io_timeout);
        xdr.xdrEncodeInt(lock_timeout);
        flags.xdrEncode(xdr);
        xdr.xdrEncodeByte(termChar);
    }

    public void xdrDecode(XdrDecodingStream xdr) throws OncRpcException, IOException {
        lid = new DeviceLink(xdr);
        requestSize = xdr.xdrDecodeInt();
        io_timeout = xdr.xdrDecodeInt();
        lock_timeout = xdr.xdrDecodeInt();
        flags = new DeviceFlags(xdr);
        termChar = xdr.xdrDecodeByte();
    }

}