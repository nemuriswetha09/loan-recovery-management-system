package com.company.loanrecovery.repository;
import com.company.loanrecovery.entity.Resolution;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
public interface ResolutionRepository extends JpaRepository<Resolution,Long> {
  List<Resolution> findAllByTenantIdOrderByCreatedAtDesc(Long tenantId);
  Optional<Resolution> findByIdAndTenantId(Long id,Long tenantId);
  long countByTenantId(Long tenantId);
}
