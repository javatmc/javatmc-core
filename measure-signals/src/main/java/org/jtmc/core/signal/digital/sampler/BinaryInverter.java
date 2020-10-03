package org.jtmc.core.signal.digital.sampler;

import org.jtmc.core.signal.digital.BinarySampler;
import org.jtmc.core.signal.digital.BinarySignal;
import org.jtmc.core.signal.Signal;

/**
 * BinaryInverter takes a binary signal and inverts the signal logically
 */
public class BinaryInverter implements BinarySampler {

	@Override
	public BinarySignal sample(Signal<Boolean> signal) {
		BinarySignal output = new BinarySignal("~" + signal.getId());
		signal.stream().forEach(point -> {
			output.add(point.time, !point.value);
		});
		return output;
	}
	
}