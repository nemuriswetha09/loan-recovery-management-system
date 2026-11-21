package com.company.loanrecovery.repository;
import com.company.loanrecovery.entity.Visit;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
public interface VisitRepository extends JpaRepository<Visit,Long> {
  List<Visit> findAllByTenantIdOrderByCreatedAtDesc(Long tenantId);
  Optional<Visit> findByIdAndTenantId(Long id,Long tenantId);
  long countByTenantId(Long tenantId);
}
