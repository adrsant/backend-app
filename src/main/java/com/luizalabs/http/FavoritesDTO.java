package com.luizalabs.http;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class FavoritesDTO {

  private MetaData meta;
  private List<UUID> products;
}

@Builder
@Getter
class MetaData {

  @JsonProperty("page_size")
  private int pageSize;
  @JsonProperty("page_number")
  private int pageNumber;
}