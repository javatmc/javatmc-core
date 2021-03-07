package org.jtmc.core.lxi.mdns;

import javax.jmdns.ServiceInfo;
import org.jtmc.core.visa.DeviceIdentifier;

/**
 * Contains mDNS related utility functions.
 */
public class MDNS {

  /**
   * Converts MDNS Service Information into DeviceIdentifier according to 
   * the keys in the mDNS text record.
   * 
   * <p>For more details see: LXI Specification 1.5.01, Section 10.4.3
   * 
   * @param serviceInfo mDNS text record
   * @return DeviceIdentifier
   */
  public static DeviceIdentifier getDeviceIdentifier(ServiceInfo serviceInfo) {
    String manufacturer = serviceInfo.getPropertyString("Manufacturer");
    String model = serviceInfo.getPropertyString("Model");
    String serialNumber = serviceInfo.getPropertyString("SerialNumber");
    String firmwareVersion = serviceInfo.getPropertyString("FirmwareVersion");

    return DeviceIdentifier.from(
      manufacturer, model, serialNumber, firmwareVersion);
  }
  
  /**
   * Returns the mDNS text record of a DeviceIdentifier according to the LXI
   * mDNS discovery specification.
   * 
   * <p>For more details see: LXI Specification 1.5.01, Section 10.4.3
   * 
   * @param identifier DeviceIdentifier
   * @return mDNS TXT record
   */
  public static String convertToTextRecord(DeviceIdentifier identifier) {
    StringBuffer buffer = new StringBuffer();
    buffer.append("Manufacturer=" + identifier.getManufacturer() + "\n");
    buffer.append("Model=" + identifier.getModel() + "\n");
    buffer.append("SerialNumber=" + identifier.getSerialNumber() + "\n");
    buffer.append("FirmwareVersion=" + identifier.getFirmwareVersion());

    return buffer.toString();
  }
}
