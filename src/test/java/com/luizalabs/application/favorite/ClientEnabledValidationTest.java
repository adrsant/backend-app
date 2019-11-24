package com.luizalabs.application.favorite;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import com.luizalabs.entities.Favorite;
import com.luizalabs.exception.ProductInvalidException;
import com.luizalabs.exception.ResourceNotFoundException;
import com.luizalabs.repositories.ClientRepository;
import java.util.UUID;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ClientEnabledValidationTest {

  @Mock private ClientRepository repository;
  @InjectMocks private ClientEnabledValidation validation;

  @Test
  public void should_check_valid() {
    var favorite =
        Favorite.builder().productId(UUID.randomUUID()).clientId(UUID.randomUUID()).build();

    given(repository.existsById(favorite.getClientId())).willReturn(true);
    validation.check(favorite);

    then(repository).should().existsById(favorite.getClientId());
  }

  @Test
  public void should_check_invalid() {
    var favorite =
        Favorite.builder().productId(UUID.randomUUID()).clientId(UUID.randomUUID()).build();

    given(repository.existsById(favorite.getClientId())).willReturn(false);

    assertThatThrownBy(() -> validation.check(favorite))
        .isInstanceOf(ResourceNotFoundException.class)
        .hasMessageContaining("client not fount!");
    then(repository).should().existsById(favorite.getClientId());
  }
}
