package com.luizalabs.http.dto;

import com.luizalabs.entities.Client;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ClientsDTO {

  private MetaDataDTO meta;
  private List<Client> clients;
}
