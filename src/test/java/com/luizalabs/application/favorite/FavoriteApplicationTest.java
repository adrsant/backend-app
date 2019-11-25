package com.luizalabs.application.favorite;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;

import com.luizalabs.entities.Favorite;
import com.luizalabs.entities.pk.FavoritePK;
import com.luizalabs.exception.ProductInvalidException;
import com.luizalabs.repositories.FavoriteRepository;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class FavoriteApplicationTest {
  @Mock private FavoriteCustomValidation customValidation;
  @Mock private FavoriteRepository repository;
  private FavoriteApplication application;

  @Before
  public void init() {
    this.application = new FavoriteApplication(List.of(customValidation), repository);
  }

  @Test
  public void should_add_favorite_product() {
    var favorite =
        Favorite.builder().productId(UUID.randomUUID()).clientId(UUID.randomUUID()).build();
    doNothing().when(customValidation).check(favorite);

    application.add(favorite);

    then(repository).should().save(favorite);
  }

  @Test
  public void should_not_add_favorite_product() {
    var favorite =
        Favorite.builder().productId(UUID.randomUUID()).clientId(UUID.randomUUID()).build();
    doThrow(ProductInvalidException.class).when(customValidation).check(favorite);

    assertThatThrownBy(() -> application.add(favorite))
        .isInstanceOf(ProductInvalidException.class);

    then(repository).should(never()).save(favorite);
  }

  @Test
  public void should_delete_favorite() {
    var favorite =
        Favorite.builder().productId(UUID.randomUUID()).clientId(UUID.randomUUID()).build();

    var pk =
        FavoritePK.builder()
            .clientId(favorite.getClientId())
            .productId(favorite.getProductId())
            .build();

    application.delete(favorite);
    repository.deleteById(pk);
  }

  @Test
  public void should_find_list_favorites() {
    UUID clientId = UUID.randomUUID();
    List<Favorite> favorites = List.of(Favorite.builder().build());

    given(repository.findByClientId(clientId)).willReturn(favorites);

    Collection<Favorite> favoriteList = application.find(clientId);

    assertThat(favorites).isSameAs(favoriteList);

    then(repository).should().findByClientId(clientId);
  }
}
