package com.luizalabs.http.dto;

import java.util.List;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class FavoritesDTO {

  private MetaDataDTO meta;
  private List<UUID> products;
}
