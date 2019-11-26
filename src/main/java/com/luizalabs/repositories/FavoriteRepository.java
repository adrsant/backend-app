package com.luizalabs.repositories;

import com.luizalabs.entities.Favorite;
import com.luizalabs.entities.pk.FavoritePK;
import java.util.Collection;
import java.util.UUID;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FavoriteRepository extends JpaRepository<Favorite, FavoritePK> {

  @Query("select f from Favorite f where f.clientId = :clientId")
  Slice<Favorite> findByClientId(UUID clientId, Pageable pageable);

}
