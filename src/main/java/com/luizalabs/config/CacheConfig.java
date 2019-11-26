package com.luizalabs.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.Ticker;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class CacheConfig {

  @Bean
  public CacheManager cacheManager(Ticker ticker) {
    CaffeineCache productsCache = buildCache("products", ticker, 30);
    SimpleCacheManager manager = new SimpleCacheManager();
    manager.setCaches(Arrays.asList(productsCache));
    return manager;
  }

  @Bean
  public Ticker ticker() {
    return Ticker.systemTicker();
  }

  private CaffeineCache buildCache(String name, Ticker ticker, int minutesToExpire) {
    return new CaffeineCache(
        name,
        Caffeine.newBuilder()
            .expireAfterWrite(minutesToExpire, TimeUnit.MINUTES)
            .maximumSize(100)
            .ticker(ticker)
            .build());
  }
}
