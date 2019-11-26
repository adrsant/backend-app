package com.luizalabs.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import com.luizalabs.test.AbstractTestRepository;
import java.util.UUID;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

public class FavoriteRepositoryTest extends AbstractTestRepository {

  @Autowired private FavoriteRepository repository;

  @Test
  public void shoud_find_favorites() {
    UUID id = UUID.fromString("1ba100b0-d568-4bb9-838e-c8139852cc5c");
    var pageRequest = PageRequest.of(1, 5);
    assertThat(repository.findByClientId(id, pageRequest)).isEmpty();
  }

  @Test
  public void shoud_not_find_favorites() {
    var pageRequest = PageRequest.of(1, 5);
    UUID id = UUID.fromString("1bf0f365-fbdd-4e21-9786-da459d78dd1f");
    assertThat(repository.findByClientId(id, pageRequest)).isEmpty();
  }
}
