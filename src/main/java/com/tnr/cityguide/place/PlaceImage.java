package com.tnr.cityguide.place;
import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;
@Entity @Table(name="place_images")
public class PlaceImage {
 @Id @GeneratedValue(strategy=GenerationType.UUID) private UUID id;
 @Column(name="place_id",nullable=false,length=40) private String placeId;
 @Column(nullable=false,columnDefinition="text") private String url;
 @Column(name="sort_order",nullable=false) private int sortOrder;
 @Column(name="created_at",nullable=false) private Instant createdAt;
 @PrePersist void create(){createdAt=Instant.now();}
 public UUID getId(){return id;} public String getPlaceId(){return placeId;} public void setPlaceId(String v){placeId=v;}
 public String getUrl(){return url;} public void setUrl(String v){url=v;} public int getSortOrder(){return sortOrder;} public void setSortOrder(int v){sortOrder=v;}
}
