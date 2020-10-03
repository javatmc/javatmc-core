package org.jtmc.core.visa.mock;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.nio.ByteBuffer;

import org.jtmc.core.device.ISocket;

/**
 * VirtualSocket
 */
public class MockSocket implements ISocket {

    private boolean connected;

    private String className;

    private String instrumentName;

    public MockSocket(String className) {
        this(className, null);
    }

    public MockSocket(String className, String instrumentName) {
        this.className = className;
		this.instrumentName = instrumentName;
		this.connected = true;
    }

    @Override
    public void close() {
        this.connected = false;
    }

    @Override
    public boolean isConnected() {
        return this.connected;
    }

    @Override
    public String getResourceString() {
        return "MOCK::" + className + (instrumentName != null ? "::" + instrumentName: "") + "::INSTR";
    }

	public String getClassName() {
		return className;
	}

	public String getInstrumentName() {
		return instrumentName;
	}

    @Override
    public void send(ByteBuffer message) throws IOException {
        if(!this.connected) {
            throw new IOException("MockSocket not connected");
        }
    }

    @Override
    public ByteBuffer receive(int count, long timeout) throws IOException, SocketTimeoutException {
        if(!this.connected) {
            throw new IOException("MockSocket not connected");
        }
        return ByteBuffer.allocate(0);
    }

    @Override
    public ByteBuffer receive(char delimiter, long timeout) throws IOException, SocketTimeoutException {
        if(!this.connected) {
            throw new IOException("MockSocket not connected");
        }
        return ByteBuffer.allocate(0);
    }
    
}