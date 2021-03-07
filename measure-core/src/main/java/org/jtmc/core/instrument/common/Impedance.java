package org.jtmc.core.instrument.common;

/**
 * Contains common impedance values used in test and measurement.
 * 
 * <p>Users and instrument drivers should use these values for a consistent
 * experience across devices.
 */
public class Impedance {
  
  /**
   * Represents a 50Ohm load.
   */
  public static final double Z50 = 50.0;

  /**
   * Represents a High Impedance load.
   */
  public static final double HIZ = Double.MAX_VALUE;
}
