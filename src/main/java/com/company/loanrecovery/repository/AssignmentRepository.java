package com.company.loanrecovery.repository;

import com.company.loanrecovery.entity.Assignment;
import com.company.loanrecovery.entity.enums.AssignmentRole;
import com.company.loanrecovery.entity.enums.AssignmentStatus;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AssignmentRepository
        extends JpaRepository<Assignment, Long> {

    Page<Assignment> findAllByTenantId(
            Long tenantId,
            Pageable pageable
    );

    Optional<Assignment> findByIdAndTenantId(
            Long id,
            Long tenantId
    );

    Page<Assignment> findAllByTenantIdAndLoanCase_Id(
            Long tenantId,
            Long caseId,
            Pageable pageable
    );

    Page<Assignment> findAllByTenantIdAndEmployee_Id(
            Long tenantId,
            Long employeeId,
            Pageable pageable
    );

    Page<Assignment> findAllByTenantIdAndAssignmentStatus(
            Long tenantId,
            AssignmentStatus status,
            Pageable pageable
    );

    boolean existsByTenantIdAndLoanCase_IdAndAssignmentRoleAndAssignmentStatus(
            Long tenantId,
            Long caseId,
            AssignmentRole role,
            AssignmentStatus status
    );

    Optional<Assignment> findByTenantIdAndLoanCase_IdAndAssignmentRoleAndAssignmentStatus(
            Long tenantId,
            Long caseId,
            AssignmentRole role,
            AssignmentStatus status
    );
    long countByTenantId(Long tenantId);
}