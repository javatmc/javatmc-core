package org.jtmc.core.instrument;

import java.util.Collection;

/**
 * A DC power supply is a device capable of outputting a set voltage 
 * or a set current.
 * 
 * @author Balazs Eszes
 */
public interface DCPowerSupply {

  Collection<PowerOutput> getPowerOutputs();

  /**
   * Returns the power output at the given index.
   * 
   * @param index Output index (zero indexed)
   * @return Power output
   */
  default PowerOutput getPowerOutput(int index) {
    return this.getPowerOutputs().stream().skip(index).findFirst().get();
  }

  /**
   * Returns the power output with the given name.
   * 
   * @param name Output name
   * @return Power output
   */
  default PowerOutput getPowerOutput(String name) {
    return this.getPowerOutputs()
              .stream()
              .filter(channel -> channel.getName().equals(name))
              .findFirst()
              .get();
  }

  /**
   * PowerOutput is used to represent a hardware output capable to sourcing and
   * optionally sinking current.
   */
  public static interface PowerOutput {
    /**
     * Returns the human readable name of the output.
     * 
     * @return Power output name
     */
    String getName();

    /**
     * Enables or disables the power output.
     * 
     * @param enabled if {@code true} the output is enabled
     */
    void setEnabled(boolean enabled);

    /**
     * Sets the maximum voltage the channel tries to output also known
     * as 'Set voltage'.
     * 
     * @param voltage Set voltage
     */
    void setMaximumVoltage(double voltage);

    double getMaximumVoltage();

    void setMaximumCurrent(double current);

    double getMaximumCurrent();

    /**
     * Returns the voltage currently output by the power output.
     * 
     * @return Output voltage
     */
    double getVoltage();

    /**
     * Return the current (amperes) currently output by the power output.
     * 
     * @return Output amperes
     */
    double getCurrent();
  }

}
