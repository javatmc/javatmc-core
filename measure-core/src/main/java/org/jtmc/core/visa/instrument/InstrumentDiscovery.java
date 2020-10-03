package org.jtmc.core.visa.instrument;

import java.util.Set;

import org.jtmc.core.visa.VisaException;

/**
 * InstrumentDiscovery can be used to discover instruments accessible
 * to the test controller
 */
public interface InstrumentDiscovery {

	/**
	 * Discovers instruments and returns a set of connectable endpoints
	 * 
	 * @return Instrument Endpoints
	 * @throws VisaException when an error occured while discovering instruments
	 */
	Set<? extends InstrumentEndpoint> discover() throws VisaException;
}
