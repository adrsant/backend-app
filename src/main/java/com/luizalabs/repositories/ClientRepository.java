package com.luizalabs.repositories;

import com.luizalabs.entities.Client;
import com.luizalabs.entities.Product;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, UUID> {}
