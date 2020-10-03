package org.jtmc.core.lxi.vxi11.rpc;

import org.acplt.oncrpc.*;
import java.io.IOException;

public class DeviceRemoteFunction implements XdrAble {
    public int hostAddr;
    public int hostPort;
    public int progNum;
    public int progVers;
    public int progFamily;

    public DeviceRemoteFunction() {
    }

    public DeviceRemoteFunction(XdrDecodingStream xdr) throws OncRpcException, IOException {
        xdrDecode(xdr);
    }

    public void xdrEncode(XdrEncodingStream xdr) throws OncRpcException, IOException {
        xdr.xdrEncodeInt(hostAddr);
        xdr.xdrEncodeInt(hostPort);
        xdr.xdrEncodeInt(progNum);
        xdr.xdrEncodeInt(progVers);
        xdr.xdrEncodeInt(progFamily);
    }

    public void xdrDecode(XdrDecodingStream xdr) throws OncRpcException, IOException {
        hostAddr = xdr.xdrDecodeInt();
        hostPort = xdr.xdrDecodeInt();
        progNum = xdr.xdrDecodeInt();
        progVers = xdr.xdrDecodeInt();
        progFamily = xdr.xdrDecodeInt();
    }

}