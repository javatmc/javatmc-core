package org.jtmc.core.visa;

/**
 * DeviceIdentifier holds instrument information as specified in the SCPI-99 standard.
 * 
 * <p>Includes the following properties:
 * <ul>
 * <li>Device vendor
 * <li>Model
 * <li>Serial number
 * <li>Firmware version
 * </ul>
 */
public final class DeviceIdentifier {

  public static final DeviceIdentifier UNKNOWN = new DeviceIdentifier("-", "-", "-", "-");

  private final String manufacturer;
  private final String model;
  private final String serialNumber;
  private final String firmwareVersion;

  private DeviceIdentifier(
        String manufacturer,
        String model,
        String serialNumber,
        String firmwareVersion) {
    this.manufacturer = manufacturer;
    this.model = model;
    this.serialNumber = serialNumber;
    this.firmwareVersion = firmwareVersion;
  }

  /**
   * Constructs an Identifier from the format seen in the LXI Discovery
   * and Identification specification.
   * 
   * @param raw Raw string in the 
   * @return Device identifier object
   */
  public static DeviceIdentifier from(String raw) {
    String[] info = raw.trim().split(",");
    if (info.length != 4) {
      throw new IllegalArgumentException(
          "Bad device identifier: \'" + raw + "\' must be 4 comma-separated fields");
    }
    return new DeviceIdentifier(info[0], info[1], info[2], info[3]);
  }

  /**
   * Constructs an Identifier from the fields found in LXI Discovery
   * and Identification specification.
   * 
   * @param manufacturer Manufacturer of the device
   * @param model Model number
   * @param serialNumber Serial number
   * @param firmwareVersion Firmware version
   * @return Device identifier object
   */
  public static DeviceIdentifier from(
        String manufacturer,
        String model,
        String serialNumber,
        String firmwareVersion) {
    return new DeviceIdentifier(manufacturer, model, serialNumber, firmwareVersion);
  }

  public String getManufacturer() {
    return manufacturer;
  }

  public String getModel() {
    return model;
  }

  public String getSerialNumber() {
    return serialNumber;
  }

  public String getFirmwareVersion() {
    return firmwareVersion;
  }

  /**
   * Returns the string representation of this identifier.
   * @return Raw string identifier
   */
  public String value() {
    return manufacturer + "," + model + "," + serialNumber + "," + firmwareVersion;
  }

  /**
   * Returns a string representing this identifier in a debug friendly way.
   * @return Debug string
   */
  @Override
  public String toString() {
    //TODO: rework format
    return "[mf=" + manufacturer + ";model=" + model
          + ";sn=" + serialNumber + ";ver=" + firmwareVersion + "]";
  }
}
