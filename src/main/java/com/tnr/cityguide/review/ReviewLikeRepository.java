package com.tnr.cityguide.review;
import java.util.UUID;import org.springframework.data.jpa.repository.JpaRepository;
public interface ReviewLikeRepository extends JpaRepository<ReviewLike,ReviewLike.Key>{long countByReviewId(UUID reviewId);boolean existsByReviewIdAndUserId(UUID reviewId,UUID userId);}
