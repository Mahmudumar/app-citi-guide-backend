package com.tnr.cityguide.auth;
import com.tnr.cityguide.user.User;
import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;
@Entity @Table(name="password_reset_tokens")
public class PasswordResetToken {
 @Id @GeneratedValue(strategy=GenerationType.UUID) private UUID id;
 @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="user_id",nullable=false) private User user;
 @Column(name="token_hash",nullable=false,length=64) private String tokenHash;
 @Column(name="expires_at",nullable=false) private Instant expiresAt;
 @Column(name="used_at") private Instant usedAt;
 @Column(nullable=false) private int attempts;
 @Column(name="created_at",nullable=false) private Instant createdAt;
 @PrePersist void create(){createdAt=Instant.now();}
 public UUID getId(){return id;} public User getUser(){return user;} public void setUser(User v){user=v;}
 public String getTokenHash(){return tokenHash;} public void setTokenHash(String v){tokenHash=v;}
 public Instant getExpiresAt(){return expiresAt;} public void setExpiresAt(Instant v){expiresAt=v;}
 public Instant getUsedAt(){return usedAt;} public void setUsedAt(Instant v){usedAt=v;}
 public int getAttempts(){return attempts;} public void setAttempts(int v){attempts=v;}
}
