package com.company.loanrecovery.controller;
import com.company.loanrecovery.common.ApiResponse;
import com.company.loanrecovery.dto.request.*;
import com.company.loanrecovery.dto.response.AuthResponse;
import com.company.loanrecovery.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/auth")
public class AuthController {
 private final AuthService service;
 public AuthController(AuthService service){
  this.service=service;
 }
 @PostMapping("/login")
 public ResponseEntity<ApiResponse<AuthResponse>> login(@Valid @RequestBody LoginRequest r)
 {
  return ResponseEntity.ok(new ApiResponse<>(true,"Login successful",service.login(r)));
 }
 @PostMapping("/refresh")
 public ResponseEntity<ApiResponse<AuthResponse>> refresh(@Valid @RequestBody RefreshRequest r){
  return ResponseEntity.ok(new ApiResponse<>(true,"Token refreshed",service.refresh(r)));
 }
 @PostMapping("/logout")
 public ResponseEntity<ApiResponse<Void>> logout(@Valid @RequestBody RefreshRequest r){
  service.logout(r);return ResponseEntity.ok(new ApiResponse<>(true,"Logged out",null));
 }
}
