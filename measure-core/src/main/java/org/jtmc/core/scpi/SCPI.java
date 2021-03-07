package org.jtmc.core.scpi;

/**
 * Includes standard SCPI commands, such as resetting the device, 
 * waiting for an operation.
 */
public final class SCPI {

  /**
   * The standard command to query a device's identifier.
   * 
   * <p>The response is four comma-separated fields 
   * <i>'Manufacturer,Model,SerialNumber,FirmwareVersion'</i>
   */
  public static final SCPICommand idnQuery = SCPICommand.builder().query("*IDN").build();

  /**
   * The standard command to query whether the device has finished 
   * it's last operation.
   * 
   * <p>In sequential mode the response is a single '1' character
   */
  public static final SCPICommand opcQuery = SCPICommand.builder().query("*OPC").build();

  /**
   * The standard command to reset the device into a known state.
   * 
   * <p>The reset state is dependent on the particular device
   */
  public static final SCPICommand resetDevice = SCPICommand.builder().command("*RST").build();

  public static final SCPICommand clearStatus = SCPICommand.builder().query("*CLS").build();

  public static final SCPICommand testDevice = SCPICommand.builder().query("*TST").build();
}
