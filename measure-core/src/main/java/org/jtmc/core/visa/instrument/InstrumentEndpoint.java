package org.jtmc.core.visa.instrument;

import java.io.IOException;
import org.jtmc.core.device.ISocket;

/**
 * Instrument Endpoints allow an instrument to be connected.
 */
public interface InstrumentEndpoint {

  /**
   * Returns the socket connecting to the instrument.
   * @return Socket
   * @throws IOException if there was an error connecting the instrument
   */
  ISocket connect() throws IOException;

}
