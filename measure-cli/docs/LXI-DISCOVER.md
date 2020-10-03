# LXI Discover command

Run `lxi-discover` to discover all instruments on your network using VXI-11.

---

## Options

`--interface -i`: Specifies which network interface to broadcast on. If not specified then it will broadcast on all interfaces

`--timeout -t`: Specifices the timeout in of service reponses in milliseconds, defaults to `1000`

### VXI-11 based discovery

`--vxi11`: Enables VXI-11 based instrument discovery

`--vxi11-no-resolve`: Disables automatic device identification

### mDNS based discovery

`--mdns`: Uses mDNS service discovery protocol to discover devices

`--mdns-services`: Specifies the mDNS services to discover, valid values can be found in the [MDNSServiceType enumeration](../../measure-core/src/main/java/org/jtmc/core/lxi/mdns/MDNSServiceType.java)

---

### Examples

Discover all VXI-11 devices connected on network interface *eth0* without resolving device identifiers

    lxi-discover --interface eth0 --vxi11 --vxi11-no-resolve

Discover all mDNS devices on the network supporting the LXI service

    lxi-discover --mdns --mdns-services LXI
