package org.jtmc.core.lxi.vxi11;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.acplt.oncrpc.OncRpcBroadcastEvent;
import org.acplt.oncrpc.OncRpcBroadcastListener;
import org.acplt.oncrpc.OncRpcException;
import org.acplt.oncrpc.OncRpcProtocols;
import org.acplt.oncrpc.OncRpcUdpClient;
import org.jtmc.core.lxi.LXIDiscovery;
import org.jtmc.core.lxi.vxi11.portmap.Portmap;
import org.jtmc.core.lxi.vxi11.portmap.PortmapParams;
import org.jtmc.core.lxi.vxi11.portmap.PortmapResponse;
import org.jtmc.core.scpi.socket.RawSCPISocket;
import org.jtmc.core.visa.DeviceIdentifier;

/**
 * VXI11Discovery implements RPC based instrument discovery
 * 
 * <p>This implementation broadcasts RPC Portmap queries over UDP. The portmap
 * parameters specifically look for the existence of the VXI-11 Device Core
 * program over TCP.
 */
public class VXI11Discovery implements LXIDiscovery {

  /**
   * Default timeout, 1 second as specified in the LXI standard.
   */
  public static final int DEFAULT_TIMEOUT = 1000;

  private int timeout;

  private boolean resolveDeviceInfo;

  private int retransmissions;

  /**
   * Constructs a new VXI-11 discovery tool using the default parameters.
   * 
   * <ul>
   * <li>1 second timeout
   * <li>Don't resolve device identifiers
   * <li>Retransmit 1 time
   * </ul>
   */
  public VXI11Discovery() {
    this(false);
  }

  /**
   * Constructs a new VXI-11 discovery tool.
   * 
   * @param resolveDeviceInfo If {@code true} then after discovering devices it
   *                          will attempt to do an identification query
   */
  public VXI11Discovery(boolean resolveDeviceInfo) {
    this(resolveDeviceInfo, 1);
  }

  /**
   * Constructs a new VXI-11 discovery tool.
   * 
   * @param resolveDeviceInfo If {@code true} then after discovering devices it
   *                          will attempt to do an identification query
   * @param retransmissions   The number of retransmissions when broadcasting the
   *                          portmap message, {@code 0} specifies no
   *                          retranmissions so it will do a total of {@code 1}
   *                          tranmission
   */
  public VXI11Discovery(boolean resolveDeviceInfo, int retransmissions) {
    this(resolveDeviceInfo, retransmissions, DEFAULT_TIMEOUT);
  }

  /**
   * Constructs a new VXI-11 discovery tool.
   * 
   * @param resolveDeviceInfo If {@code true} then after discovering devices it
   *                          will attempt to do an identification query
   * @param retransmissions   The number of retransmissions when broadcasting the
   *                          portmap message, {@code 0} specifies no
   *                          retranmissions so it will do a total of {@code 1}
   *                          tranmission
   * @param timeout           Time to wait for portmap responses in milliseconds
   */
  public VXI11Discovery(boolean resolveDeviceInfo, int retransmissions, int timeout) {
    this.timeout = timeout;
    this.resolveDeviceInfo = resolveDeviceInfo;
    this.retransmissions = retransmissions;
  }

  @Override
  public Set<VXI11InstrumentEndpoint> discover(
      NetworkInterface networkInterface) throws IOException {
    try {
      InetAddress bcast = getBroadcastAddress(networkInterface);
      OncRpcUdpClient rpcClient = (OncRpcUdpClient) OncRpcUdpClient.newOncRpcClient(bcast, 
          Portmap.PROGRAM, Portmap.VERSION, Portmap.PORT, OncRpcProtocols.ONCRPC_UDP);
      rpcClient.setTimeout(timeout);

      Set<VXI11InstrumentEndpoint> devices = new HashSet<>();

      PortmapResponse response = new PortmapResponse();
      OncRpcBroadcastListener callback = new OncRpcBroadcastListener() {
        @Override
        public void replyReceived(OncRpcBroadcastEvent evt) {
          VXI11InstrumentEndpoint endpoint = new VXI11InstrumentEndpoint(evt.getReplyAddress(),
              response.getPort(), DeviceIdentifier.UNKNOWN);
          devices.add(endpoint);
        }
      };

      //TODO: find out where the 4 comes from
      PortmapParams portmapRequest = new PortmapParams(
          VXI11.DeviceCore.PROGRAM, VXI11.DeviceCore.VERSION, OncRpcProtocols.ONCRPC_TCP, 4);

      for (int i = 0; i < retransmissions + 1; i++) {
        rpcClient.broadcastCall(Portmap.PROC_GETPORT, portmapRequest, response, callback);
      }

      if (resolveDeviceInfo) {
        Set<VXI11InstrumentEndpoint> resolved = devices.stream().map(endpoint -> {
          try (VXI11Socket socket = endpoint.connect()) {
            RawSCPISocket scpiSocket = new RawSCPISocket(socket);
            return new VXI11InstrumentEndpoint(endpoint.getHost(), endpoint.getPort(),
                scpiSocket.getDeviceIdentifier());
          } catch (IOException e) {
            return endpoint;
          }
        }).collect(Collectors.toSet());
        return resolved;
      }

      return devices;
    } catch (OncRpcException e) {
      throw new IOException(e);
    } catch (IllegalArgumentException e) {
      throw new IOException(e);
    }
  }

  /**
   * Returns a broadcast address for the given network interface.
   * 
   * @param networkInterface Network interface
   * @return Broadcast IP Address
   * @throws SocketException If the network interface is unavailable
   */
  private static InetAddress getBroadcastAddress(
      NetworkInterface networkInterface) throws SocketException {

    if (!networkInterface.isUp() || networkInterface.isVirtual()) {
      throw new IllegalArgumentException(
          "Network interface '" + networkInterface.getName() + "' unavailable or is virtual.");
    }
    Optional<InterfaceAddress> address = networkInterface.getInterfaceAddresses().stream()
        .filter(addr -> addr.getBroadcast() != null).findFirst();
    return address
        .orElseThrow(() -> new IllegalArgumentException(
            "Network interface '" + networkInterface.getName() + "' has no broadcast address"))
        .getBroadcast();
  }

}
