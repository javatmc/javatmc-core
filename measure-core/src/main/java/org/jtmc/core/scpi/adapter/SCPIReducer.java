package org.jtmc.core.scpi.adapter;

import java.io.IOException;

import org.jtmc.core.scpi.ISCPISocket;
import org.jtmc.core.scpi.SCPICommand;
import org.jtmc.core.scpi.SCPISocketAdapter;

/**
 * SCPIReducer
 */
public class SCPIReducer extends SCPISocketAdapter {

    public SCPIReducer(ISCPISocket adapter) {
        super(adapter);
    }

    @Override
    public void send(SCPICommand... commands) throws IOException {
        //TODO: reduce
    }
    
}