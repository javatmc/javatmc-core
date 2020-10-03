package org.jtmc.core.lxi.vxi11;

import java.io.IOException;

import org.jtmc.core.lxi.vxi11.VXI11.ErrorCode;

/**
 * VXI11Exception is a convenience exception for indicating an exception while using the VXI-11 Protocol
 */
public class VXI11Exception extends IOException {

    private static final long serialVersionUID = 9011406167109584636L;

    /**
     * Constructs a VXI11Exception based on the error code
     * @param code VXI-11 Error code
     */
    public VXI11Exception(int code) {
        super("VXI11 Error: " + ErrorCode.getErrorString(code));
    }

    public VXI11Exception(String message) {
        super(message);
    }

    public VXI11Exception(Throwable cause) {
        super(cause);
    }

    public VXI11Exception(String message, Throwable cause) {
        super(message, cause);
    }

}