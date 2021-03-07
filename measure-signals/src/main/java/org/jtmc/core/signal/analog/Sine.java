package org.jtmc.core.signal.analog;

/**
 * Sine function as an AnalogSignal.
 */
public class Sine extends AnalogSignal {

  public Sine(double frequency, double amplitude, double offset) {
    super("");
  }

  public static Sine sine(double frequency, double amplitude, double offset) {
    return new Sine(frequency, amplitude, offset);
  }
}
