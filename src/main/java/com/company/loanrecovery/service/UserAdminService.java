package com.company.loanrecovery.service;
import com.company.loanrecovery.dto.request.UserCreateRequest;
import com.company.loanrecovery.entity.UserAccount;
import com.company.loanrecovery.repository.UserAccountRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Locale;
import java.util.Set;
@Service
public class UserAdminService {
 private static final Set<String> ROLES=Set.of("ROLE_SUPER_ADMIN","ROLE_ADMIN","ROLE_MANAGER","ROLE_TEAMLEAD","ROLE_AGENT","ROLE_TELECALLER");
 private final UserAccountRepository repo; private final PasswordEncoder encoder;
 public UserAdminService(UserAccountRepository repo,PasswordEncoder encoder){this.repo=repo;this.encoder=encoder;}
 @Transactional public UserAccount create(UserCreateRequest r){
  String username=r.username().trim().toLowerCase(Locale.ROOT);
  if(repo.existsByTenantIdAndUsername(r.tenantId(),username)) throw new IllegalArgumentException("Username already exists for this tenant");
  String role=r.role().trim().toUpperCase(Locale.ROOT); if(!role.startsWith("ROLE_")) role="ROLE_"+role;
  if(!ROLES.contains(role)) throw new IllegalArgumentException("Unsupported role");
  UserAccount u=new UserAccount();u.setTenantId(r.tenantId());u.setUsername(username);u.setPasswordHash(encoder.encode(r.password()));u.setRole(role);u.setEmployeeId(r.employeeId());return repo.save(u);
 }
}
