package com.tnr.cityguide.city;
import jakarta.persistence.*;import java.time.Instant;
@Entity @Table(name="cities")
public class City {
 @Id @Column(length=40) private String id;
 @Column(nullable=false,length=100) private String name;
 @Column(nullable=false,length=100) private String country;
 @Column(nullable=false) private double lat;
 @Column(nullable=false) private double lng;
 @Column(nullable=false,length=60) private String timezone;
 @Column(nullable=false) private int hue=200;
 @Column(nullable=false,length=300) private String blurb;
 @Column(nullable=false,columnDefinition="text") private String description;
 @Column(name="image_url",columnDefinition="text") private String imageUrl;
 @Column(name="created_at",nullable=false,updatable=false) private Instant createdAt;
 @Column(name="updated_at",nullable=false) private Instant updatedAt;
 @PrePersist void create(){createdAt=Instant.now();updatedAt=createdAt;}@PreUpdate void update(){updatedAt=Instant.now();}
 public String getId(){return id;}public void setId(String v){id=v;}public String getName(){return name;}public void setName(String v){name=v;}public String getCountry(){return country;}public void setCountry(String v){country=v;}public double getLat(){return lat;}public void setLat(double v){lat=v;}public double getLng(){return lng;}public void setLng(double v){lng=v;}public String getTimezone(){return timezone;}public void setTimezone(String v){timezone=v;}public int getHue(){return hue;}public void setHue(int v){hue=v;}public String getBlurb(){return blurb;}public void setBlurb(String v){blurb=v;}public String getDescription(){return description;}public void setDescription(String v){description=v;}public String getImageUrl(){return imageUrl;}public void setImageUrl(String v){imageUrl=v;}
}
