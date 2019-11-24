package com.luizalabs.application.favorite;

import com.luizalabs.entities.Favorite;
import com.luizalabs.entities.pk.FavoritePK;
import com.luizalabs.exception.ProductInvalidException;
import com.luizalabs.repositories.FavoriteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
class ProductAddedValidation implements FavoriteCustomValidation {

  private FavoriteRepository repository;

  @Override
  public void check(Favorite favorite) {
    FavoritePK pk =
        FavoritePK.builder()
            .clientId(favorite.getClientId())
            .productId(favorite.getProductId())
            .build();

    boolean isEnabled = repository.existsById(pk);

    if (isEnabled) {
      throw new ProductInvalidException("product already exists in list!");
    }
  }
}
