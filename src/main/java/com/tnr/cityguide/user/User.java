package com.tnr.cityguide.user;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name="users")
public class User {
    @Id @GeneratedValue(strategy=GenerationType.UUID) private UUID id;
    @Column(nullable=false,length=100) private String name;
    @Column(nullable=false,length=255) private String email;
    @Column(name="password_hash",nullable=false,length=100) private String passwordHash;
    @Column(nullable=false,length=40) private String phone="";
    @Column(name="avatar_url") private String avatarUrl;
    @Enumerated(EnumType.STRING) @Column(nullable=false,length=10) private Role role=Role.USER;
    @Column(name="notif_events",nullable=false) private boolean notifEvents=true;
    @Column(name="notif_reviews",nullable=false) private boolean notifReviews=true;
    @Column(name="notif_news",nullable=false) private boolean notifNews=false;
    @Column(name="created_at",nullable=false,updatable=false) private Instant createdAt;
    @Column(name="updated_at",nullable=false) private Instant updatedAt;
    @PrePersist void onCreate(){createdAt=Instant.now();updatedAt=createdAt;}
    @PreUpdate void onUpdate(){updatedAt=Instant.now();}
    public UUID getId(){return id;} public void setId(UUID v){id=v;}
    public String getName(){return name;} public void setName(String v){name=v;}
    public String getEmail(){return email;} public void setEmail(String v){email=v;}
    public String getPasswordHash(){return passwordHash;} public void setPasswordHash(String v){passwordHash=v;}
    public String getPhone(){return phone;} public void setPhone(String v){phone=v;}
    public String getAvatarUrl(){return avatarUrl;} public void setAvatarUrl(String v){avatarUrl=v;}
    public Role getRole(){return role;} public void setRole(Role v){role=v;}
    public boolean isNotifEvents(){return notifEvents;} public void setNotifEvents(boolean v){notifEvents=v;}
    public boolean isNotifReviews(){return notifReviews;} public void setNotifReviews(boolean v){notifReviews=v;}
    public boolean isNotifNews(){return notifNews;} public void setNotifNews(boolean v){notifNews=v;}
    public Instant getCreatedAt(){return createdAt;} public Instant getUpdatedAt(){return updatedAt;}
}
