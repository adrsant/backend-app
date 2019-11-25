package com.luizalabs.application.client;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import com.luizalabs.entities.Client;
import com.luizalabs.exception.ResourceNotFoundException;
import com.luizalabs.repositories.ClientRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ClientApplicationTest {
  @Mock private ClientCustomValidation customValidation;
  @Mock private ClientRepository repository;
  private ClientApplication application;

  @Before
  public void init() {
    this.application = new ClientApplication(List.of(customValidation), repository);
  }

  @Test
  public void should_create_client() {
    var client = Client.builder().email("teste@teste.com").build();
    ArgumentCaptor<Client> argCaptor = ArgumentCaptor.forClass(Client.class);
    doNothing().when(customValidation).check(client);
    application.create(client);
    verify(customValidation).check(argCaptor.capture());

    then(repository).should().save(client);
  }

  @Test
  public void should_not_create_client() {
    var client = Client.builder().email("teste@teste.com").build();
    doThrow(RuntimeException.class).when(customValidation).check(client);

    assertThatThrownBy(() -> application.create(client)).isInstanceOf(RuntimeException.class);

    then(repository).should(never()).save(client);
  }

  @Test
  public void should_delete_client() {
    UUID clientId = UUID.randomUUID();
    given(repository.existsById(clientId)).willReturn(true);
    application.delete(clientId);

    then(repository).should().deleteById(clientId);
  }

  @Test
  public void should_not_delete_client() {
    UUID clientId = UUID.randomUUID();
    given(repository.existsById(clientId)).willReturn(false);

    assertThatThrownBy(() -> application.delete(clientId))
        .isInstanceOf(ResourceNotFoundException.class);

    then(repository).should(never()).deleteById(clientId);
  }

  @Test
  public void should_update_client() {
    var client = Client.builder().email("teste@teste.com").build();
    ArgumentCaptor<Client> argCaptor = ArgumentCaptor.forClass(Client.class);

    application.create(client);
    verify(customValidation).check(argCaptor.capture());

    then(repository).should().save(client);
  }

  @Test
  public void should_find_client() {
    var client = Client.builder().id(UUID.randomUUID()).email("teste@teste.com").build();
    given(repository.findById(client.getId())).willReturn(Optional.of(client));

    Client result = application.find(client.getId());

    assertThat(result).isEqualTo(client);
    then(repository).should().findById(client.getId());
  }

  @Test
  public void should_not_find_client() {
    UUID clientId = UUID.randomUUID();
    given(repository.findById(clientId)).willReturn(Optional.empty());

    assertThatThrownBy(() -> application.find(clientId))
        .isInstanceOf(ResourceNotFoundException.class);

    then(repository).should().findById(clientId);
  }
}
