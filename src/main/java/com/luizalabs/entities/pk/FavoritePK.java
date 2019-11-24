package com.luizalabs.entities.pk;

import java.io.Serializable;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class FavoritePK implements Serializable {
   private UUID clientId;
   private UUID productId;
}
