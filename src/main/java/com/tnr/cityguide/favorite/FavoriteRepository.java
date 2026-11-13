package com.tnr.cityguide.favorite;
import java.util.*;import org.springframework.data.jpa.repository.JpaRepository;
public interface FavoriteRepository extends JpaRepository<Favorite,Favorite.Key>{List<Favorite> findByUserIdOrderByCreatedAtDesc(UUID userId);}
