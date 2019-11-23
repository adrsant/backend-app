package com.luizalabs.repositories;

import com.luizalabs.entities.Favorite;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteRepository extends JpaRepository<Favorite, UUID> {}
