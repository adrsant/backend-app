package com.luizalabs.application.favorite;

import com.luizalabs.entities.Favorite;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
class FoundFavoriteValidation implements FavoriteCustomValidation {

  @Override
  public void check(Favorite favorite) {}
}
