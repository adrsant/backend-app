package com.luizalabs.config;

import io.micrometer.prometheus.PrometheusMeterRegistry;
import io.prometheus.client.hotspot.BufferPoolsExports;
import io.prometheus.client.hotspot.ClassLoadingExports;
import io.prometheus.client.hotspot.GarbageCollectorExports;
import io.prometheus.client.hotspot.MemoryAllocationExports;
import io.prometheus.client.hotspot.MemoryPoolsExports;
import io.prometheus.client.hotspot.StandardExports;
import io.prometheus.client.hotspot.ThreadExports;
import io.prometheus.client.hotspot.VersionInfoExports;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PrometheusConfiguration {

  @Bean
  MeterRegistryCustomizer<PrometheusMeterRegistry> jvmDefaultMetrics() {
    return prometheusRegistry -> {
      final var registry = prometheusRegistry.getPrometheusRegistry();
      registry.register(new StandardExports());
      registry.register(new MemoryPoolsExports());
      registry.register(new MemoryAllocationExports());
      registry.register(new BufferPoolsExports());
      registry.register(new GarbageCollectorExports());
      registry.register(new ThreadExports());
      registry.register(new ClassLoadingExports());
      registry.register(new VersionInfoExports());
    };
  }
}
