package org.jtmc.core.scpi.mock;

import org.jtmc.core.scpi.SCPICommand;
import org.jtmc.core.visa.DeviceIdentifier;
import org.jtmc.core.visa.mock.MockSocket;

/**
 * TestSCPISocket can be used a base class for testing SCPI functionality.
 */
public class TestSCPISocket extends MockSCPISocket {

  public TestSCPISocket(DeviceIdentifier idn) {
    super(new MockSocket("TestSocket", "inst0"), idn);
  }

  @Override
  protected void onReset() {

  }

  @Override
  protected void onNotMapped(SCPICommand command) {

  }
}
