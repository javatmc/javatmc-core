package org.jtmc.core.lxi.mdns;

/**
 * Contains mDNS service types that are used to discover LXI capable devices.
 * 
 * <p>For more details see: LXI Specification 1.5.01, Section 10.4.3.10
 */
public enum MDNSServiceType {

  /**
   * LXI Service type denotes an endpoint capable of identification via HTTP
   * the service is expected to be on port 80.
   */
  LXI("_lxi._tcp.local"),

  /**
   * HTTP Service type denotes an endpoint that may be accessed 
   * from a web browser.
   */
  HTTP("_http._tcp.local"),

  /**
   * HiSLIP Service type denotes an endpoint hosting a HiSLIP server.
   */
  HISLIP("_hislip._tcp.local"),

  /**
   * SCPI Raw Service type denotes an endpoint hosting a TCP server 
   * interpreting raw SCPI commands.
   */
  SCPI_RAW("_scpi-raw._tcp.local"),

  /**
   * SCPI Telnet Service type denotes an endpoint hosting a Telnet server
   * interpreting SCPI commands.
   */
  SCPI_TELNET("_scpi-telnet._tcp.local"),

  /**
   * VXI11 Service type denotes an endpoint hosting a VXI-11 Server.
   */
  VXI11("_vxi11._tcp.local");

  private String fqdn;

  public static final MDNSServiceType[] ALL = MDNSServiceType.values();
  
  private MDNSServiceType(String fqdn) {
    this.fqdn = fqdn;
  }

  /**
   * Returns the fully qualified name of the mDNS service.
   * @return Fully Qualified mDNS service name
   */
  public String fqdn() {
    return fqdn;
  }
}
