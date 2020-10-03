package org.jtmc.core.signal.analog.sampler;

import org.jtmc.core.signal.analog.AnalogSampler;
import org.jtmc.core.signal.analog.AnalogSignal;

import java.util.function.Function;

import org.jtmc.core.signal.Signal;

/**
 * This sampler inverts any analog signal alongside the voltage axis
 */
public class AnalogInverter implements AnalogSampler, Function<Float, Float> {

	private final float offset;

	public AnalogInverter() {
		this(0.0f);
	}

	public AnalogInverter(float offset) {
		this.offset = offset;
	}

	@Override
	public Float apply(Float value) {
		return -(value - offset);
	}

	@Override
	public AnalogSignal sample(Signal<Float> signal) {
		return new AnalogSignal(signal, this);
	}

	/**
	 * @return the offset
	 */
	public float getOffset() {
		return offset;
	}

}