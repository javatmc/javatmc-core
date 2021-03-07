package org.jtmc.siglent.info;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jtmc.core.util.Units;

/**
 * SiglentOscilloscopeInfo is used to contain meta information about 
 * Siglent Oscilloscopes.
 */
public class SiglentOscilloscopeModel {
 
  public static final Pattern SCOPE_REGEX = Pattern.compile(
      "SDS(?<seriesprefix>[123456])(?<bandwidth>[0-9]{2})(?<channels>[24])"
      + "(?<seriespostfix>DL\\+|CML\\+|CFL|X(\\+|-E)?)");

  private int series;

  private String bandwidth;

  private int channels;

  private String postfix;

  /**
   * Converts a Siglent Oscilloscope model number into a metainfo container object.
   * 
   * @param model Oscilloscope model
   * @return Oscilloscope information
   * @throws IllegalArgumentException If the model number is invalid
   */
  public static SiglentOscilloscopeModel create(String model) throws IllegalArgumentException {
    Matcher matcher = SCOPE_REGEX.matcher(model);
    if (!matcher.matches()) {
      throw new IllegalArgumentException();
    }
    int series = Integer.parseInt(matcher.group("seriesprefix"));
    String bandwidth = matcher.group("bandwidth");
    int channels = Integer.parseInt(matcher.group("channels"));
    String postfix = matcher.group("seriespostfix");
    return new SiglentOscilloscopeModel(series, bandwidth, channels, postfix);
  }

  private SiglentOscilloscopeModel(int series, String bandwidth, int channels, String postfix) {
    this.series = series;
    this.channels = channels;
    this.postfix = postfix;
  }

  /**
   * Returns the analog bandwidth of the oscilloscope model.
   * @return Analog bandwidth
   */
  public double getBandwidth() {
    if (this.series == 5) {
      switch (this.bandwidth) {
        case "03": return Units.mega(350);
        case "05": return Units.mega(500);
        case "10": return Units.mega(1000);
        default: break;
      }
    }
    switch (this.bandwidth) {
      case "03": return Units.mega(350);
      case "05": return Units.mega(50);
      case "07": return Units.mega(70);
      case "10": return Units.mega(100);
      case "15": return Units.mega(150);
      case "20": return Units.mega(200);
      case "30": return Units.mega(300);
      case "35": return Units.mega(350);
      default: break;
    }
    throw new IllegalArgumentException("Unable to determine bandwidth from: " 
        + this.bandwidth + "(series " + this.series + ")");
  }

  public int getSeries() {
    return series;
  }

  public int getChannels() {
    return channels;
  }

  public String getPostfix() {
    return postfix;
  }

}
