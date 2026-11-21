package com.company.loanrecovery.controller;
import com.company.loanrecovery.common.ApiResponse;
import com.company.loanrecovery.dto.request.UserCreateRequest;
import com.company.loanrecovery.entity.UserAccount;
import com.company.loanrecovery.service.UserAdminService;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/users")
public class UserAdminController {
 private final UserAdminService service; public UserAdminController(UserAdminService service){this.service=service;}
 @PostMapping @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN')")
 public ResponseEntity<ApiResponse<UserCreatedResponse>> create(@Valid @RequestBody UserCreateRequest r){
  UserAccount u=service.create(r);return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(true,"User created",new UserCreatedResponse(u.getId(),u.getTenantId(),u.getUsername(),u.getRole(),u.getEmployeeId(),u.isEnabled())));
 }
 public record UserCreatedResponse(Long id,Long tenantId,String username,String role,Long employeeId,boolean enabled){}
}
