package com.luizalabs.application.favorite;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import com.luizalabs.entities.Favorite;
import com.luizalabs.entities.pk.FavoritePK;
import com.luizalabs.exception.ProductInvalidException;
import com.luizalabs.repositories.FavoriteRepository;
import java.util.List;
import java.util.UUID;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

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
    ArgumentCaptor<Favorite> argCaptor = ArgumentCaptor.forClass(Favorite.class);
    doNothing().when(customValidation).check(favorite);

    application.add(favorite);
    verify(customValidation).check(argCaptor.capture());
    then(repository).should().save(favorite);
  }

  @Test
  public void should_not_add_favorite_product() {
    var favorite =
        Favorite.builder().productId(UUID.randomUUID()).clientId(UUID.randomUUID()).build();
    doThrow(ProductInvalidException.class).when(customValidation).check(favorite);

    assertThatThrownBy(() -> application.add(favorite)).isInstanceOf(ProductInvalidException.class);

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

    given(repository.existsById(pk)).willReturn(true);

    application.delete(favorite);
    then(repository).should().deleteById(pk);
  }

  @Test
  public void should_not_delete_favorite() {
    var favorite =
        Favorite.builder().productId(UUID.randomUUID()).clientId(UUID.randomUUID()).build();

    var pk =
        FavoritePK.builder()
            .clientId(favorite.getClientId())
            .productId(favorite.getProductId())
            .build();
    given(repository.existsById(pk)).willReturn(false);

    assertThatThrownBy(() -> application.delete(favorite)).isInstanceOf(RuntimeException.class);

    then(repository).should(never()).deleteById(pk);
  }

  @Test
  public void should_find_list_favorites() {
    UUID clientId = UUID.randomUUID();
    Slice<Favorite> favorites = new SliceImpl(List.of(Favorite.builder().build()));
    var pageRequest = PageRequest.of(1, 5);

    given(repository.findByClientId(clientId, pageRequest)).willReturn(favorites);

    Slice<Favorite> favoriteList = application.find(clientId, pageRequest);

    assertThat(favorites).isSameAs(favoriteList);

    then(repository).should().findByClientId(clientId, pageRequest);
  }
}
