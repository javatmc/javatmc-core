package org.jtmc.siglent.info;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SiglentFunctionGeneratorModel {

	public final static Pattern BENCHTOP_FGEN_REGEX = Pattern.compile("SDG(?<seriesprefix>[123456])(?<id>[0-9]{2,3})(?<seriespostfix>X?)");
	
	private int series;

	public static SiglentFunctionGeneratorModel create(String model) {
		Matcher matcher = BENCHTOP_FGEN_REGEX.matcher(model);
		if(!matcher.matches()) {
			throw new IllegalArgumentException();
		}
		int series = Integer.parseInt(matcher.group("seriesprefix"));
		//String postfix = matcher.group("seriespostfix");
		//TODO: add more information
		
		return new SiglentFunctionGeneratorModel(series);
	}

	private SiglentFunctionGeneratorModel(int series) {
		this.series = series;
	}

	public int getSeries() {
		return series;
	}
}