package com.luizalabs.config;

import java.time.Duration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@EnableCircuitBreaker
@Configuration
public class ClientHttpConfig {

  @Value("${products-api.timeout.read}")
  private long readTimeout = 2000;

  @Value("${products-api.timeout.connection}")
  private long connectionTimeout = 500;

  @Bean
  public RestTemplate restTemplate(RestTemplateBuilder builder) {
    return builder
        .setConnectTimeout(Duration.ofMillis(connectionTimeout))
        .setReadTimeout(Duration.ofMillis(readTimeout))
        .build();
  }
}
