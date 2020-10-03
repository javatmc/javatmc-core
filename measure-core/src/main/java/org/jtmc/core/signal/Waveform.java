package org.jtmc.core.signal;

import org.jtmc.core.util.EnumParameters;

import org.jtmc.core.signal.Waveform.WaveformParameter;

public class Waveform extends EnumParameters<WaveformParameter> {

	public static enum WaveformParameter {
		PERIOD, FREQUENCY, AMPLITUDE, OFFSET, DUTY, SYMMETRY, PHASE, STDEV, MEAN, WIDTH, RISE, FALL, DELAY;
	}

	public static enum WaveformType {
		SINE, SQUARE, RAMP, PULSE, NOISE, DC;
	}

}