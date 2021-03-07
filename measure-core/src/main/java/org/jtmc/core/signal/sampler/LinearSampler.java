package org.jtmc.core.signal.sampler;

import org.jtmc.core.signal.Signal;

/**
 * LinearSampler takes any numeric signal and uses linear interpolation between
 * values to fill out the missing parts.
 */
public class LinearSampler<U extends Number & Comparable<U>> implements Sampler<U, Signal<U>> {

  @Override
  public Signal<U> sample(Signal<U> signal) {
    return null;
  }

}
