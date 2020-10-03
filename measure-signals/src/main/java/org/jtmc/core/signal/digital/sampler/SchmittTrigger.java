package org.jtmc.core.signal.digital.sampler;

import org.jtmc.core.signal.digital.BinarySignal;
import org.jtmc.core.signal.sampler.Sampler;
import org.jtmc.core.signal.Signal;
import org.jtmc.core.signal.Signal.DataPoint;

/**
 * Threshold
 */
public class SchmittTrigger implements Sampler<Float, BinarySignal> {

	private float upper;

	private float lower;

	public SchmittTrigger(float upper, float lower) {
		this.upper = upper;
		this.lower = lower;
	}

	@Override
	public BinarySignal sample(Signal<Float> signal) {
		BinarySignal output = new BinarySignal(signal.getId());
		int state = -state(signal.first(), upper, lower);
		for(DataPoint<Float> point : signal.getData()) {
			int current = state(point.value, upper, lower);
			if(current != state && current != 0) {
				output.add(point.time, bin(current));
				state = current;
			}
		}
		return null;
	}

	private int state(float value, float upper, float lower) {
		return (value > upper ? 1 : (value < lower ? -1 : 0));
	}

	private boolean bin(int state) {
		return state > 0 ? true : false;
	}
	
}