package org.jtmc.core.util;

import java.util.Locale;

/**
 * Util
 */
public final class Units {

	public static final double MEGA = 1000000.0;

	public static final double KILO = 1000.0;

	public static final double MILLI = 0.001;

	public static final double MICRO = 0.000001;

	public static final double NANO = 0.000000001;

	public static final double PICO = 0.00000000001;

	public static double mega(double value) {
		return MEGA * value;
	}

	public static double kilo(double value) {
		return KILO * value;
	}

	public static double milli(double value) {
		return MILLI * value;
	}

	public static double micro(double value) {
		return MICRO * value;
	}

	public static double nano(double value) {
		return NANO * value;
	}

	public static double nV(double nanovolts) {
		return nano(nanovolts);
	}

	public static double mV(double millivolts) {
		return milli(millivolts);
	}

	public static String auto(double value, int digits, String unit) {
		double abs = Math.abs(value);
		if(abs >= 1) {
			return toString(value, 1, digits, unit);
		}
		else if(abs >= Units.milli(1)) {
			return toString(value, MILLI, digits, "m" + unit);
		}
		else if(abs >= Units.micro(1)) {
			return toString(value, MICRO, digits, "u" + unit);
		}
		else if(abs >= Units.nano(1)) {
			return toString(value, NANO, digits, "n" + unit);
		}
		return toString(value, NANO, digits, unit);
	}

	private static String toString(double value, double range, int digits, String unit) {
		return String.format(Locale.US, "%.0" + digits + "f" + unit, value / range);
	}
}