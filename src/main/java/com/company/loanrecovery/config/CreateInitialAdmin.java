package com.company.loanrecovery.config;
import com.company.loanrecovery.entity.UserAccount;
import com.company.loanrecovery.repository.UserAccountRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;
@Configuration
public class CreateInitialAdmin {
 @Bean CommandLineRunner bootstrapAdmin(UserAccountRepository repo,PasswordEncoder encoder,
  @Value("${app.bootstrap.tenant-id:0}") Long tenant,
  @Value("${app.bootstrap.username:}") String username,
  @Value("${app.bootstrap.password:}") String password){
  return args->{if(tenant>0&&!username.isBlank()&&!password.isBlank()&&!repo.existsByTenantIdAndUsername(tenant,username)){
   UserAccount u=new UserAccount();u.setTenantId(tenant);u.setUsername(username.trim().toLowerCase());u.setPasswordHash(encoder.encode(password));u.setRole("ROLE_SUPER_ADMIN");repo.save(u);
  }};
 }
}
