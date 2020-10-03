package org.jtmc.spring.configuration;

import java.util.List;

import org.jtmc.core.lxi.raw.RawSocketFactory;
import org.jtmc.core.lxi.vxi11.VXI11SocketFactory;
import org.jtmc.core.visa.factory.ISocketFactory;
import org.jtmc.core.visa.factory.SocketFactory;
import org.jtmc.core.visa.mock.MockSocketFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SocketFactoryAutoConfiguration {

	@Bean
	public ISocketFactory rawSocketFactory() {
		return new RawSocketFactory();
	}

	@Bean
	public ISocketFactory vxi11SocketFactory() {
		return new VXI11SocketFactory();
	}

	@Bean
	public ISocketFactory mockSocketFactory() {
		return new MockSocketFactory();
	}

	@Bean
	public SocketFactory socketFactory(List<ISocketFactory> factories){
		return new SocketFactory(factories);
	}
	
}