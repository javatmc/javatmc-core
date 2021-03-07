package org.jtmc.core.lxi.vxi11;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketOptions;
import java.net.SocketTimeoutException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import org.acplt.oncrpc.OncRpcClient;
import org.acplt.oncrpc.OncRpcException;
import org.acplt.oncrpc.OncRpcProtocols;
import org.jtmc.core.device.ISocket;
import org.jtmc.core.lxi.vxi11.rpc.CreateLinkParams;
import org.jtmc.core.lxi.vxi11.rpc.CreateLinkResponse;
import org.jtmc.core.lxi.vxi11.rpc.DeviceError;
import org.jtmc.core.lxi.vxi11.rpc.DeviceFlags;
import org.jtmc.core.lxi.vxi11.rpc.DeviceLink;
import org.jtmc.core.lxi.vxi11.rpc.DeviceReadParams;
import org.jtmc.core.lxi.vxi11.rpc.DeviceReadResponse;
import org.jtmc.core.lxi.vxi11.rpc.DeviceWriteParams;
import org.jtmc.core.lxi.vxi11.rpc.DeviceWriteResponse;

/**
 * VXI11Socket implements the VXI11 RPC interface.
 */
public class VXI11Socket implements ISocket {

  public static final int DEFAULT_IO_TIMEOUT = 2000;

  public static final int DEFAULT_WRITE_BLOCK_SIZE = 8128;

  //TODO: update value
  public static final int CLIENT_ID = 1234567;

  private final InetAddress host;
  
  private final int port;

  private final String name;

  private int lockTimeout;

  private int ioTimeout;

  private int writeBlockSize;

  private transient OncRpcClient client;

  private transient CreateLinkResponse link;

  public VXI11Socket(final InetAddress host) throws IOException {
    this(host, 0, "inst0");
  }

  public VXI11Socket(final InetAddress host, final int port) throws IOException {
    this(host, port, "inst0");
  }

  public VXI11Socket(
        final InetAddress host,
        final int port,
        final String instrumentName) throws IOException {
    this(host, instrumentName, port, false, 0, DEFAULT_IO_TIMEOUT, DEFAULT_WRITE_BLOCK_SIZE);
  }

  /**
   * Creates a new VXI11Socket with the given parameters.
   * @param host IP Address of the intrument
   * @param name Name to be given to the instrument
   * @param port Port number of the instrument, use 0 to use Portmap interface for 
   * @param lock when {@code true} the instrument will be locked for {@code lockTimeout}
   * @param lockTimeout Maximum time to lock the device for
   * @param ioTimeout Maximum time to wait for IO operations to complete.
   * @param writeBlockSize Number of bytes to write in a single write operation
   * @throws IOException when
   */
  public VXI11Socket(
        final InetAddress host,
        final String name,
        int port,
        boolean lock,
        int lockTimeout,
        int ioTimeout,
        int writeBlockSize) throws IOException {
    this.host = host;
    this.port = port;
    this.name = name;
    this.lockTimeout = lockTimeout;
    this.ioTimeout = ioTimeout;
    this.writeBlockSize = writeBlockSize;

    try {
      client = OncRpcClient.newOncRpcClient(host,
                                            VXI11.DeviceCore.PROGRAM,
                                            VXI11.DeviceCore.VERSION,
                                            port,
                                            OncRpcProtocols.ONCRPC_TCP);
      link = createLink(CLIENT_ID, lock, lockTimeout, name);
    } catch (OncRpcException e) {
      client = null;
      throw new IOException(e);
    }
  }

  private CreateLinkResponse createLink(
        int clientId,
        boolean lockDevice,
        int lockTimeout,
        String device) throws OncRpcException {
    CreateLinkResponse response = new CreateLinkResponse();
    CreateLinkParams params = new CreateLinkParams(clientId, lockDevice, lockTimeout, device);
    client.call(VXI11.DeviceCore.CREATE_LINK, VXI11.DeviceCore.VERSION, params, response);
    return response;
  }

  private DeviceWriteResponse write(
        int linkId,
        int ioTimeout,
        int lockTimeout,
        DeviceFlags flags,
        byte... data) throws OncRpcException {
    DeviceLink link = new DeviceLink(linkId);
    DeviceWriteParams params = new DeviceWriteParams(link, ioTimeout, lockTimeout, flags, data);
    DeviceWriteResponse response = new DeviceWriteResponse();
    client.call(VXI11.DeviceCore.DEVICE_WRITE, VXI11.DeviceCore.VERSION, params, response);
    return response;
  }

  private DeviceReadResponse read(
        int linkId,
        int size,
        int ioTimeout,
        int lockTimeout,
        DeviceFlags flags,
        byte termination) throws OncRpcException {
    DeviceLink link = new DeviceLink(linkId);
    DeviceReadParams params = new DeviceReadParams(link,
                                                   size,
                                                   ioTimeout,
                                                   lockTimeout,
                                                   flags,
                                                   termination);
    DeviceReadResponse response = new DeviceReadResponse();
    client.call(VXI11.DeviceCore.DEVICE_READ, VXI11.DeviceCore.VERSION, params, response);
    return response;
  }

  private DeviceError destroyLink(int linkId) throws OncRpcException {
    DeviceError error = new DeviceError();
    DeviceLink link = new DeviceLink(linkId);
    client.call(VXI11.DeviceCore.DESTROY_LINK, VXI11.DeviceCore.VERSION, link, error);
    return error;
  }

  public int getPort() {
    return port;
  }

  @Override
  public void close() {
    try {
      destroyLink(link.getLinkId());
      client.close();
    } catch (OncRpcException e) {
      //log warning
    } finally {
      client = null;
    }
  }

  @Override
  public boolean isConnected() {
    return client != null;
  }

  @Override
  public String getResourceString() {
    return "TCPIP::" + this.host + "::" + this.name + "::INSTR";
  }

  @Override
  public void send(ByteBuffer message) throws IOException {
    if (!message.hasArray()) {
      throw new IOException("Message empty.");
    }
    try {
      int offset = 0;
      int size = message.capacity();
      byte[] block = new byte[Math.min(size, writeBlockSize)];
      while (offset < size) {
        message.get(block, offset, size - offset > writeBlockSize ? writeBlockSize : size);
        DeviceFlags flags = new DeviceFlags(false, offset + writeBlockSize >= size, false);
        DeviceWriteResponse response = this.write(link.getLinkId(),
                                                  ioTimeout,
                                                  lockTimeout,
                                                  flags,
                                                  block);
        if (response.getError() != VXI11.ErrorCode.NO_ERROR) {
          throw new VXI11Exception(response.getError());
        }
        offset += writeBlockSize;
      }
    } catch (OncRpcException e) {
      throw new IOException(e);
    }
  }

  @Override
  public ByteBuffer receive(int count, long timeout) throws IOException, SocketTimeoutException {
    try {
      DeviceFlags flags = new DeviceFlags(false, false, false);
      DeviceReadResponse response = this.read(link.getLinkId(),
                                              count,
                                              (int) timeout,
                                              lockTimeout,
                                              flags,
                                              (byte) 0);
      if (response.getError() != VXI11.ErrorCode.NO_ERROR) {
        throw new VXI11Exception(response.getError());
      }
      return ByteBuffer.wrap(response.data);
    } catch (OncRpcException e) {
      throw new IOException(e);
    }
  }

  @Override
  public ByteBuffer receive(char delimiter, long timeout)
      throws IOException, SocketTimeoutException {
    try {
      ArrayList<byte[]> buffer = new ArrayList<>();
      DeviceReadResponse response = null;
      DeviceFlags flags = new DeviceFlags(false, false, false);
      while (response == null || !response.isEOISet()) {
        response = this.read(link.getLinkId(),
                             SocketOptions.SO_RCVBUF,
                             (int) timeout,
                             lockTimeout,
                             flags,
                             (byte) delimiter);
        if (response.getError() != VXI11.ErrorCode.NO_ERROR) {
          throw new VXI11Exception(response.getError());
        }
      }

      buffer.add(response.data);
      int length = buffer.stream().mapToInt(array -> array.length).sum();
      ByteBuffer data = ByteBuffer.allocate(length);
      buffer.stream().forEach(data::put);

      return data;
    } catch (OncRpcException e) {
      throw new IOException(e);
    }
  }

}
