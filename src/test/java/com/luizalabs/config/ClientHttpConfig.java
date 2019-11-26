package com.luizalabs.config;

import java.time.Duration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.web.client.RestTemplate;

@Profile("test")
@Configuration
public class ClientHttpConfig {

  @Value("${products-api.timeout.read}")
  private long readTimeout;

  @Value("${products-api.timeout.connection}")
  private long connectionTimeout;

  @Bean
  public RestTemplate apiProducts(RestTemplateBuilder builder) {
    return builder
        .setConnectTimeout(Duration.ofMillis(connectionTimeout))
        .setReadTimeout(Duration.ofMillis(readTimeout))
        .build();
  }
}
