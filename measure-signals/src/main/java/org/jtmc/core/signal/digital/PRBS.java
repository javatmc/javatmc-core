package org.jtmc.core.signal.digital;

import org.jtmc.core.signal.Signal;

/**
 * PRBS
 */
public class PRBS extends Signal<Boolean> {

	public PRBS(int bitCount, float bitRate) {
		super("PRBS");
	}

}