package org.jtmc.core.visa;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

import org.jtmc.core.device.ISocket;
import org.jtmc.core.visa.exception.UnsupportedSocketException;
import org.jtmc.core.visa.factory.ISocketFactory;
/**
 * VISAResourceManager
 */
public class VISAResourceManager implements AutoCloseable {

	private ISocketFactory socketFactory;

	private List<VisaDeviceFactory<?>> factories;

	private transient List<ISocket> sockets;

	public VISAResourceManager(final ISocketFactory socketFactory, List<VisaDeviceFactory<?>> factories) {
		this.socketFactory = socketFactory;
		this.factories = new LinkedList<>(factories);
		this.sockets = new LinkedList<>();
	}

	public ISocket connect(String resourceString) throws IOException, VisaException {
		return socketFactory.create(resourceString);
	}

	public <T> T connect(String resourceString, Class<T> klass) throws IOException, VisaException {
		ISocket socket = this.connect(resourceString);

		ArrayList<Object> candidates = new ArrayList<>(factories.size());
		candidates.add(socket);
		
		for(VisaDeviceFactory<?> factory : factories) {
			try {
				if(factory.supports(socket)) {
					Object dev = factory.create(socket);
					candidates.add(dev);
				}
			} catch(UnsupportedSocketException e) {
				e.printStackTrace();
			}
		}
		
		try {
			return findCandidate(candidates, klass);
		} catch(NoSuchElementException e) {
			throw new VisaException("No candidate found for type " + klass);
		}
	}

	private static <T> T findCandidate(Collection<Object> candidates, Class<T> klass) throws NoSuchElementException {
		for(Object obj : candidates) {
			if(klass.isAssignableFrom(obj.getClass())) {
				//TODO: handle ambiguity, prefer equal type
				return klass.cast(obj);
			}
		}
		throw new NoSuchElementException();
	}    

	@Override
	public void close() {
		this.sockets.forEach(ISocket::close);
	}

}