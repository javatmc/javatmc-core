package org.jtmc.core.lxi.mdns;

import javax.jmdns.ServiceInfo;

import org.jtmc.core.visa.DeviceIdentifier;

public class MDNS {

	/**
	 * Converts MDNS Service Information into DeviceIdentifier according to the keys
	 * 
	 * @param serviceInfo
	 * @return
	 */
	public static DeviceIdentifier resolveDeviceIdentifier(ServiceInfo serviceInfo) {
		String manufacturer = serviceInfo.getPropertyString("Manufacturer");
		String model = serviceInfo.getPropertyString("Model");
		String serialNumber = serviceInfo.getPropertyString("SerialNumber");
		String firmwareVersion = serviceInfo.getPropertyString("FirmwareVersion");
		
		return DeviceIdentifier.from(manufacturer, model, serialNumber, firmwareVersion);
	}
	
	public static String convertDeviceIdentifierToRecordKeys(DeviceIdentifier identifier) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("Manufacturer=" + identifier.getManufacturer() + "\n");
		buffer.append("Model=" + identifier.getModel() + "\n");
		buffer.append("SerialNumber=" + identifier.getSerialNumber() + "\n");
		buffer.append("FirmwareVersion=" + identifier.getFirmwareVersion());
		return buffer.toString();
	}
}
