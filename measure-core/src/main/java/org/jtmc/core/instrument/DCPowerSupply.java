package org.jtmc.core.instrument;

import java.util.Collection;

/**
 * A DC power supply is a device capable of outputting a set voltage or a set current
 * 
 * @author Balazs Eszes
 */
public interface DCPowerSupply {

	Collection<PowerOutput> getPowerOutputs();

	default PowerOutput getPowerOutput(int index) {
		return this.getPowerOutputs().stream().skip(index).findFirst().get();
	}

	default PowerOutput getPowerOutput(String name) {
		return this.getPowerOutputs().stream().filter(channel -> channel.getName().equals(name)).findFirst().get();
	}

	public static interface PowerOutput {
		String getName();

		void setEnabled(boolean enabled);

		void setMaximumVoltage(double voltage);

		double getMaximumVoltage();

		void setMaximumCurrent(double current);

		double getMaximumCurrent();

		double getVoltage();

		double getCurrent();
	}
}