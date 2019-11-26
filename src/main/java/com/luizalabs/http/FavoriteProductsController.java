package com.luizalabs.http;

import com.luizalabs.application.favorite.FavoriteApplication;
import com.luizalabs.entities.Favorite;
import java.util.UUID;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
  public FavoritesDTO find(@PathVariable UUID clientId, @Min(1) @RequestParam int page) {
    var pageRequest = PageRequest.of(page - 1, 10);
    var ids = application.find(clientId, pageRequest).map(Favorite::getProductId);
    return FavoritesDTO.builder()
        .meta(MetaData.builder().pageNumber(ids.getNumber() + 1).pageSize(ids.getSize()).build())
        .products(ids.getContent())
        .build();
  }
}
