package com.luizalabs.http;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luizalabs.application.client.ClientApplication;
import com.luizalabs.entities.Client;
import com.luizalabs.exception.EmailClientFoundException;
import com.luizalabs.exception.ResourceNotFoundException;
import java.util.List;
import java.util.UUID;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.SliceImpl;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(value = ClientController.class, secure = false)
public class ClientControllerTest {

  private static final String CONTEXT_ROOT = "/api/clients";
  @Autowired private MockMvc mockMvc;
  @MockBean private ClientApplication application;

  @Test
  public void should_find_client() throws Exception {
    Client client = Client.builder().email("teste").id(UUID.randomUUID()).build();

    given(application.find(client.getId())).willReturn(client);
    mockMvc
        .perform(get(CONTEXT_ROOT + "/{id}", client.getId()))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        .andReturn();

    then(application).should().find(client.getId());
  }

  @Test
  public void should_find_all_clients() throws Exception {
    Client client = Client.builder().email("teste").id(UUID.randomUUID()).build();

    given(application.find(Mockito.any(Pageable.class)))
        .willReturn(new SliceImpl<>(List.of(client)));
    mockMvc
        .perform(get(CONTEXT_ROOT).param("page", "1"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        .andReturn();

    then(application).should().find(Mockito.any(Pageable.class));
  }

  @Test
  public void should_not_find_client() throws Exception {
    var id = UUID.randomUUID();
    var msgError = "client not found!";

    given(application.find(id)).willThrow(new ResourceNotFoundException(msgError));
    mockMvc
        .perform(get(CONTEXT_ROOT + "/{id}", id))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.message").value(msgError))
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        .andReturn();

    then(application).should().find(id);
  }

  @Test
  public void should_create_client() throws Exception {
    var client = Client.builder().id(null).email("teste").name("NAME TEST").build();
    var json = new ObjectMapper().writeValueAsString(client);

    mockMvc
        .perform(post(CONTEXT_ROOT).content(json).contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(status().isCreated())
        .andReturn();

    then(application).should().create(client);
  }

  @Test
  public void should_create_client_invalid_email() throws Exception {
    var client = Client.builder().id(null).email("teste").name("NAME TEST").build();
    var json = new ObjectMapper().writeValueAsString(client);

    doThrow(new EmailClientFoundException()).when(application).create(client);

    mockMvc
        .perform(post(CONTEXT_ROOT).content(json).contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message").value("E-mail already registered!"))
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        .andReturn();

    then(application).should().create(client);
  }

  @Test
  public void should_delete_client() throws Exception {
    var id = UUID.randomUUID();

    mockMvc
        .perform(delete(CONTEXT_ROOT + "/{id}", id))
        .andExpect(status().isNoContent())
        .andReturn();

    then(application).should().delete(id);
  }

  @Test
  public void should_not_delete_client() throws Exception {
    var id = UUID.randomUUID();
    var msgError = "client not found!";

    given(application.find(id)).willThrow(new ResourceNotFoundException(msgError));
    mockMvc
        .perform(delete(CONTEXT_ROOT + "/{id}", id))
        .andExpect(status().isNoContent())
        .andReturn();

    then(application).should().delete(id);
  }

  @Test
  public void shuld_update_client() throws Exception {
    var client = Client.builder().id(UUID.randomUUID()).email("teste").name("NAME TEST").build();
    var json = new ObjectMapper().writeValueAsString(client);

    mockMvc
        .perform(
            put(CONTEXT_ROOT + "/{id}", client.getId())
                .content(json)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(status().isOk())
        .andReturn();

    then(application).should().update(client);
  }

  @Test
  public void shuld_not_update_client() throws Exception {
    var client = Client.builder().id(UUID.randomUUID()).email("teste").name("NAME TEST").build();
    var json = new ObjectMapper().writeValueAsString(client);

    doThrow(new EmailClientFoundException()).when(application).update(client);

    mockMvc
        .perform(
            put(CONTEXT_ROOT + "/{id}", client.getId())
                .content(json)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(status().isBadRequest())
        .andReturn();
  }
}
