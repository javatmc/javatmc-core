package org.jtmc.cli.visa;

import java.io.IOException;

import org.jtmc.core.lxi.raw.RawSocket;

import picocli.CommandLine.Option;

public class RawSocketOption {

	@Option(names = { "--raw-host" }, required = true)
	String host;

	@Option(names = { "--raw-port" }, required = true)
	int port;

	@Option(names = { "--raw-board" }, defaultValue = "0")
	int board;

	public RawSocket getSocket() throws IOException {
		return new RawSocket(host, port, board);
	}

}