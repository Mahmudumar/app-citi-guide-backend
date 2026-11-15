package com.tnr.cityguide.review;
import jakarta.persistence.*;import java.time.Instant;import java.util.UUID;
@Entity @Table(name="reviews",uniqueConstraints=@UniqueConstraint(name="uq_reviews_place_user",columnNames={"place_id","user_id"}))
public class Review {
 @Id @GeneratedValue(strategy=GenerationType.UUID) private UUID id;
 @Column(name="place_id",nullable=false,length=40) private String placeId;
 @Column(name="user_id",nullable=false) private UUID userId;
 @Column(nullable=false) private short rating;
 @Column(nullable=false,length=600) private String text;
 @Column(name="created_at",nullable=false) private Instant createdAt;
 @Column(name="updated_at",nullable=false) private Instant updatedAt;
 @PrePersist void create(){if(createdAt==null)createdAt=Instant.now();updatedAt=createdAt;} @PreUpdate void update(){updatedAt=Instant.now();}
 public UUID getId(){return id;}public void setId(UUID v){id=v;}public String getPlaceId(){return placeId;}public void setPlaceId(String v){placeId=v;}public UUID getUserId(){return userId;}public void setUserId(UUID v){userId=v;}public short getRating(){return rating;}public void setRating(short v){rating=v;}public String getText(){return text;}public void setText(String v){text=v;}public Instant getCreatedAt(){return createdAt;}public void setCreatedAt(Instant v){createdAt=v;}
}
