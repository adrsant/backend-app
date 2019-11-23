package com.luizalabs.repositories;

import com.luizalabs.entities.Client;
import com.luizalabs.entities.Product;
import java.util.Collection;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ClientRepository extends JpaRepository<Client, UUID> {

  @Query("select (count(c) > 0) from Client c where c.email = :email")
  boolean exists(String email);

  @Query("select products from Client c where c.id = :clientId")
  Collection<Product> findProductsOfClient(UUID clientId);
}
