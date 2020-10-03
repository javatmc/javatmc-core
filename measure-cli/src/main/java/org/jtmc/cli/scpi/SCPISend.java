package org.jtmc.cli.scpi;

import java.io.File;
import java.util.List;
import java.util.concurrent.Callable;

import org.jtmc.cli.visa.VISASocketOption;

import picocli.CommandLine.ArgGroup;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "scpi-send")
public class SCPISend implements Callable<Integer> {
	
	@ArgGroup(exclusive = true)
	Input input;

	@ArgGroup(exclusive = true)
	VISASocketOption socketOption;
	
	class Input {
		@Option(names = {"--commands", "-c"}, required = false)
		List<String> commands;

		@Option(names = {"--script"}, required = false)
		File script;

		@Option(names = {"--interactive", "-i"}, required = true)
		boolean interactive;
	}

	@Override
	public Integer call() throws Exception {
		return 1;
	}
}