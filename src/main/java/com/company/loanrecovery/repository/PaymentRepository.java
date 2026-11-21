package com.company.loanrecovery.repository;
import com.company.loanrecovery.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
public interface PaymentRepository extends JpaRepository<Payment,Long> {
  List<Payment> findAllByTenantIdOrderByCreatedAtDesc(Long tenantId);
  Optional<Payment> findByIdAndTenantId(Long id,Long tenantId);
  long countByTenantId(Long tenantId);
}
