package com.luizalabs.application.client;

import com.luizalabs.entities.Client;
import com.luizalabs.exception.ResourceNotFoundException;
import com.luizalabs.repositories.ClientRepository;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class ClientApplication {

  private List<ClientCustomValidation> validations;
  private ClientRepository repository;

  public void create(Client client) {
    validations.forEach(v -> v.check(client));
    repository.save(client);
    log.info("CLIENT HAS BEEN CREATED ID {} ", client.getId());
  }

  public void delete(UUID id) {
    if (repository.existsById(id)) {
      throw new ResourceNotFoundException();
    }
    repository.deleteById(id);
    log.info("CLIENT HAS BEEN DELETED ID {}", id);
  }

  public void update(Client client) {
    validations.forEach(v -> v.check(client));
    repository.save(client);
    log.info("SAVE NEW CLIENT");
  }

  public Client find(UUID id) {
    return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException());
  }
}
