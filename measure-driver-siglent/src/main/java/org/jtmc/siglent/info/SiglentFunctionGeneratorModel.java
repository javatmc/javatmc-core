package org.jtmc.siglent.info;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * SiglentFunctionGeneratorModel contains model information about 
 * Siglent Function generators.
 */
public class SiglentFunctionGeneratorModel {

  public static final Pattern BENCHTOP_FGEN_REGEX = Pattern.compile(
      "SDG(?<seriesprefix>[123456])(?<id>[0-9]{2,3})(?<seriespostfix>X?)");
  
  private int series;

  /**
   * Creates a Siglent function generator model info container based on
   * the model number.
   * 
   * @param model Model number
   * @return Model information
   */
  public static SiglentFunctionGeneratorModel create(String model) {
    Matcher matcher = BENCHTOP_FGEN_REGEX.matcher(model);
    if (!matcher.matches()) {
      throw new IllegalArgumentException();
    }
    int series = Integer.parseInt(matcher.group("seriesprefix"));
    //String postfix = matcher.group("seriespostfix");
    //TODO: add more information
    
    return new SiglentFunctionGeneratorModel(series);
  }

  private SiglentFunctionGeneratorModel(int series) {
    this.series = series;
  }

  public int getSeries() {
    return series;
  }

}
