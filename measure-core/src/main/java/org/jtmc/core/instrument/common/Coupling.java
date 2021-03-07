package org.jtmc.core.instrument.common;

/**
 * Coupling represents how a hardware input is connected to the sensing.
 * circuit
 */
public enum Coupling {
  /** 
   * A capacitor in series is activated on the sense line.
   */
  AC,

  /**
   * The input is directly connected to the sensing circuit.
   */
  DC,

  /**
   * The sense circuit's input is grounded instead of being connected to the
   * physical input.
   */
  GND
}
