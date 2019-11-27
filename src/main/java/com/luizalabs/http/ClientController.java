package com.luizalabs.http;

import com.luizalabs.application.client.ClientApplication;
import com.luizalabs.entities.Client;
import com.luizalabs.http.dto.ClientsDTO;
import com.luizalabs.http.dto.MetaDataDTO;
import java.util.UUID;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Valid
@RequestMapping("api/clients")
public class ClientController {

  private ClientApplication application;

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.CREATED)
  public void create(@RequestBody Client client) {
    application.create(client);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable UUID id) {
    application.delete(id);
  }

  @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
  public void update(@PathVariable UUID id, @RequestBody Client client) {
    client.setId(id);
    application.update(client);
  }

  @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public Client find(@PathVariable UUID id) {
    return application.find(id);
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ClientsDTO findAll(@Min(1) @RequestParam int page) {
    var pageRequest = PageRequest.of(page - 1, 10);
    var clients = application.find(pageRequest);
    return ClientsDTO.builder()
        .meta(
            MetaDataDTO.builder()
                .pageNumber(clients.getNumber() + 1)
                .pageSize(clients.getNumberOfElements())
                .build())
        .clients(clients.getContent())
        .build();
  }
}
