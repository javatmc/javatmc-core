package org.jtmc.core.visa;

import java.io.IOException;
import java.util.Collections;

import org.jtmc.core.visa.mock.MockSocket;
import org.jtmc.core.visa.mock.MockSocketFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

/**
 * VisaResourceManagerTest
 */
@RunWith(BlockJUnit4ClassRunner.class)
public class VisaResourceManagerTest {

	private VISAResourceManager visa = new VISAResourceManager(new MockSocketFactory(), Collections.emptyList());

	@Test
	public void testConnectSocketAsSocket() throws VisaException, IOException {
		try(MockSocket device = visa.connect("MOCK::TestSocket::inst0::INSTR", MockSocket.class)) {
			
		}
	}

	@Test
	public void testConnectSocketAsSpecializedSocket() throws VisaException, IOException {
		try(MockSocket device = visa.connect("MOCK::TestSocket::inst0::INSTR", MockSocket.class)) {
			
		}
	}
	
}