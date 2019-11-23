package com.luizalabs.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import com.luizalabs.test.AbstractTestRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class FavoriteRepositoryTest extends AbstractTestRepository {

  @Autowired private FavoriteRepository repository;

  @Test
  public void testStartup() {
    assertThat(repository.findAll()).isEmpty();
  }
}
