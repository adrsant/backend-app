package com.luizalabs.application.favorite;

import com.luizalabs.entities.Favorite;
import com.luizalabs.entities.pk.FavoritePK;
import com.luizalabs.exception.ResourceNotFoundException;
import com.luizalabs.repositories.FavoriteRepository;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class FavoriteApplication {

  private List<FavoriteCustomValidation> validations;
  private FavoriteRepository repository;

  public void add(Favorite favorite) {
    validations.forEach(v -> v.check(favorite));

    repository.save(favorite);

    log.info(
        "CLIENT ID {} HAS BEEN ADDED PRODUCT ID {} IN FAVORITES LIST  ",
        favorite.getClientId(),
        favorite.getProductId());
  }

  public void delete(Favorite favorite) {
    var pk =
        FavoritePK.builder()
            .clientId(favorite.getClientId())
            .productId(favorite.getProductId())
            .build();

    if (!repository.existsById(pk)) {
      throw new ResourceNotFoundException("favorite not found!");
    }
    repository.deleteById(pk);

    log.info(
        "CLIENT ID {} HAS BEEN REMOVED PRODUCT ID {} FROM FAVORITES LIST  ",
        favorite.getClientId(),
        favorite.getProductId());
  }

  public Collection<Favorite> find(UUID clientId) { // TODO create pagination
    return repository.findByClientId(clientId);
  }
}
