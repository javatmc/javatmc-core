package org.jtmc.core.instrument;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class TestDCPowerSupply implements DCPowerSupply {

	private List<PowerOutput> outputs;

	public TestDCPowerSupply() {
		this.outputs = new LinkedList<>();
		this.outputs.add(new DummyOutputImpl(0));
		this.outputs.add(new DummyOutputImpl(1));
	}

	@Override
	public Collection<PowerOutput> getPowerOutputs() {
		return this.outputs;
	}

	public static class DummyOutputImpl implements DCPowerSupply.PowerOutput {

		private int index;

		public DummyOutputImpl(int index) {
			this.index = index;
		}

		@Override
		public String getName() {
			return String.valueOf(index + 1);
		}

		@Override
		public void setEnabled(boolean enabled) {
			
		}

		@Override
		public void setMaximumVoltage(double voltage) {
			
		}

		@Override
		public double getMaximumVoltage() {
			return 0;
		}

		@Override
		public void setMaximumCurrent(double current) {

		}

		@Override
		public double getMaximumCurrent() {
			return 0;
		}

		@Override
		public double getVoltage() {
			return 0;
		}

		@Override
		public double getCurrent() {
			return 0;
		}

	}
	
}
