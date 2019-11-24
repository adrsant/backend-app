package com.luizalabs.entities;

import com.luizalabs.entities.pk.FavoritePK;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
@IdClass(FavoritePK.class)
public class Favorite {

  @Id private UUID clientId;
  @Id private UUID productId;
}
