package org.jtmc.core.instrument;

import java.util.Collection;

/**
 * A logic analyzer is a device capable of making binary signal measurements
 * over time.
 * 
 * @author Balazs Eszes
 */
public interface LogicAnalyzer extends TimeDomainAnalyzer {

  public static final double TTL = 1.5f;

  public static final double CMOS = 1.65f;

  public static final double LVCMOS3V3 = 1.65f;

  public static final double LVCMOS2V5 = 1.25f;

  Collection<DigitalInput> getDigitalInputs();

  default DigitalInput getDigitalInput(int index) {
    return this.getDigitalInputs().stream().skip(index).findFirst().get();
  }

  /**
   * Returns the digital input with the matching name.
   * @param name Input name
   * @return Digital Input
   */
  default DigitalInput getDigitalInput(String name) {
    return this.getDigitalInputs()
               .stream()
               .filter(channel -> channel.getName()
               .equals(name))
               .findFirst()
               .get();
  }

  /**
   * DigitalInput represents a physical input recording logical values.
   */
  public static interface DigitalInput {
    String getName();

    void setEnabled(boolean enabled);

    void setThreshold(double threshold);
  }
}
