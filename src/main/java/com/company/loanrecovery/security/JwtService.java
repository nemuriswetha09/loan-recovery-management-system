package com.company.loanrecovery.security;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.company.loanrecovery.entity.UserAccount;
@Service
public class JwtService {
 private final SecretKey key; private final long ttl;
 public JwtService(@Value("${app.jwt.secret}") String secret,@Value("${app.jwt.access-ttl-seconds:900}") long ttl){
  if(secret.getBytes(StandardCharsets.UTF_8).length<32) throw new IllegalArgumentException("app.jwt.secret must be at least 32 UTF-8 bytes");
  this.key=Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)); this.ttl=ttl;
 }
 public String create(UserAccount u){
  Instant now=Instant.now();
  return Jwts.builder().subject(u.getUsername()).claim("uid",u.getId()).claim("tenantId",u.getTenantId()).claim("role",u.getRole())
   .issuedAt(Date.from(now)).expiration(Date.from(now.plusSeconds(ttl))).signWith(key).compact();
 }
 public io.jsonwebtoken.Claims parse(String token){
  return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();}
 public long getTtl(){
  return ttl;
 }
}
