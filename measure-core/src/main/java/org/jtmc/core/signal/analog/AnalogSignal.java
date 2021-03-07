package org.jtmc.core.signal.analog;

import java.util.function.Function;
import org.jtmc.core.signal.Signal;

/**
 * AnalogSignal is a numeric signal consisting of a floating point number stream.
 */
public class AnalogSignal extends Signal<Float> {

  public AnalogSignal(String id) {
    super(id);
  }

  public <U extends Comparable<U>> AnalogSignal(Signal<U> signal, Function<U, Float> transform) {
    super(signal, transform);
  }

  public double amplitude() {
    return max() - min();
  }
  
  public double offset() {
    return (max() + min()) / 2.0;
  }
  
}
