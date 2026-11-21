package com.company.loanrecovery.repository;
import com.company.loanrecovery.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
public interface UserAccountRepository extends JpaRepository<UserAccount,Long>{
 Optional<UserAccount> findByTenantIdAndUsername(Long tenantId,String username);
 boolean existsByTenantIdAndUsername(Long tenantId,String username);
}
