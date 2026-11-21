package com.company.loanrecovery.entity;
import jakarta.persistence.*;
import java.time.Instant;
@Entity @Table(name="refresh_tokens", indexes=@Index(name="ix_refresh_hash",columnList="token_hash",unique=true))
public class RefreshToken {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
 private Long id;

 @Column(name="user_id",nullable=false)
 private Long userId;

 @Column(name="token_hash",nullable=false,length=64,unique=true)
 private String tokenHash;

 @Column(nullable=false)
 private Instant expiresAt;

 @Column(nullable=false)
 private boolean revoked=false;
 @Column(nullable=false)
 private Instant createdAt=Instant.now();
 public Long getId(){
  return id;
 }
 public Long getUserId(){
  return userId;
 }
 public void setUserId(Long v) {
  userId=v;
 }
 public String getTokenHash(){
  return tokenHash;
 }
 public void setTokenHash(String v){
  tokenHash=v;
 }
 public Instant getExpiresAt(){
  return expiresAt;
 }
 public void setExpiresAt(Instant v){
  expiresAt=v;
 }
 public boolean isRevoked(){
  return revoked;
 }
 public void setRevoked(boolean v){
  revoked=v;
 }
 public Instant getCreatedAt(){
  return createdAt;
 }
}
