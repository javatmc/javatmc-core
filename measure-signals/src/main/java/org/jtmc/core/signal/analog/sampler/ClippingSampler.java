package org.jtmc.core.signal.analog.sampler;

import org.jtmc.core.signal.analog.AnalogSampler;
import org.jtmc.core.signal.analog.AnalogSignal;

import java.util.function.Function;

import org.jtmc.core.signal.Signal;

/**
 * ClippingSampler takes an analog signal and clips voltages to minimum and
 * maximum values.
 */
public class ClippingSampler implements AnalogSampler, Function<Float, Float> {

	private float min;

	private float max;

	/**
	 * Creates a new clipping sampler
	 * 
	 * @param min Minimum voltage
	 * @param max Maximum voltage
	 */
	public ClippingSampler(float min, float max) {
		this.min = min;
		this.max = max;
	}

	@Override
	public Float apply(Float value) {
		return Math.max(Math.min(value, max), min);
	}

	@Override
	public AnalogSignal sample(Signal<Float> signal) {
		return new AnalogSignal(signal, this);
	}
	
}