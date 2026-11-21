package com.company.loanrecovery.repository;
import com.company.loanrecovery.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Long>{
 Optional<RefreshToken> findByTokenHash(String tokenHash);
 void deleteAllByUserId(Long userId);
}
