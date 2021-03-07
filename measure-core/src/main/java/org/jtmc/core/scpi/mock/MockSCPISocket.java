package org.jtmc.core.scpi.mock;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.net.SocketTimeoutException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jtmc.core.scpi.ISCPISocket;
import org.jtmc.core.scpi.SCPICommand;
import org.jtmc.core.visa.DeviceIdentifier;
import org.jtmc.core.visa.mock.MockSocket;

/**
 * MockSCPISocket is used to emulate a SCPI interpreting device.
 */
public abstract class MockSCPISocket implements ISCPISocket {

  private BlockingQueue<String> rxStream = new LinkedBlockingQueue<>(10);

  private final MockSocket socket;

  private final DeviceIdentifier idn;

  public MockSCPISocket(MockSocket socket, DeviceIdentifier idn) {
    this.socket = socket;
    this.idn = idn;
  }

  @Override
  public String toString() {
    return "MockSCPISocket[" + this.getClass().getSimpleName() + "]";
  }

  /**
   * Matches the given command path to the incoming command.
   * 
   * @param path Command pattern
   * @param command Command
   * @return Matcher
   */
  private Matcher matcher(String path, String command) {
    Pattern regex = Pattern.compile(path);
    return regex.matcher(command);
  }

  /**
   * Resolves method parameters using the given SCPI command and matcher.
   * 
   * @param matcher Matcher object, has to match before
   * @param method Method to call
   * @param command SCPICommand
   * @return Arguments
   */
  private Object[] resolve(Matcher matcher, Method method, SCPICommand command) {
    Object[] arguments = new Object[method.getParameterCount()];
    for (int i = 0; i < arguments.length; i++) {
      Parameter param = method.getParameters()[i];
      if (param.getType().equals(SCPICommand.class)) {
        arguments[i] = command;
      } else if (param.isNamePresent()) {
        String value = matcher.group(param.getName());
        arguments[i] = resolve(value, param.getType());
      }
    }
    return arguments;
  }

  private Object resolve(String value, Class<?> type) {
    if (type.equals(String.class)) {
      return value;
    } else if (type.equals(Integer.class) || type.equals(int.class)) {
      return Integer.parseInt(value);
    }
    throw new IllegalArgumentException("Can't convert '" + value + "' to " + type.getSimpleName());
  }

  /**
   * Converts command handler return values to response strings.
   * 
   * @param object Command return value
   * @return Response string
   */
  private String resolveReturnType(Object object) {
    if (object instanceof SCPICommand) {
      return ((SCPICommand) object).getRaw();
    } else if (object instanceof String) {
      return (String) object;
    }
    return null;
  }

  private void handle(
      Matcher matcher, 
      Method method, 
      SCPICommand command) throws InvocationTargetException {
    try {
      Object[] arguments = resolve(matcher, method, command);
      Object ret = method.invoke(this, arguments);

      String response = resolveReturnType(ret);
      if (response != null) {
        this.pushResponse(response);
      }
    } catch (IllegalAccessException | IllegalArgumentException e) {
      //TODO: log
      e.printStackTrace();
    }
  }

  /**
   * Called when the socket has received a command.
   * 
   * @param in Received SCPI command
   * @throws Exception when handling the command failed
   */
  protected void onReceive(SCPICommand in) throws Exception {
    for (Method method : this.getClass().getMethods()) {
      if (method.isAnnotationPresent(OnCommand.class)) {
        OnCommand path = method.getAnnotation(OnCommand.class);
        Matcher matcher = matcher(path.value(), in.getCommand());
        if (matcher.matches()) {
          handle(matcher, method, in);
          return;
        }
      }
    }
    this.onNotMapped(in);
  }

  /**
   * Pushes the SCPI command to the response stream.
   * 
   * @param response SCPI response
   */
  protected final void pushResponse(String response) {
    rxStream.offer(response);
  }

  /**
   * Called when receiving identification query command.
   * 
   * @return Response including the device's identifier
   */
  @OnCommand("\\*IDN\\?")
  public final String onIdn() {
    return idn.value();
  }

  /**
   * Called when receiving procedure complete query.
   * 
   * @return 1
   */
  @OnCommand("\\*OPC\\?")
  public final String onOpc() {
    return "1";
  }

  /**
   * Called when receiving reset command.
   */
  @OnCommand("\\*RST")
  public final void onRst() {
    this.onReset();
  }

  protected abstract void onReset();

  /**
   * Called when no mapping was found for the inbound command.
   * 
   * @param command Inbound SCPI command
   */
  protected abstract void onNotMapped(SCPICommand command);

  @Override
  public final void send(SCPICommand... commands) throws IOException {
    if (!socket.isConnected()) {
      throw new IOException("Device not connected.");
    }
    for (SCPICommand command : commands) {
      try {
        onReceive(command);
      } catch (Exception e) {
        throw new IOException(e);
      }
    }
  }

  @Override
  public String receive(int count, long timeout) throws IOException {
    throw new UnsupportedOperationException("Invalid operation");
  }

  @Override
  public final String receive(long timeout) throws SocketTimeoutException, IOException {
    try {
      if (!socket.isConnected()) {
        throw new IOException("Device not connected.");
      }
      String response = timeout == 0
          ? rxStream.take() : rxStream.poll(timeout, TimeUnit.MILLISECONDS);
      if (response == null) {
        throw new SocketTimeoutException("Response timed out.");
      }
      return response;
    } catch (InterruptedException e) {
      throw new IOException();
    }
  }

}
