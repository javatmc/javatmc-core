package org.jtmc.cli.visa;

import picocli.CommandLine.ArgGroup;
import picocli.CommandLine.Option;

public class VISASocketOption {

	@ArgGroup(exclusive = false)
	RawSocketOption rawSocket;
	
	@ArgGroup(exclusive = false)
	SerialSocketOption serialSocket;

	@Option(names = {"--resource"})
	String resourceString;
	
}