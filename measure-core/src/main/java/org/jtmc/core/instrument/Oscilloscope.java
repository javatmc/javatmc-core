package org.jtmc.core.instrument;

import java.util.Collection;
import java.util.Optional;
import org.jtmc.core.instrument.common.Coupling;
import org.jtmc.core.signal.analog.AnalogSignal;
import org.jtmc.core.visa.exception.InstrumentException;

/**
 * An oscilloscope is a device capable of making voltage measurements over time.
 * 
 * @author Balazs Eszes
 */
public interface Oscilloscope extends TimeDomainAnalyzer {

  Collection<AnalogInput> getAnalogInputs();

  default AnalogInput getAnalogInput(int index) {
    return this.getAnalogInputs().stream().skip(index).findFirst().get();
  }

  /**
   * Returns the analog input with the matching name.
   * @param name Input name
   * @return Analog Input
   */
  default AnalogInput getAnalogInput(String name) {
    return this.getAnalogInputs()
               .stream()
               .filter(channel -> channel.getName().equals(name))
               .findFirst()
               .get();
  }

  /**
   * AnalogInput represents a physical input recording numerical values.
   */
  public static interface AnalogInput {
    String getName();

    void setEnabled(boolean enabled) throws InstrumentException;

    void setRange(double range) throws InstrumentException;

    void setOffset(double offset) throws InstrumentException;

    void setProbeAttenuation(double attenuation) throws InstrumentException;

    void setProbeSense(boolean enable) throws InstrumentException;

    void setImpedance(double impedance) throws InstrumentException;

    void setBandwidthLimit(double bandwidthLimit) throws InstrumentException;

    void setCoupling(Coupling coupling) throws InstrumentException;

    Optional<AnalogSignal> getAnalogInputSignal(int channel) throws InstrumentException;
  }

}
