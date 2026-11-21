package com.company.loanrecovery.service;
import com.company.loanrecovery.dto.request.*;
import com.company.loanrecovery.dto.response.AuthResponse;
import com.company.loanrecovery.entity.*;
import com.company.loanrecovery.repository.*;
import com.company.loanrecovery.security.JwtService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.time.Instant;
import java.util.Base64;
import java.util.HexFormat;

@Service
public class AuthService {
 private final UserAccountRepository users;
 private final RefreshTokenRepository tokens;
 private final PasswordEncoder encoder;
 private final JwtService jwt;
 private final long refreshTtl;
 public AuthService(UserAccountRepository users,RefreshTokenRepository tokens,PasswordEncoder encoder,JwtService jwt,@Value("${app.jwt.refresh-ttl-seconds:2592000}") long refreshTtl){
  this.users=users;
  this.tokens=tokens;
  this.encoder=encoder;
  this.jwt=jwt;
  this.refreshTtl=refreshTtl;
 }
 @Transactional public AuthResponse login(LoginRequest r){
  UserAccount u=users.findByTenantIdAndUsername(r.tenantId(),r.username()).orElseThrow(()->new BadCredentialsException("Invalid username or password"));
  if(!u.isEnabled()||!encoder.matches(r.password(),u.getPasswordHash())) throw new BadCredentialsException("Invalid username or password");
  return issue(u);
 }
 @Transactional public AuthResponse refresh(RefreshRequest r){
  String hash=sha256(r.refreshToken());
  RefreshToken old=tokens.findByTokenHash(hash).orElseThrow(()->new BadCredentialsException("Invalid refresh token"));
  if(old.isRevoked()||!old.getExpiresAt().isAfter(Instant.now())) throw new BadCredentialsException("Refresh token expired or revoked");
  old.setRevoked(true);
  UserAccount u=users.findById(old.getUserId()).filter(UserAccount::isEnabled).orElseThrow(()->new BadCredentialsException("User disabled"));
  return issue(u);
 }
 @Transactional public void logout(RefreshRequest r){
  tokens.findByTokenHash(sha256(r.refreshToken())).ifPresent(t->t.setRevoked(true));
 }
 private AuthResponse issue(UserAccount u){
  String raw=randomToken();
  RefreshToken t=new RefreshToken();
  t.setUserId(u.getId());
  t.setTokenHash(sha256(raw));
  t.setExpiresAt(Instant.now().plusSeconds(refreshTtl));tokens.save(t);
  return new AuthResponse(jwt.create(u),raw,"Bearer",jwt.getTtl(),u.getId(),u.getTenantId(),u.getUsername(),u.getRole());
 }
 private String randomToken(){
  byte[] b=new byte[48];
  new SecureRandom().nextBytes(b);
  return Base64.getUrlEncoder().withoutPadding().encodeToString(b);}
 private String sha256(String s)
 {
  try
 {
  return HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(s.getBytes(StandardCharsets.UTF_8)));
 }catch(NoSuchAlgorithmException e){
   throw new IllegalStateException(e);}
 }
}
