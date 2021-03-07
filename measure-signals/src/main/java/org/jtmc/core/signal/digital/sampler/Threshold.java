package org.jtmc.core.signal.digital.sampler;

import org.jtmc.core.signal.Signal;
import org.jtmc.core.signal.Signal.DataPoint;
import org.jtmc.core.signal.digital.BinarySignal;
import org.jtmc.core.signal.sampler.Sampler;

/**
 * Threshold converts an analog signal to a binary signal using the given
 * threshold.
 */
public class Threshold implements Sampler<Float, BinarySignal> {

  private float threshold;

  /**
   * Constructs a Threshold sampler with the given threshold, values
   * below it will be interpreted as 0, otherwise will be 1's.
   * 
   * @param threshold Threshold
   */
  public Threshold(float threshold) {
    this.threshold = threshold;
  }

  @Override
  public BinarySignal sample(Signal<Float> signal) {
    BinarySignal output = new BinarySignal(signal.getId());
    boolean state = !(signal.first() > threshold);
    for (DataPoint<Float> point : signal.getData()) {
      boolean current = point.value > threshold;
      if (current != state) {
        output.add(point.time, current);
        state = current;
      }
    }
    return null;
  }

}
