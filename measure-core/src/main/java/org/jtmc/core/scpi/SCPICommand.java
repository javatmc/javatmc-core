package org.jtmc.core.scpi;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * SCPICommand is a message understood by SCPI devices
 * 
 * <p>
 * In general the command format:
 * <p>
 * 	ROOT:SUB:CMD PARAM1,PARAM2,...
 * 
 * 
 */
public final class SCPICommand {

	private String raw;

	private String command;

	private List<String> parameters;

	/**
	 * Constructs a SCPICommand using the command and a list of parameters
	 * @param command Command
	 * @param params List of parameters
	 */
	public SCPICommand(String command, List<String> params) {
		if(command.contains(" ") || command.contains(",")) {
			throw new IllegalArgumentException("Invalid command");
		}
		this.command = command;
		this.parameters = Collections.unmodifiableList(params);
		this.raw = this.toString();
	}

	/**
	 * Constructs a SCPICommand using the command and an array of parameters
	 * 
	 * @param command Command
	 * @param params Parameters
	 */
	public SCPICommand(String command, String... params) {
		this(command, Arrays.asList(params));
	}

	/**
	 * Constructs a SCPICommand from it's raw string representation
	 * @param raw Raw string (CMD:SUBCMD PARAM1,PARAM2)
	 */
	public SCPICommand(String raw) {
		this.raw = raw;

		int firstSpace = this.raw.indexOf(" ");
		if(firstSpace == -1 || firstSpace == raw.length()) {
			this.command = this.raw;
			this.parameters = Collections.unmodifiableList(Collections.emptyList());
		}
		else {
			this.command = raw.substring(0, firstSpace);
			String[] params = raw.substring(firstSpace+1, raw.length()).split(",");
			this.parameters = Collections.unmodifiableList(Arrays.asList(params));
		}
	}

	/**
	 * @return the command
	 */
	public String getCommand() {
		return command;
	}

	/**
	 * @return the raw
	 */
	public String getRaw() {
		return raw;
	}

	/**
	 * @return the parameters
	 */
	public List<String> getParameters() {
		return parameters;
	}

	/**
	 * Returns the SCPI command in it's string format, equivalent to {@see getRaw()}
	 * 
	 * @return SCPI command string
	 */
	@Override
	public String toString() {
		if(raw != null) {
			return raw;
		}
		StringBuilder builder = new StringBuilder();
		builder.append(this.getCommand());
		if(this.getParameters().size() != 0) {
			builder.append(" ");
		}
		Iterator<String> it = this.getParameters().iterator();
		while(it.hasNext()) {
			String param = it.next();
			builder.append(param);
			if(it.hasNext()) {
				builder.append(",");
			}
		}
		return builder.toString();
	}

	/**
	 * Returns the given parameter's value
	 * 
	 * E.g: for {@code CMD PARAM1,VALUE1}, {@code getParameter("PARAM1")} would return VALUE1
	 * 
	 * @param name Parameter's name
	 * @return Parameter's value, empty if parameter doesn't exist or doesn't have a value associated to it
	 */
	public Optional<String> getParameter(String name) {
		Iterator<String> it = parameters.iterator();
		while(it.hasNext()) {
			if(it.next().equals(name)) {
				if(it.hasNext()) {
					return Optional.of(it.next());
				}
				else {
					return Optional.empty();
				}
			}
		}
		return Optional.empty();
	}

	/**
	 * Returns the given parameter's value as float
	 * 
	 * @param name Parameter's name
	 * @return Parameter's value
	 * 
	 * @throws NumberFormatException if the value can't be parsed as float
	 */
	public Optional<Float> getFloat(String name) {
		return this.getParameter(name)
					.map(value -> value != null ? Float.parseFloat(value) : null);
	}

	/**
	 * Returns the given parameter's value as int
	 * 
	 * @param name Parameter's name
	 * @return Parameter's value
	 * 
	 * @throws NumberFormatException if the value can't be parsed as integer
	 */
	public Optional<Integer> getInt(String name) {
		return this.getParameter(name)
					.map(value -> value != null ? Integer.parseInt(value) : null);
	}

	/**
	 * Returns whether or not the given parameter exists
	 * 
	 * @param param Parameter's name
	 * @return {@code true} if there's such parameter
	 */
	public boolean hasParameter(String param) {
		return this.parameters.contains(param);
	}

	/**
	 * Returns a mutable command builder
	 * 
	 * @return Mutable builder instance
	 */
	public static Builder builder() {
		return new Builder();
	}

	/**
	 * A mutable class for building SCPI commands
	 * 
	 * <p>
	 * Method calls can be chained for compact usage
	 * 
	 * <p>
	 * Create command
	 * {@code SCPICommand.builder().command("SET50").build()}
	 * 
	 * 
	 * <p>
	 * Create query
	 * {@code SCPICommand.builder().query("C1:BSWV").build()}
	 * 
	 * <p>
	 * Create command with parameters
	 * {@code SCPICommand.builder().command("C1:BSWV").with("TYPE", "SINE").with("FREQ", 10000).build()}
	 */
	public final static class Builder {

		private String command;

		private List<String> parameters = new LinkedList<>();

		/**
		 * Sets the command
		 * 
		 * @param command
		 * @return
		 */
		public Builder command(String command) {
			this.command = command;
			return this;
		}

		/**
		 * Sets the command to 
		 * 
		 * @param cmds
		 * @return
		 */
		public Builder command(String... cmds) {
			this.command = String.join(":", cmds);
			return this;
		}

		/**
		 * Sets the command to a query
		 * 
		 * @param command 
		 * @return {@code this} Builder
		 */
		public Builder query(String command) {
			this.command = command + "?";
			return this;
		}

		public Builder with(String param, String value) {
			this.parameters.add(param);
			this.parameters.add(value);
			return this;
		}

		public Builder with(String param, int value) {
			return this.with(param, String.valueOf(value));
		}

		public Builder with(String param, float value) {
			return this.with(param, String.valueOf(value));
		}

		public Builder with(String param) {
			this.parameters.add(param);
			return this;
		}

		public SCPICommand build() {
			return new SCPICommand(command, parameters);
		}
	}
	
}