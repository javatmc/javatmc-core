package org.jtmc.siglent.driver;

import java.io.IOException;
import java.util.Collection;
import org.jtmc.core.instrument.FunctionGenerator;
import org.jtmc.core.instrument.common.Impedance;
import org.jtmc.core.scpi.ISCPISocket;
import org.jtmc.core.scpi.SCPICommand;
import org.jtmc.core.scpi.SCPISocketAdapter;
import org.jtmc.core.visa.DeviceIdentifier;
import org.jtmc.core.visa.exception.InstrumentException;
import org.jtmc.siglent.info.SiglentFunctionGeneratorModel;

/**
 * Siglent SDG Series function generator driver.
 */
public class SDGDriver extends SCPISocketAdapter implements FunctionGenerator {

  public SDGDriver(
        ISCPISocket adapter,
        DeviceIdentifier deviceIdentifier,
        SiglentFunctionGeneratorModel functionGeneratorModel) {
    super(adapter, deviceIdentifier);
    // TODO Auto-generated constructor stub
  }

  @Override
  public Collection<AnalogOutput> getAnalogOutputs() {
    // TODO Auto-generated method stub
    return null;
  }

  private static class AnalogOutputImpl implements FunctionGenerator.AnalogOutput {

    private ISCPISocket socket;

    private int channelId;

    private long memoryDepth;

    private int verticalResolution;

    public AnalogOutputImpl(
          ISCPISocket socket,
          int channelId,
          long memoryDepth,
          int verticalResolution) {
      this.socket = socket;
      this.channelId = channelId;
      this.memoryDepth = memoryDepth;
      this.verticalResolution = verticalResolution;
    }

    @Override
    public String getName() {
      return String.valueOf(this.channelId);
    }

    @Override
    public void setEnabled(boolean enabled) throws InstrumentException {
      try {
        this.socket.send(channelCommand("OUTP").with(enabled ? "ON" : "OFF").build());
      } catch (IOException e) {
        throw new InstrumentException(e);
      }
    }

    @Override
    public void setOperationMode(OperationMode operationMode) throws InstrumentException {
      // TODO Auto-generated method stub

    }

    @Override
    public void setImpedance(double impedance) throws InstrumentException {
      try {
        //TODO: validate impedance value (50-10000 Ohms or HiZ)
        this.socket.send(
                      channelCommand("OUTP")
                      .with("LOAD", impedance == Impedance.HIZ ? "HZ" : String.valueOf(impedance))
                      .build());
      } catch (IOException e) {
        throw new InstrumentException(e);
      }
    }

    @Override
    public void setAmplitude(double amplitude) throws InstrumentException {
      try {
        this.socket.send(channelCommand("OUTP").with("AMP", String.valueOf(amplitude)).build());
      } catch (IOException e) {
        throw new InstrumentException(e);
      }
    }

    @Override
    public void setOffset(double offset) throws InstrumentException {
      try {
        this.socket.send(channelCommand("OUTP").with("OFST", String.valueOf(offset)).build());
      } catch (IOException e) {
        throw new InstrumentException(e);
      }
    }

    @Override
    public void setFrequency(double frequency) throws InstrumentException {
      try {
        this.socket.send(channelCommand("OUTP").with("FREQ", String.valueOf(frequency)).build());
      } catch (IOException e) {
        throw new InstrumentException(e);
      }
    }

    @Override
    public void setPhase(double phase) throws InstrumentException {
      try {
        //TODO: validate Phase 0- 360
        this.socket.send(channelCommand("OUTP").with("PHSE", String.valueOf(phase)).build());
      } catch (IOException e) {
        throw new InstrumentException(e);
      }
    }

    @Override
    public void setWaveformType(WaveformType waveformType) throws InstrumentException {
      try {
        //TODO: RampDown = Triangle, 100% Sym; RampUp = Triangle, 0% Sym;
        this.socket.send(
                      channelCommand("OUTP")
                      .with("WVTP", String.valueOf(waveformType.toString()))
                      .build());
      } catch (IOException e) {
        throw new InstrumentException(e);
      }
    }

    private SCPICommand.Builder channelCommand(String command) {
      return SCPICommand.builder().command("C" + channelId, command);
    }

  }

  /*
  @Override
  public synchronized void setAnalogArbitraryWaveform(int channel, Signal<Float> signal) 
      throws IOException {
    validateChannelNumber(channel);
    if (signal.getData().size() != WAVE_LENGTH) {
      signal = sampler.sample(signal);
    }

    SCPICommand arbMode = channelCommand(channel, "BSWV").with("WVTP", "ARB").build();
    this.send(arbMode);
    this.waitForOperation(DEFAULT_TIMEOUT);

    SCPICommand srate = channelCommand(channel, "SRATE").with("MODE", "TARB").build();
    this.send(srate);
    this.waitForOperation(DEFAULT_TIMEOUT);

    SCPICommand wdata = channelCommand(channel, "WVDT")
                        .with("WVNM", signal.getId())
                        .with("LENGTH", "32KB")
        .with("FREQ", 1.0f / signal.period()).with("AMPL", signal.max() - signal.min())
        .with("OFST", (signal.max() + signal.min()) / 2.0f)
        .with("WAVEDATA", new String(binary(signal).array(), StandardCharsets.ISO_8859_1)).build();
    this.send(wdata);
    this.waitForOperation(DEFAULT_TIMEOUT);

    SCPICommand arbWave = channelCommand(channel, "ARWV").with("NAME", signal.getId()).build();
    this.send(arbWave);
    this.waitForOperation(DEFAULT_TIMEOUT);
  }

  private static ByteBuffer binary(Signal<Float> signal) {
    ByteBuffer buffer = ByteBuffer.allocate(16384 * 2);
    double min = signal.min();
    double max = signal.max();
    signal.getData().forEach(point -> {
      double k = (point.value - min) * 2 / (max - min) - 1;
      short value = (short) (k * 65535 / 2);
      byte low = (byte) (value & 0xFF);
      byte high = (byte) ((value & 0xFF00) >> 8);
      buffer.put(low);
      buffer.put(high);
    });
    return buffer;
  }
  */
}
