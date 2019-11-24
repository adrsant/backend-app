package com.luizalabs.application.favorite;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import com.luizalabs.entities.Favorite;
import com.luizalabs.exception.ResourceNotFoundException;
import com.luizalabs.infra.eai.FindProductIntegration;
import java.util.UUID;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ProductEnabledValidationTest {

  @Mock private FindProductIntegration integration;
  @InjectMocks private ProductEnabledValidation validation;

  @Test
  public void should_check_valid() {
    var favorite =
        Favorite.builder().productId(UUID.randomUUID()).clientId(UUID.randomUUID()).build();

    given(integration.execute(favorite.getProductId())).willReturn(true);
    validation.check(favorite);

    then(integration).should().execute(favorite.getProductId());
  }

  @Test
  public void should_check_invalid() {
    var favorite =
        Favorite.builder().productId(UUID.randomUUID()).clientId(UUID.randomUUID()).build();

    given(integration.execute(favorite.getProductId())).willReturn(false);

    assertThatThrownBy(() -> validation.check(favorite))
        .isInstanceOf(ResourceNotFoundException.class)
        .hasMessageContaining("product not found!");

    then(integration).should().execute(favorite.getProductId());
  }
}
