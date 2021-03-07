package org.jtmc.cli.lxi;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.jtmc.core.lxi.LXIDiscovery;
import org.jtmc.core.lxi.LXIInstrumentEndpoint;
import org.jtmc.core.lxi.mdns.MDNSDiscovery;
import org.jtmc.core.lxi.mdns.MDNSServiceType;
import org.jtmc.core.lxi.vxi11.VXI11Discovery;
import picocli.CommandLine.ArgGroup;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

/**
 * LXICommand is used to discover instruments on a network.
 */
@Command(name = "lxi-discover")
public class LXIDiscover implements Callable<Integer> {

  @Option(
      names = { "--interface", "-i" }, 
      description = "Network interface to run discovery on, " 
        + "if not specified then it will broadcast on all interfaces", 
      required = false)
  public NetworkInterface networkInterface;

  @ArgGroup(exclusive = true)
  public DiscoverySettings discoverySettings;

  @Option(
      names = {"--json"}, 
      description = "Outputs a JSON formatted instrument array", 
      defaultValue = "false")
  public boolean jsonOutput;

  @Option(
      names = {"--no-warnings"}, 
      description = "Disables warnings in the output", 
      defaultValue = "false")
  public boolean noWarnings;

  public LXIDiscover() {
    this.discoverySettings = new DiscoverySettings();
    this.discoverySettings.vxi11Settings = new VXI11Settings();
  }

  /**
   * DiscoverySettings holds the possible discovery methods.
   */
  public static class DiscoverySettings {
    @ArgGroup(exclusive = false)
    public VXI11Settings vxi11Settings;

    @ArgGroup(exclusive = false)
    public MDNSSettings mdnsSettings;

    LXIDiscovery discovery() {
      return vxi11Settings != null ? vxi11Settings.discovery() : mdnsSettings.discovery();
    }
  }

  /**
   * VXI11Settings holds the options for VXI11 based instrument discovery.
   */
  public static class VXI11Settings {
    @Option(
        names = { "--vxi11" }, 
        description = "Enables VXI-11 based instrument discovery", 
        defaultValue = "true")
    public boolean vxi11DiscoveryEnabled;

    @Option(
        names = {"--vxi11-no-resolve"}, 
        description = "Disables automatic device identification", 
        defaultValue = "false")
    public boolean vxi11noResolve;

    @Option(
        names = {"--vxi11-retransmissions" }, 
        description = "Number of retransmission, useful when using unreliable connection", 
        defaultValue = "1")
    public int vxi11retransmissions;

    @Option(
        names = {"--vxi11-timeout" }, 
        description = "Specify the timeout of a response in milliseconds", 
        defaultValue = "1000")
    public int vxi11timeout;

    VXI11Settings() {
      this.vxi11DiscoveryEnabled = true;
      this.vxi11noResolve = false;
      this.vxi11retransmissions = 1;
      this.vxi11timeout = 1000;
    }

    LXIDiscovery discovery() {
      return new VXI11Discovery(!vxi11noResolve, vxi11retransmissions, vxi11timeout);
    }
  }

  /**
   * MDNSSettings holds the options for mDNS based instrument discovery.
   */
  public static class MDNSSettings {
    @Option(
        names = { "--mdns" }, 
        description = "Enables mDNS based instrument discovery", 
        defaultValue = "false")
    public boolean mdnsDiscoveryEnabled;

    @Option(
        names = { "--mdns-services" }, 
        description = "Specify mDNS service types to search")
    public List<MDNSServiceType> mdnsServiceTypes = Collections.singletonList(MDNSServiceType.LXI);

    LXIDiscovery discovery() {
      return new MDNSDiscovery(mdnsServiceTypes);
    }
  }

  @Override
  public Integer call() throws Exception {
    //Collecting network interfaces to discover on
    Stream<NetworkInterface> interfaces;
    if (networkInterface == null) {
      // Not using NetworkInterface.networkInterfaces() as it's only introduced in
      // Java 9
      List<NetworkInterface> interfaceList = new LinkedList<>();
      Enumeration<NetworkInterface> interfaceEnumeration = NetworkInterface.getNetworkInterfaces();
      while (interfaceEnumeration.hasMoreElements()) {
        interfaceList.add(interfaceEnumeration.nextElement());
      }
      interfaces = interfaceList.stream();
    } else {
      interfaces = Collections.singletonList(networkInterface).stream();
    }

    //Running discovery tool
    LXIDiscovery discoveryTool = discoverySettings.discovery();
    List<String> warnings = new ArrayList<>();
    Set<LXIInstrumentEndpoint> instruments = interfaces.flatMap(i -> {
      try {
        return discoveryTool.discover(i).stream();
      } catch (Exception e) {
        if (!noWarnings) {
          warnings.add("Warning " + e.getClass() + ":" + e.getMessage());
        }
        return Collections.<LXIInstrumentEndpoint>emptySet().stream();
      }
    }).collect(Collectors.toCollection(HashSet::new));

    //Printing out the result
    if (jsonOutput) {
      ObjectMapper mapper = new ObjectMapper();
      InstrumentListJSON json = new InstrumentListJSON(instruments, warnings);
      mapper.writeValue(System.out, json);
    } else {
      instruments.forEach(endpoint -> {
        System.out.printf("Found %s at %s:%g (%s)", 
            endpoint.getDeviceIdentifier().value(), 
            endpoint.getHost(), 
            endpoint.getPort(), 
            endpoint.getClass().getSimpleName());
      });
      warnings.forEach(warning -> {
        System.out.println(warning);
      });
    }
    return 0;
  }

  /**
   * InstrumentListJSON is a container for instrument discovery result.
   */
  public static class InstrumentListJSON {

    Set<LXIInstrumentEndpoint> instruments;

    List<String> warnings;

    public InstrumentListJSON(Set<LXIInstrumentEndpoint> instruments, List<String> warnings) {
      this.instruments = instruments;
      this.warnings = warnings;
    }

    public Set<LXIInstrumentEndpoint> getInstruments() {
      return instruments;
    }

    public List<String> getWarnings() {
      return warnings;
    }

  }

}
