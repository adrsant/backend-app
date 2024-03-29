package com.luizalabs.application.client;

import com.luizalabs.entities.Client;
import com.luizalabs.exception.ResourceNotFoundException;
import com.luizalabs.repositories.ClientRepository;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class ClientApplication {

  private List<ClientCustomValidation> validations;
  private ClientRepository repository;

  @PreAuthorize("hasAuthority('ROLE_ADMIN')")
  public void create(Client client) {
    validations.forEach(v -> v.check(client));
    repository.save(client);
    log.info("CLIENT HAS BEEN CREATED ID {} ", client.getId());
  }

  @PreAuthorize("hasAuthority('ROLE_ADMIN')")
  public void delete(UUID id) {
    if (!repository.existsById(id)) {
      throw new ResourceNotFoundException("client not found!");
    }
    repository.deleteById(id);
    log.info("CLIENT HAS BEEN DELETED ID {}", id);
  }

  @PreAuthorize("hasAuthority('ROLE_ADMIN')")
  public void update(Client client) {
    validations.forEach(v -> v.check(client));
    repository.save(client);
    log.info("SCLIENT HAS BEEN UPDATED ID {}", client.getId());
  }

  public Client find(UUID id) {
    return repository
        .findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("client not found!"));
  }

  public Slice<Client> find(Pageable pageable) {
    return repository
        .findAll(pageable);
  }
}
