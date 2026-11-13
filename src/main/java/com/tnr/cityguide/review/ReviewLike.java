package com.tnr.cityguide.review;
import jakarta.persistence.*;import java.io.Serializable;import java.time.Instant;import java.util.UUID;
@Entity @Table(name="review_likes")
@IdClass(ReviewLike.Key.class)
public class ReviewLike {
 @Id @Column(name="review_id") private UUID reviewId;
 @Id @Column(name="user_id") private UUID userId;
 @Column(name="created_at",nullable=false) private Instant createdAt;
 @PrePersist void create(){createdAt=Instant.now();}
 public UUID getReviewId(){return reviewId;}public void setReviewId(UUID v){reviewId=v;}public UUID getUserId(){return userId;}public void setUserId(UUID v){userId=v;}
 public static class Key implements Serializable {private UUID reviewId;private UUID userId;public Key(){}public Key(UUID r,UUID u){reviewId=r;userId=u;}public UUID getReviewId(){return reviewId;}public void setReviewId(UUID v){reviewId=v;}public UUID getUserId(){return userId;}public void setUserId(UUID v){userId=v;}public boolean equals(Object o){return o instanceof Key k&&java.util.Objects.equals(reviewId,k.reviewId)&&java.util.Objects.equals(userId,k.userId);}public int hashCode(){return java.util.Objects.hash(reviewId,userId);}}
}
