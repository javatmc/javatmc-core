package org.jtmc.core.signal.digital;

import org.jtmc.core.signal.Signal;

/**
 * BinarySignal is a logical signal consisting and 0's and 1's.
 */
public class BinarySignal extends Signal<Boolean> {

  public BinarySignal(String id) {
    super(id);
  }

}
