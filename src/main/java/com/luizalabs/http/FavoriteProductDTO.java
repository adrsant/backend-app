package com.luizalabs.http;

import java.util.UUID;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@Getter
public class FavoriteProductDTO {

  @NotNull private UUID clientId;
  @NotNull private UUID productId;
}
