package com.luizalabs.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import com.luizalabs.test.AbstractTestRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ProductRepositoryTest extends AbstractTestRepository {

  @Autowired private ProductRepository repository;

  @Test
  public void testStartup(){
    assertThat(repository.findAll()).isEmpty();
  }


}