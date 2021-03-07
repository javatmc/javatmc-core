package org.jtmc.cli;

import org.jtmc.cli.lxi.LXIDiscover;
import org.jtmc.cli.scpi.SCPISend;
import picocli.CommandLine;
import picocli.CommandLine.Command;

/**
 * JMeasureCLI provides command line access to instruments.
 */
@Command(
    subcommands = {
      LXIDiscover.class,
      SCPISend.class
    }
)
public class JMeasureCLI {

  public static void main(String[] args) {
    int exitCode = new CommandLine(new JMeasureCLI()).execute(args);
    System.exit(exitCode);
  }
}
