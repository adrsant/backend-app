package com.luizalabs.application.favorite;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import com.luizalabs.entities.Favorite;
import com.luizalabs.entities.pk.FavoritePK;
import com.luizalabs.exception.ProductInvalidException;
import com.luizalabs.exception.ResourceNotFoundException;
import com.luizalabs.repositories.FavoriteRepository;
import java.util.UUID;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ProductAddedValidationTest {

  @Mock private FavoriteRepository repository;
  @InjectMocks private ProductAddedValidation validation;

  @Test
  public void should_check_valid() {
    var favorite =
        Favorite.builder().productId(UUID.randomUUID()).clientId(UUID.randomUUID()).build();

    var pk =
        FavoritePK.builder()
            .clientId(favorite.getClientId())
            .productId(favorite.getProductId())
            .build();

    given(repository.existsById(pk)).willReturn(false);
    validation.check(favorite);

    then(repository).should().existsById(pk);
  }

  @Test
  public void should_check_invalid() {
    var favorite =
        Favorite.builder().productId(UUID.randomUUID()).clientId(UUID.randomUUID()).build();

    var pk =
        FavoritePK.builder()
            .clientId(favorite.getClientId())
            .productId(favorite.getProductId())
            .build();

    given(repository.existsById(pk)).willReturn(true);

    assertThatThrownBy(() -> validation.check(favorite))
        .isInstanceOf(ProductInvalidException.class)
        .hasMessageContaining("product already exists in list!");
    then(repository).should().existsById(pk);
  }
}
