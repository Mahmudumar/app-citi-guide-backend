package com.tnr.cityguide.review;
import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;
public interface ReviewRepository extends JpaRepository<Review,UUID> {
 List<Review> findByPlaceIdOrderByCreatedAtDesc(String placeId);
 List<Review> findByUserIdOrderByCreatedAtDesc(UUID userId);
 Optional<Review> findByPlaceIdAndUserId(String placeId,UUID userId);
 long countByPlaceId(String placeId);
}
