package org.jtmc.core.signal.sampler;

import org.jtmc.core.signal.Signal;

/**
 * A sampler takes in an existing signal and converts it to a different signal.
 * 
 * <p>For example it may take an Analog Signal then using some threshold
 * it could convert it into a digital signal
 */
public interface Sampler<V extends Comparable<V>, U extends Signal<? extends Comparable<?>>> {

  /**
   * Converts the given signal into a different signal.
   * 
   * @param signal Input signal
   * @return Transformed signal
   */
  public U sample(Signal<V> signal);

}
