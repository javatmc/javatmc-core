package org.jtmc.core.signal.digital;

import org.jtmc.core.signal.CompositeSignal;
import org.jtmc.core.signal.Signal;

import static org.jtmc.core.util.Units.*;

/**
 * Wiegand
 */
public class Wiegand extends CompositeSignal<Boolean> {

	private int value;
	
	public Wiegand(int value) {
		super("W26_" + String.valueOf(value));
		this.value = value;
		Signal<Boolean> d0 = new Signal<>("D0");
		Signal<Boolean> d1 = new Signal<>("D1");
		this.add(d0);
		this.add(d1);
		
		int output = value << 1;
		if(Integer.bitCount(value & 0xFFF000) % 2 != 0) {
			output |= (1 << 25);
		}
		if(Integer.bitCount(value & 0x000FFF) % 2 == 0) {
			output |= 1;
		}
		double time = micro(1);
		d0.add(0, true);
		d1.add(0, true);
		for(int i=25;i>=0;i--) {
			if((output & (1 << i)) == 0) {
				d0.add(time, true);
				d0.add(time + micro(100), false);
				d0.add(time + micro(200), true);
			}
			else {
				d1.add(time, true);
				d1.add(time + micro(100), false);
				d1.add(time + micro(200), true);
			}
			time += milli(1);
		}
		d0.add(time, true);
		d1.add(time, true);
	}

	public int getValue() {
		return value;
	}
	
}