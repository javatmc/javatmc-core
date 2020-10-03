package org.jtmc.core.signal.digital.sampler;

import org.jtmc.core.signal.digital.BinarySignal;
import org.jtmc.core.signal.sampler.Sampler;
import org.jtmc.core.signal.Signal;
import org.jtmc.core.signal.Signal.DataPoint;

/**
 * Threshold
 */
public class Threshold implements Sampler<Float, BinarySignal> {

	private float threshold;

	public Threshold(float threshold) {
		this.threshold = threshold;
	}

	@Override
	public BinarySignal sample(Signal<Float> signal) {
		BinarySignal output = new BinarySignal(signal.getId());
		boolean state = !(signal.first() > threshold);
		for(DataPoint<Float> point : signal.getData()) {
			boolean current = point.value > threshold;
			if(current != state) {
				output.add(point.time, current);
				state = current;
			}
		}
		return null;
	}
	
}