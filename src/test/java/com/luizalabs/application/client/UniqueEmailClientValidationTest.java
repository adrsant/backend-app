package com.luizalabs.application.client;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

import com.luizalabs.entities.Client;
import com.luizalabs.exception.EmailClientFoundException;
import com.luizalabs.repositories.ClientRepository;
import java.util.UUID;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class UniqueEmailClientValidationTest {
  @Mock private ClientRepository repository;
  @InjectMocks private UniqueEmailClientValidation validation;

  @Test
  public void should_check_unique_email_new_client() {
    Client client = Client.builder().id(UUID.randomUUID()).email("teste@teste.com").build();

    given(repository.exists(client.getId(), client.getEmail())).willReturn(false);

    validation.check(client);
  }
  @Test
  public void should_check_unique_email() {
    Client client = Client.builder().email("teste@teste.com").build();

    given(repository.exists(client.getEmail())).willReturn(false);

    validation.check(client);
  }

  @Test
  public void should_check_not_unique_email_new_client() {
    Client client = Client.builder().email("teste@teste.com").build();

    given(repository.exists(client.getEmail())).willReturn(true);

    assertThatThrownBy(() -> validation.check(client))
        .isInstanceOf(EmailClientFoundException.class)
        .hasMessageContaining("E-mail already registered!");
  }

  @Test
  public void should_check_not_unique_email() {
    Client client = Client.builder().id(UUID.randomUUID()).email("teste@teste.com").build();

    given(repository.exists(client.getId(), client.getEmail())).willReturn(true);

    assertThatThrownBy(() -> validation.check(client))
        .isInstanceOf(EmailClientFoundException.class)
        .hasMessageContaining("E-mail already registered!");
  }
}
