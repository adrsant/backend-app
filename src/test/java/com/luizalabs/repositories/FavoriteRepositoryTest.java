package com.luizalabs.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import com.luizalabs.test.AbstractTestRepository;
import java.util.UUID;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class FavoriteRepositoryTest extends AbstractTestRepository {

  @Autowired private FavoriteRepository repository;

  @Test
  public void shoud_find_favorites() {
    UUID id = UUID.fromString("1ba100b0-d568-4bb9-838e-c8139852cc5c");
    assertThat(repository.findByClientId(id)).isEmpty();
  }

  @Test
  public void shoud_not_find_favorites() {
    UUID id = UUID.fromString("1bf0f365-fbdd-4e21-9786-da459d78dd1f");
    assertThat(repository.findByClientId(id)).isEmpty();
  }
}
