package com.luizalabs.application.favorite;

import com.luizalabs.entities.Favorite;
import com.luizalabs.exception.ProductInvalidException;
import com.luizalabs.exception.ResourceNotFoundException;
import com.luizalabs.infra.eai.FindProductIntegration;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
class ProductEnabledValidation implements FavoriteCustomValidation {

  private FindProductIntegration integration;

  @Override
  public void check(Favorite favorite) {
    boolean isEnabled = integration.execute(favorite.getProductId());

    if(!isEnabled){
      throw new ResourceNotFoundException("product not found!");
    }
  }
}
