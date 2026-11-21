package com.company.loanrecovery.repository;

import com.company.loanrecovery.entity.Employee;
import com.company.loanrecovery.entity.enums.EmployeeStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeRepository
        extends JpaRepository<Employee, Long> {

    boolean existsByTenantIdAndEmpId(Long tenantId, String empId);

    boolean existsByTenantIdAndEmail(Long tenantId, String email);

    boolean existsByTenantIdAndMobile(Long tenantId, String mobile);

    boolean existsByTenantIdAndEmailAndIdNot(
            Long tenantId, String email, Long id);

    boolean existsByTenantIdAndMobileAndIdNot(
            Long tenantId, String mobile, Long id);

    Page<Employee> findAllByTenantIdAndUserStatusNot(
            Long tenantId,
            EmployeeStatus status,
            Pageable pageable
    );

    Optional<Employee> findByIdAndTenantIdAndUserStatusNot(
            Long id,
            Long tenantId,
            EmployeeStatus status
    );

    Optional<Employee> findByIdAndTenantId(
            Long id,
            Long tenantId
    );
    Optional<Employee> findByIdAndTenantIdAndUserStatus(
            Long id,
            Long tenantId,
            EmployeeStatus userStatus
    );
    long countByTenantId(Long tenantId);
}