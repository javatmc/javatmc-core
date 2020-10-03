package org.jtmc.core.lxi.mdns;

/**
 * Contains mDNS service types that are used to discover
 * LXI capable devices
 */
public enum MDNSServiceType {
	LXI("_lxi._tcp.local"), 
	HTTP("_http._tcp.local"), 
	HISLIP("_hislip._tcp.local"), 
	SCPI_RAW("_scpi-raw._tcp.local"), 
	SCPI_TELNET("_scpi-telnet._tcp.local"), 
	VXI11("_vxi11._tcp.local");

	private String fqdn;

	public final static MDNSServiceType[] ALL = MDNSServiceType.values();
	
	private MDNSServiceType(String fqdn) {
		this.fqdn = fqdn;
	}

	/**
	 * Returns the fully qualified name of the mDNS service
	 * @return Fully Qualified mDNS service name
	 */
	public String fqdn() {
		return fqdn;
	}

}