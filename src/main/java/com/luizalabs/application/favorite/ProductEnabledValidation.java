package com.luizalabs.application.favorite;

import com.luizalabs.entities.Favorite;
import com.luizalabs.exception.ResourceNotFoundException;
import com.luizalabs.infra.eai.FindProductIntegration;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor
class ProductEnabledValidation implements FavoriteCustomValidation {

  private FindProductIntegration integration;

  @Override
  @Cacheable(cacheNames = "products", key = "#favorite.productId")
  public void check(Favorite favorite) {
    boolean isEnabled = integration.execute(favorite.getProductId());

    if (!isEnabled) {
      throw new ResourceNotFoundException("product not found!");
    }
  }
}
