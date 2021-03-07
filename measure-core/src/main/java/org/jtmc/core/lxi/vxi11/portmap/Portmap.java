package org.jtmc.core.lxi.vxi11.portmap;

/**
 * Portmap is a service of ONC RPC for finding
 * RPC endpoints on a network. This class contains constants
 * used by the protocol.
 * 
 * <p>More information: https://tools.ietf.org/html/rfc1833
 */
public class Portmap {

  public static final int PROGRAM = 100000;

  public static final int VERSION = 2;

  public static final int PORT = 111;

  public static final int PROC_GETPORT = 3;
}
