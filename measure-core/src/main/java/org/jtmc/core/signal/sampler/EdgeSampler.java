package org.jtmc.core.signal.sampler;

import java.util.Iterator;

import org.jtmc.core.signal.Signal;
import org.jtmc.core.signal.Signal.DataPoint;

/**
 * EdgeSampler takes any signal and outputs a signal which is sampled at the given rate
 */
public class EdgeSampler<U extends Comparable<U>> implements Sampler<U, Signal<U>> {

	private long points;

	public EdgeSampler(long points) {
		this.points = points;
	}

	@Override
	public Signal<U> sample(Signal<U> signal) {
		Signal<U> output = new Signal<U>(signal.getId());
		double timestep = signal.period() / points;
		
		Iterator<DataPoint<U>> point = signal.getData().iterator();
		int index = 0;
		
		while(point.hasNext()) {
			DataPoint<U> p = point.next();
			for(;index * timestep < p.time || (!point.hasNext() && index < points);index++) {
				output.add(index * timestep, p.value);
			}
		}
		
		return output;
	}

	public long getPoints() {
		return points;
	}
	
}