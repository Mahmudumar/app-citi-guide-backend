package com.tnr.cityguide.place;

import jakarta.persistence.*;
import java.time.Instant;
import java.time.LocalTime;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity @Table(name="places")
public class Place {
 @Id @Column(length=40) private String id;
 @Column(name="city_id",nullable=false,length=40) private String cityId;
 @Column(nullable=false,length=20) private String category;
 @Column(nullable=false,length=150) private String name;
 @Column(nullable=false,columnDefinition="text") private String description;
 @Column(nullable=false,length=255) private String address;
 @Column(nullable=false,length=40) private String phone="";
 @Column(nullable=false,length=255) private String website="";
 @Column(nullable=false) private double lat;
 @Column(nullable=false) private double lng;
 @Column(name="opens_at") private LocalTime opensAt;
 @Column(name="closes_at") private LocalTime closesAt;
 @Column(name="open_24h",nullable=false) private boolean open24h;
 @JdbcTypeCode(SqlTypes.ARRAY) @Column(name="closed_days",columnDefinition="smallint[]") private short[] closedDays;
 @Column(name="hours_text",nullable=false,length=120) private String hoursText;
 @Column(nullable=false,length=120) private String extra="";
 @Column(name="base_rating",nullable=false) private double baseRating;
 @Column(name="rating_avg",nullable=false) private double ratingAvg;
 @Column(name="review_count",nullable=false) private int reviewCount;
 @Column(name="created_at",nullable=false,updatable=false) private Instant createdAt;
 @Column(name="updated_at",nullable=false) private Instant updatedAt;
 @PrePersist void create(){createdAt=Instant.now();updatedAt=createdAt;} @PreUpdate void update(){updatedAt=Instant.now();}
 public String getId(){return id;} public void setId(String v){id=v;}
 public String getCityId(){return cityId;} public void setCityId(String v){cityId=v;}
 public String getCategory(){return category;} public void setCategory(String v){category=v;}
 public String getName(){return name;} public void setName(String v){name=v;}
 public String getDescription(){return description;} public void setDescription(String v){description=v;}
 public String getAddress(){return address;} public void setAddress(String v){address=v;}
 public String getPhone(){return phone;} public void setPhone(String v){phone=v;}
 public String getWebsite(){return website;} public void setWebsite(String v){website=v;}
 public double getLat(){return lat;} public void setLat(double v){lat=v;}
 public double getLng(){return lng;} public void setLng(double v){lng=v;}
 public LocalTime getOpensAt(){return opensAt;} public void setOpensAt(LocalTime v){opensAt=v;}
 public LocalTime getClosesAt(){return closesAt;} public void setClosesAt(LocalTime v){closesAt=v;}
 public boolean isOpen24h(){return open24h;} public void setOpen24h(boolean v){open24h=v;}
 public short[] getClosedDays(){return closedDays;} public void setClosedDays(short[] v){closedDays=v;}
 public String getHoursText(){return hoursText;} public void setHoursText(String v){hoursText=v;}
 public String getExtra(){return extra;} public void setExtra(String v){extra=v;}
 public double getBaseRating(){return baseRating;} public void setBaseRating(double v){baseRating=v;}
 public double getRatingAvg(){return ratingAvg;} public void setRatingAvg(double v){ratingAvg=v;}
 public int getReviewCount(){return reviewCount;} public void setReviewCount(int v){reviewCount=v;}
}
