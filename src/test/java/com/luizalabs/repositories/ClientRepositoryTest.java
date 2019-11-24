package com.luizalabs.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import com.luizalabs.test.AbstractTestRepository;
import java.util.UUID;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

@Sql("/scenario-sql/client-created.sql")
public class ClientRepositoryTest extends AbstractTestRepository {

  @Autowired private ClientRepository repository;

  @Test
  public void should_exist_new_client_registered() {
    assertThat(repository.exists("teste@gmail.com")).isTrue();
  }

  @Test
  public void should_not_exist_new_client_registered() {
    assertThat(repository.exists("teste-not@gmail.com")).isFalse();
  }

  @Test
  public void should_find_email_registered() {
    assertThat(
            repository.exists(
                UUID.fromString("1ba100b0-d568-4bb9-838e-c8139852cc5c"), "teste@gmail.com"))
        .isFalse();
  }

  @Test
  public void should_not_exist_client_registered() {
    assertThat(
            repository.exists(
                UUID.fromString("1ba100b0-d568-4bb9-838e-c8139852cc5c"), "teste-not@gmail.com"))
        .isFalse();
  }

  @Test
  public void should_exist_client_registered() {
    assertThat(
            repository.exists(
                UUID.fromString("1ba100b0-d568-4bb9-838e-c8139852cc5c"), "teste1@gmail.com"))
        .isTrue();
  }
}
