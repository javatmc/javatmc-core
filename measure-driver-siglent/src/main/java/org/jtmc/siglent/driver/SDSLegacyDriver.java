package org.jtmc.siglent.driver;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.jtmc.core.instrument.Oscilloscope;
import org.jtmc.core.instrument.common.Coupling;
import org.jtmc.core.scpi.ISCPISocket;
import org.jtmc.core.scpi.SCPICommand;
import org.jtmc.core.scpi.SCPISocketAdapter;
import org.jtmc.core.signal.analog.AnalogSignal;
import org.jtmc.core.util.Units;
import org.jtmc.core.visa.DeviceIdentifier;
import org.jtmc.core.visa.exception.InstrumentException;
import org.jtmc.siglent.info.SiglentOscilloscopeModel;

/**
 * Driver for the following Siglent Oscilloscopes:
 * 
 * <p>
 * SDS1000CFL
 * <p>
 * SDS1000A
 * <p>
 * SDS1000CML+/CNL+/DL+/E+/F+
 * <p>
 * SDS1000X
 * <p>
 * SDS1000X+
 * <p>
 * SDS1000X-E/C
 * <p>
 * SDS2000(X)
 */
public class SDSLegacyDriver extends SCPISocketAdapter implements Oscilloscope {

	private final static int HORIZONTAL_DIVISIONS = 14;

	private final static int VERTICAL_DIVISIONS = 8;

	private final static double[] TIME_DIV_VALUES = new double[] { Units.nano(1), Units.nano(2), Units.nano(5),
			Units.nano(10), Units.nano(20), Units.nano(50), Units.nano(100), Units.nano(200), Units.nano(500),
			Units.micro(1), Units.micro(2), Units.micro(5), Units.micro(10), Units.micro(20), Units.micro(50),
			Units.micro(100), Units.micro(200), Units.micro(500), Units.milli(1), Units.milli(2), Units.milli(5),
			Units.milli(10), Units.milli(20), Units.milli(50), Units.milli(100), Units.milli(200), Units.milli(500), 1f,
			2f, 5f, 10f, 20f, 50f, 100f };

	private AcquisitionSystemImpl acquisitionSystem;

	private List<AnalogInput> channels;

	public SDSLegacyDriver(ISCPISocket socket) throws IOException {
		this(socket, socket.getDeviceIdentifier());
	}

	public SDSLegacyDriver(ISCPISocket socket, DeviceIdentifier deviceIdentifier) throws IOException {
		this(socket, deviceIdentifier, SiglentOscilloscopeModel.create(deviceIdentifier.getModel()));
	}

	public SDSLegacyDriver(ISCPISocket socket, DeviceIdentifier deviceIdentifier, SiglentOscilloscopeModel info) throws IOException {
		super(socket, deviceIdentifier);

		// Sets the response to the Shortest format, meaning for queries it will only respond
		// with the values
		socket.send(new SCPICommand("CHDR", "OFF"));

		// Initiate channels and acquisition system
		this.acquisitionSystem = new AcquisitionSystemImpl(socket, HORIZONTAL_DIVISIONS, TIME_DIV_VALUES);
		this.channels = new LinkedList<AnalogInput>();
		
		for(int i = 0; i < info.getChannels(); i++) {
			this.channels.add(new ChannelImpl(socket, i + 1, VERTICAL_DIVISIONS));
		}
	}

	@Override
	public void setRunState(RunState state) throws InstrumentException {
		try {
			String mode = state.name();
			if (state == RunState.NORMAL) {
				mode = "NORM";
			}
			this.send(SCPICommand.builder().command("TRMD").with(mode).build());
		} catch (IOException e) {
			throw new InstrumentException(e);
		}
	}

	@Override
	public AcquisitionBaseSystem acquisition() {
		return this.acquisitionSystem;
	}

	@Override
	public Collection<AnalogInput> getAnalogInputs() {
		return this.channels;
	}

	private static class ChannelImpl implements AnalogInput {

		private ISCPISocket socket;

		private int channel;

		private int verticalDivisions;

		public ChannelImpl(ISCPISocket socket, int channel, int verticalDivisions) {
			this.socket = socket;
			this.channel = channel;
			this.verticalDivisions = verticalDivisions;
		}

		private static SCPICommand.Builder channelCommand(int channel, String cmd) {
			return SCPICommand.builder().command("C" + channel, cmd);
		}

		@Override
		public String getName() {
			return String.valueOf(this.channel);
		}

		@Override
		public void setEnabled(boolean enabled) throws InstrumentException {
			try {
				socket.send(channelCommand(channel, "TRA").with(enabled ? "ON":"OFF").build());
			} catch(IOException e) {
				throw new InstrumentException(e);
			}
		}

		@Override
		public void setRange(double range) throws InstrumentException {
			try {
				double gain = range / VERTICAL_DIVISIONS;
				socket.send(channelCommand(channel, "VDIV").with(Units.auto(gain, 1, "V")).build());
			} catch(IOException e) {
				throw new InstrumentException(e);
			}
		}

		@Override
		public void setOffset(double offset) throws InstrumentException {
			try {
				String ofst = Units.auto(offset, 3, "V").toUpperCase();
				socket.send(channelCommand(channel, "OFST").with(ofst).build());
			} catch(IOException e) {
				throw new InstrumentException(e);
			}
		}

		@Override
		public void setProbeAttenuation(double attenuation) throws InstrumentException {
			try {
				String attn = attenuation < 1 ? String.format("%.01f", attenuation) : String.format("%.00f", attenuation);
				socket.send(channelCommand(channel, "ATTN").with(attn).build());
			} catch(IOException e) {
				throw new InstrumentException(e);
			}
		}

		@Override
		public void setProbeSense(boolean enable) throws InstrumentException {
			//TODO: 2000X series has probe detection ring
			throw new InstrumentException("Probe sense is not supported");
		}

		@Override
		public void setImpedance(double impedance) throws InstrumentException {
			//2 channel variants have 50 Ohm mode
			throw new InstrumentException("No implemented");
		}

		@Override
		public void setBandwidthLimit(double bandwidthLimit) throws InstrumentException {
			double MHZ20 = Units.mega(20);
			if(Math.abs(bandwidthLimit - MHZ20) <= 0.01) {
				//Set bandwidth 
			}
			throw new InstrumentException("");
		}

		@Override
		public void setCoupling(Coupling coupling) {
			// TODO Auto-generated method stub

		}

		@Override
		public Optional<AnalogSignal> getAnalogInputSignal(int channel) throws InstrumentException {
			// TODO Auto-generated method stub
			return null;
		}
		
	}

	private static class AcquisitionSystemImpl implements AcquisitionBaseSystem {

		private ISCPISocket socket;

		private int horizontalDivisions;

		private final double[] timeDivisionValues;

		public AcquisitionSystemImpl(ISCPISocket socket, int horizontalDivisions, double[] timeDivisionValues) {
			this.socket = socket;
			this.horizontalDivisions = horizontalDivisions;
			this.timeDivisionValues = timeDivisionValues;
		}

		@Override
		public void setTimespan(double span) throws InstrumentException {
			try {
				double secPerDiv = span / horizontalDivisions;
				double targetDiv = Arrays.stream(TIME_DIV_VALUES).filter(tdiv -> Math.abs(secPerDiv - tdiv) <= Units.PICO || tdiv >= secPerDiv).findFirst().getAsDouble();
				String tdiv = Units.auto(targetDiv, 0, "s");
	
				socket.send(SCPICommand.builder().command("TDIV").with(tdiv).build());
			} catch(IOException e) {
				throw new InstrumentException(e);
			}
		}

		@Override
		public void setSampleCount(long count) throws InstrumentException {
			// TODO Auto-generated method stub

		}

		@Override
		public long getSampleCount() throws InstrumentException {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public double getSampleRate() throws InstrumentException {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public void setTimeOffset(double offset) throws InstrumentException {
			try {
				String triggerDelay = Units.auto(-offset, 2, "s");
	
				socket.send(SCPICommand.builder().command("TRDL").with(triggerDelay).build());
			} catch(IOException e) {
				throw new InstrumentException(e);
			}
		}

		@Override
		public void setMode(AcquisitionMode mode) throws InstrumentException {
			// TODO Auto-generated method stub

		}

		@Override
		public AcquisitionState getState() throws InstrumentException {
			// TODO Auto-generated method stub
			return null;
		}

	}
	
}