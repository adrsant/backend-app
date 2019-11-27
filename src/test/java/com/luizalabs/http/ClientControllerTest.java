package com.luizalabs.http;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.luizalabs.application.client.ClientApplication;
import com.luizalabs.entities.Client;
import java.util.UUID;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(ClientController.class)
public class ClientControllerTest {

  private static final Long TENANT_ID = 1L;
  private static final String CONTEXT_ROOT = "/api/clients";
  @Autowired private MockMvc mockMvc;
  @MockBean private ClientApplication application;

  @Test
  @Ignore
  public void should_find_client() throws Exception {
    Client client = Client.builder().email("teste").id(UUID.randomUUID()).build();

    given(application.find(client.getId())).willReturn(client);
    mockMvc
        .perform(get(CONTEXT_ROOT + "/{id}", client))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        .andReturn();

    then(application).should().find(client.getId());
  }

  @Test
  public void create() {}

  @Test
  public void delete() {}

  @Test
  public void update() {}

  @Test
  public void find() {}

  @Test
  public void findAll() {}
}
