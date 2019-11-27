package com.luizalabs.http.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MetaDataDTO {

  @JsonProperty("page_size")
  private int pageSize;
  @JsonProperty("page_number")
  private int pageNumber;
}
