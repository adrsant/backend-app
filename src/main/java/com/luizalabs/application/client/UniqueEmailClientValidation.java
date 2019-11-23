package com.luizalabs.application.client;

import com.luizalabs.entities.Client;
import com.luizalabs.exception.EmailClientFoundException;
import com.luizalabs.repositories.ClientRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
class UniqueEmailClientValidation implements ClientCustomValidation {

  private ClientRepository repository;

  @Override
  public void check(Client client) {
    if (client.getId() == null && repository.exists(client.getEmail())) {
      throw new EmailClientFoundException();
    }

    if (repository.exists(client.getId(), client.getEmail())) {
      throw new EmailClientFoundException();
    }
  }
}
