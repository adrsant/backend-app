package com.luizalabs.http;

import com.luizalabs.application.favorite.FavoriteApplication;
import com.luizalabs.entities.Favorite;
import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Valid
@RequestMapping("api/clients/{clientId}/favorite-products")
public class FavoriteProductsController {

  private FavoriteApplication application;

  @PostMapping(path = "/{productId}")
  @ResponseStatus(HttpStatus.CREATED)
  public void add(@PathVariable UUID clientId, @PathVariable UUID productId) {
    Favorite entity = Favorite.builder().clientId(clientId).productId(productId).build();
    application.add(entity);
  }

  @DeleteMapping("/{productId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable UUID clientId, @PathVariable UUID productId) {
    Favorite entity = Favorite.builder().clientId(clientId).productId(productId).build();
    application.delete(entity);
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public Collection<UUID> find(@PathVariable UUID clientId) {
    return application.find(clientId).stream()
        .map(Favorite::getProductId)
        .collect(Collectors.toList());
  }
}
