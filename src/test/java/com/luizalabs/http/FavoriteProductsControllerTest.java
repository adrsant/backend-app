package com.luizalabs.http;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.luizalabs.application.favorite.FavoriteApplication;
import com.luizalabs.entities.Favorite;
import com.luizalabs.exception.ProductInvalidException;
import com.luizalabs.exception.ResourceNotFoundException;
import java.util.List;
import java.util.UUID;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.SliceImpl;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(value = FavoriteProductsController.class, secure = false)
public class FavoriteProductsControllerTest {

  private static final String CONTEXT_ROOT = "/api/clients/{clientId}/favorite-products";
  @Autowired private MockMvc mockMvc;
  @MockBean private FavoriteApplication application;

  @Test
  public void should_add_favorite() throws Exception {
    var favorite =
        Favorite.builder().clientId(UUID.randomUUID()).productId(UUID.randomUUID()).build();

    mockMvc
        .perform(
            post(CONTEXT_ROOT + "/{productId}", favorite.getClientId(), favorite.getProductId()))
        .andExpect(status().isCreated())
        .andReturn();

    then(application).should().add(favorite);
  }

  @Test
  public void should_not_add_favorite() throws Exception {
    var favorite =
        Favorite.builder().clientId(UUID.randomUUID()).productId(UUID.randomUUID()).build();
    var msgError = "product already exists in list!";

    doThrow(new ProductInvalidException(msgError)).when(application).add(favorite);

    mockMvc
        .perform(
            post(CONTEXT_ROOT + "/{productId}", favorite.getClientId(), favorite.getProductId()))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message").value(msgError))
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        .andReturn();

    then(application).should().add(favorite);
  }

  @Test
  public void should_delete_favorite() throws Exception {
    var favorite =
        Favorite.builder().clientId(UUID.randomUUID()).productId(UUID.randomUUID()).build();

    mockMvc
        .perform(
            delete(CONTEXT_ROOT + "/{productId}", favorite.getClientId(), favorite.getProductId()))
        .andExpect(status().isNoContent())
        .andReturn();

    then(application).should().delete(favorite);
  }

  @Test
  public void should_not_delete_favorite() throws Exception {
    var favorite =
        Favorite.builder().clientId(UUID.randomUUID()).productId(UUID.randomUUID()).build();
    var msgError = "product not found!";

    doThrow(new ResourceNotFoundException(msgError)).when(application).delete(favorite);

    mockMvc
        .perform(
            delete(CONTEXT_ROOT + "/{productId}", favorite.getClientId(), favorite.getProductId()))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.message").value(msgError))
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        .andReturn();

    then(application).should().delete(favorite);
  }

  @Test
  public void should_find_favorites() throws Exception {
    var favorite =
        Favorite.builder().clientId(UUID.randomUUID()).productId(UUID.randomUUID()).build();

    given(application.find(eq(favorite.getClientId()), any(Pageable.class)))
        .willReturn(new SliceImpl<>(List.of(favorite)));

    mockMvc
        .perform(get(CONTEXT_ROOT, favorite.getClientId()).param("page", "1"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        .andReturn();

    then(application).should().find(eq(favorite.getClientId()), any(Pageable.class));
  }
}
