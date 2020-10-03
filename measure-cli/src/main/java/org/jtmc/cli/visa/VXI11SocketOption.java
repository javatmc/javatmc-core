package org.jtmc.cli.visa;

import java.io.IOException;
import java.net.InetAddress;

import org.jtmc.core.lxi.vxi11.VXI11Socket;

import picocli.CommandLine.Option;

public class VXI11SocketOption {
	@Option(names = { "--vxi11-host" }, required = true)
	InetAddress host;

	@Option(names = { "--vxi11-port" }, defaultValue = "0")
	int port;

	@Option(names = { "--vxi11-name" }, defaultValue = "inst0")
	String name;

	@Option(names = {"--vxi11-lock"}, defaultValue = "false")
	boolean lock;

	@Option(names = {"--vxi1-lock-timeout"}, defaultValue = "0")
	int lockTimeout;

	@Option(names = {"--vxi1-io-timeout"}, defaultValue = "0")
	int ioTimeout;

	@Option(names = {"--vxi1-write-block-size"}, defaultValue = "8128")
	int writeBlockSize;


	public VXI11Socket getSocket() throws IOException {
		return new VXI11Socket(host, name, port, lock, lockTimeout, ioTimeout, writeBlockSize);
	}
}