package org.jtmc.core.instrument;

import org.jtmc.core.visa.exception.InstrumentException;

/**
 * TimeDomainAnalyzer is any device capable of time correlated measurements
 * 
 * @author Balazs Eszes
 */
public interface TimeDomainAnalyzer {

	//TODO: move to trigger subsystem
	void setRunState(RunState state) throws InstrumentException;

	public static enum RunState {
		AUTO, NORMAL, SINGLE, STOP;
	}

	AcquisitionBaseSystem acquisition();

	public static interface AcquisitionBaseSystem {

		void setTimespan(double span) throws InstrumentException;

		void setSampleCount(long count) throws InstrumentException;

		long getSampleCount() throws InstrumentException;

		double getSampleRate() throws InstrumentException;

		void setTimeOffset(double offset) throws InstrumentException;
		
		void setMode(AcquisitionMode mode) throws InstrumentException;

		public static enum AcquisitionMode {
			NORMAL, HIGH_RESOLUTION, AVERAGE, PEAK, ENVELOPE
		}

		AcquisitionState getState() throws InstrumentException;

		public static enum AcquisitionState {
			COMPLETE, INPROGRESS, UNKNOWN
		}
	}
}