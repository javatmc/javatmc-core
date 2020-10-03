package org.jtmc.core.instrument;

import java.util.Collection;

import org.jtmc.core.visa.exception.InstrumentException;

/**
 * A waveform generator is a device capable of outputting a changing voltage signal
 * 
 * @author Balazs Eszes
 */
public interface FunctionGenerator {

	Collection<AnalogOutput> getAnalogOutputs();

	public static interface AnalogOutput {
		String getName();

		void setEnabled(boolean enabled) throws InstrumentException;

		void setOperationMode(OperationMode operationMode) throws InstrumentException;

		void setImpedance(double impedance) throws InstrumentException;

		// Commons

		void setAmplitude(double amplitude) throws InstrumentException;

		void setOffset(double offset) throws InstrumentException;

		void setFrequency(double frequency) throws InstrumentException;

		void setPhase(double phase) throws InstrumentException;

		// Builtin

		void setWaveformType(WaveformType waveformType) throws InstrumentException;

		default void setWaveform(WaveformType waveformType, double amplitude, double offset, double frequency, double phase) throws InstrumentException {
			
		}

		// Arbitrary

		//void setWaveform();

		//void setSampleRate();

	}

	public static enum WaveformType {
		SINE, SQUARE, TRIANGLE, RAMPUP, RAMPDOWN, DC, NOISE
	}

	public static enum OperationMode {
		CONTINUOUS, BURST
	}
}