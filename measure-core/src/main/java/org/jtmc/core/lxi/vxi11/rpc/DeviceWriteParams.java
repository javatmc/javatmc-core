package org.jtmc.core.lxi.vxi11.rpc;

import org.acplt.oncrpc.*;
import java.io.IOException;

public class DeviceWriteParams implements XdrAble {
    public DeviceLink lid;
    public int io_timeout;
    public int lock_timeout;
    public DeviceFlags flags;
    public byte [] data;

    public DeviceWriteParams() {

    }

    public DeviceWriteParams(DeviceLink lid, int io_timeout, int lock_timeout, DeviceFlags flags, byte[] data) {
        this.lid = lid;
        this.io_timeout = io_timeout;
        this.lock_timeout = lock_timeout;
        this.flags = flags;
        this.data = data;
    }

    public DeviceWriteParams(XdrDecodingStream xdr) throws OncRpcException, IOException {
        xdrDecode(xdr);
    }

    public void xdrEncode(XdrEncodingStream xdr) throws OncRpcException, IOException {
        lid.xdrEncode(xdr);
        xdr.xdrEncodeInt(io_timeout);
        xdr.xdrEncodeInt(lock_timeout);
        flags.xdrEncode(xdr);
        xdr.xdrEncodeDynamicOpaque(data);
    }

    public void xdrDecode(XdrDecodingStream xdr) throws OncRpcException, IOException {
        lid = new DeviceLink(xdr);
        io_timeout = xdr.xdrDecodeInt();
        lock_timeout = xdr.xdrDecodeInt();
        flags = new DeviceFlags(xdr);
        data = xdr.xdrDecodeDynamicOpaque();
    }

}