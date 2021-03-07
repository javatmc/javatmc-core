package org.jtmc.spring.configuration;

import java.util.List;
import org.jtmc.core.visa.VISAResourceManager;
import org.jtmc.core.visa.VisaDeviceFactory;
import org.jtmc.core.visa.factory.SocketFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * ServiceAutoConfiguration provides VISA resource beans.
 */
@Configuration
public class ServiceAutoConfiguration {

  @Bean
  public VISAResourceManager visaResourceManager(
      SocketFactory socketFactory, 
      List<VisaDeviceFactory<?>> visaFactories) {
    return new VISAResourceManager(socketFactory, visaFactories);
  }

}
