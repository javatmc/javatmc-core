package org.jtmc.core.signal.analog.sampler;

import java.util.function.Function;
import org.jtmc.core.signal.Signal;
import org.jtmc.core.signal.analog.AnalogSignal;
import org.jtmc.core.signal.sampler.Sampler;

/**
 * BinaryToAnalog converts a binary signal to an analog signal with the given
 * low and high associations.
 */
public class BinaryToAnalog implements 
    Sampler<Boolean, AnalogSignal>, Function<Boolean, Float> {

  private float high;

  private float low;

  public BinaryToAnalog(float high, float low) {
    this.high = high;
    this.low = low;
  }

  @Override
  public AnalogSignal sample(Signal<Boolean> signal) {
    return new AnalogSignal(signal, this);
  }

  @Override
  public Float apply(Boolean value) {
    return value ? high : low;
  }

}
