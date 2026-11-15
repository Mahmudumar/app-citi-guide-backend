package com.tnr.cityguide.favorite;
import jakarta.persistence.*;import java.io.Serializable;import java.time.Instant;import java.util.UUID;
@Entity @Table(name="favorites") @IdClass(Favorite.Key.class)
public class Favorite {
 @Id @Column(name="user_id") private UUID userId;
 @Id @Column(name="place_id",length=40) private String placeId;
 @Column(name="created_at",nullable=false) private Instant createdAt;
 @PrePersist void create(){createdAt=Instant.now();}
 public UUID getUserId(){return userId;}public void setUserId(UUID v){userId=v;}public String getPlaceId(){return placeId;}public void setPlaceId(String v){placeId=v;}public Instant getCreatedAt(){return createdAt;}
 public static class Key implements Serializable {private UUID userId;private String placeId;public Key(){}public Key(UUID u,String p){userId=u;placeId=p;}public UUID getUserId(){return userId;}public void setUserId(UUID v){userId=v;}public String getPlaceId(){return placeId;}public void setPlaceId(String v){placeId=v;}public boolean equals(Object o){return o instanceof Key k&&java.util.Objects.equals(userId,k.userId)&&java.util.Objects.equals(placeId,k.placeId);}public int hashCode(){return java.util.Objects.hash(userId,placeId);}}
}
