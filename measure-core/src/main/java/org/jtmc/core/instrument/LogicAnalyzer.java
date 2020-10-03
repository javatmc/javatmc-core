package org.jtmc.core.instrument;

import java.util.Collection;

/**
 * A logic analyzer is a device capable of making binary signal measurements
 * over time
 * 
 * @author Balazs Eszes
 */
public interface LogicAnalyzer extends TimeDomainAnalyzer {

	public final static double TTL = 1.5f;

	public final static double CMOS = 1.65f;

	public final static double LVCMOS3V3 = 1.65f;

	public final static double LVCMOS2V5 = 1.25f;

	Collection<DigitalInput> getDigitalInputs();

	default DigitalInput getDigitalInput(int index) {
		return this.getDigitalInputs().stream().skip(index).findFirst().get();
	}

	default DigitalInput getDigitalInput(String name) {
		return this.getDigitalInputs().stream().filter(channel -> channel.getName().equals(name)).findFirst().get();
	}

	public static interface DigitalInput {
		String getName();

		void setEnabled(boolean enabled);

		void setThreshold(double threshold);
	}
}