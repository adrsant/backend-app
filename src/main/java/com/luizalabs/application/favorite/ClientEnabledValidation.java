package com.luizalabs.application.favorite;

import com.luizalabs.entities.Favorite;
import com.luizalabs.exception.ResourceNotFoundException;
import com.luizalabs.repositories.ClientRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
class ClientEnabledValidation implements FavoriteCustomValidation {

  private ClientRepository repository;

  @Override
  public void check(Favorite favorite) {
    if(!repository.existsById(favorite.getClientId())){
      throw new ResourceNotFoundException("client not fount!");
    }
  }
}
