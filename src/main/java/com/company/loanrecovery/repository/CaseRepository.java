package com.company.loanrecovery.repository;

import com.company.loanrecovery.entity.LoanCase;
import com.company.loanrecovery.entity.enums.CaseStatus;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CaseRepository
        extends JpaRepository<LoanCase, Long> {

    // Duplicate checks
    boolean existsByTenantIdAndCaseNumber(
            Long tenantId, String caseNumber);

    boolean existsByTenantIdAndLoanAccountNumber(
            Long tenantId, String loanAccountNumber);

    // Pagination
    Page<LoanCase> findAllByTenantIdAndDeletedFalse(
            Long tenantId, Pageable pageable);

    // Filter by status
    Page<LoanCase> findAllByTenantIdAndCaseStatusAndDeletedFalse(
            Long tenantId,
            CaseStatus caseStatus,
            Pageable pageable);

    // Get active case by database ID
    Optional<LoanCase> findByIdAndTenantIdAndDeletedFalse(
            Long id, Long tenantId);

    // Get case including deleted records
    Optional<LoanCase> findByIdAndTenantId(
            Long id, Long tenantId);

    // Find using business case number
    Optional<LoanCase> findByCaseNumberAndTenantIdAndDeletedFalse(
            String caseNumber, Long tenantId);
    long countByTenantId(Long tenantId);
}