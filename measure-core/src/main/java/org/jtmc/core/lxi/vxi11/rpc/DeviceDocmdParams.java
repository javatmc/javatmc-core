package org.jtmc.core.lxi.vxi11.rpc;

import org.acplt.oncrpc.*;
import java.io.IOException;

public class DeviceDocmdParams implements XdrAble {
    public DeviceLink lid;
    public DeviceFlags flags;
    public int io_timeout;
    public int lock_timeout;
    public int cmd;
    public boolean network_order;
    public int datasize;
    public byte [] data_in;

    public DeviceDocmdParams() {
    }

    public DeviceDocmdParams(XdrDecodingStream xdr) throws OncRpcException, IOException {
        xdrDecode(xdr);
    }

    public void xdrEncode(XdrEncodingStream xdr) throws OncRpcException, IOException {
        lid.xdrEncode(xdr);
        flags.xdrEncode(xdr);
        xdr.xdrEncodeInt(io_timeout);
        xdr.xdrEncodeInt(lock_timeout);
        xdr.xdrEncodeInt(cmd);
        xdr.xdrEncodeBoolean(network_order);
        xdr.xdrEncodeInt(datasize);
        xdr.xdrEncodeDynamicOpaque(data_in);
    }

    public void xdrDecode(XdrDecodingStream xdr) throws OncRpcException, IOException {
        lid = new DeviceLink(xdr);
        flags = new DeviceFlags(xdr);
        io_timeout = xdr.xdrDecodeInt();
        lock_timeout = xdr.xdrDecodeInt();
        cmd = xdr.xdrDecodeInt();
        network_order = xdr.xdrDecodeBoolean();
        datasize = xdr.xdrDecodeInt();
        data_in = xdr.xdrDecodeDynamicOpaque();
    }

}