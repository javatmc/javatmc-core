package org.jtmc.core.signal.analog.sampler;

import java.util.function.Function;
import org.jtmc.core.signal.Signal;
import org.jtmc.core.signal.analog.AnalogSampler;
import org.jtmc.core.signal.analog.AnalogSignal;

/**
 * This sampler inverts any analog signal alongside the voltage axis.
 */
public class AnalogInverter implements AnalogSampler, Function<Float, Float> {

  private final float offset;

  /**
   * Constructs an AnalogInverter with an offset of zero.
   */
  public AnalogInverter() {
    this(0.0f);
  }

  /**
   * Constructs an AnalogInverter with the given offset.
   * 
   * @param offset Offset
   */
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
   * Returns the offset of the inverter.
   * 
   * @return Offset
   */
  public float getOffset() {
    return offset;
  }

}
