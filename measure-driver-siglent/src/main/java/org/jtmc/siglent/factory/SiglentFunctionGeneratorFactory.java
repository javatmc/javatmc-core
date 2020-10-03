package org.jtmc.siglent.factory;

import java.io.IOException;

import org.jtmc.core.scpi.ISCPISocket;
import org.jtmc.core.visa.DeviceIdentifier;
import org.jtmc.core.visa.VisaException;
import org.jtmc.siglent.driver.SDGDriver;
import org.jtmc.siglent.info.SiglentFunctionGeneratorModel;

public class SiglentFunctionGeneratorFactory {

	public boolean supports(ISCPISocket scpiSocket) {
		try {
			SiglentFunctionGeneratorModel.create(scpiSocket.getDeviceIdentifier().getModel());
			return true;
		}
		catch(IllegalArgumentException | IOException e) {
			return false;
		}
	}

	public ISCPISocket create(ISCPISocket scpiSocket) throws VisaException {
		try {
			DeviceIdentifier deviceIdentifier = scpiSocket.getDeviceIdentifier();
			String model = deviceIdentifier.getModel();
			SiglentFunctionGeneratorModel fgenModel = SiglentFunctionGeneratorModel.create(model);

			return new SDGDriver(scpiSocket, deviceIdentifier, fgenModel);
		} catch(IOException e) {
			throw new VisaException(e);
		}
	}
}