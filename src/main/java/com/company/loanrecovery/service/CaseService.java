
package com.company.loanrecovery.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.company.loanrecovery.dto.request.CaseCreateRequest;
import com.company.loanrecovery.dto.request.CaseUpdateRequest;
import com.company.loanrecovery.dto.response.CaseResponse;
import com.company.loanrecovery.dto.response.PageResponse;

import com.company.loanrecovery.entity.LoanCase;
import com.company.loanrecovery.entity.enums.CaseStatus;

import com.company.loanrecovery.exception.CaseNotFoundException;
import com.company.loanrecovery.exception.DuplicateResourceException;

import com.company.loanrecovery.mapper.CaseMapper;
import com.company.loanrecovery.repository.CaseRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CaseService {

    private static final Logger log =
            LoggerFactory.getLogger(CaseService.class);

    private final CaseRepository caseRepository;
    private final CaseMapper caseMapper;

    public CaseService(
            CaseRepository caseRepository,
            CaseMapper caseMapper) {

        this.caseRepository = caseRepository;
        this.caseMapper = caseMapper;
    }

    // CREATE CASE
    @Transactional
    public CaseResponse createCase(CaseCreateRequest request) {

        Long tenantId = request.getTenantId();

        log.info("Creating case for tenantId: {}", tenantId);

        if (caseRepository.existsByTenantIdAndCaseNumber(
                tenantId, request.getCaseNumber())) {

            log.warn("Duplicate case number for tenantId: {}", tenantId);
            throw new DuplicateResourceException(
                    "Case number already exists");
        }

        if (caseRepository.existsByTenantIdAndLoanAccountNumber(
                tenantId, request.getLoanAccountNumber())) {

            log.warn("Duplicate loan account number for tenantId: {}", tenantId);
            throw new DuplicateResourceException(
                    "Loan account number already exists");
        }

        LoanCase loanCase = caseMapper.toEntity(request);

        LoanCase savedCase = caseRepository.save(loanCase);

        log.info("Case created successfully. Case ID: {}, Tenant ID: {}",
                savedCase.getId(), tenantId);

        return caseMapper.toResponse(savedCase);
    }

    // GET ALL CASES WITH PAGINATION
    @Transactional(readOnly = true)
    public PageResponse<CaseResponse> getAllCases(
            Long tenantId,
            int page,
            int size) {

        log.debug("Fetching cases. Tenant ID: {}, Page: {}, Size: {}",
                tenantId, page, size);

        if (page < 0) {
            throw new IllegalArgumentException(
                    "Page number cannot be negative");
        }

        if (size < 1 || size > 100) {
            throw new IllegalArgumentException(
                    "Page size must be between 1 and 100");
        }

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(Sort.Direction.DESC, "createdAt")
        );

        Page<LoanCase> casePage =
                caseRepository.findAllByTenantIdAndDeletedFalse(
                        tenantId, pageable);

        List<CaseResponse> cases =
                casePage.getContent()
                        .stream()
                        .map(caseMapper::toResponse)
                        .toList();

        log.info("Fetched {} cases for tenantId: {}",
                casePage.getNumberOfElements(), tenantId);

        return new PageResponse<>(
                cases,
                casePage.getNumber(),
                casePage.getSize(),
                casePage.getTotalElements(),
                casePage.getTotalPages(),
                casePage.isFirst(),
                casePage.isLast()
        );
    }

    // GET CASE BY DATABASE ID
    @Transactional(readOnly = true)
    public CaseResponse getCaseById(Long id, Long tenantId) {

        log.debug("Fetching case by ID: {} for tenantId: {}", id, tenantId);

        LoanCase loanCase = findActiveCase(id, tenantId);

        return caseMapper.toResponse(loanCase);
    }

    // GET CASE BY CASE NUMBER
    @Transactional(readOnly = true)
    public CaseResponse getCaseByCaseNumber(
            String caseNumber,
            Long tenantId) {

        log.debug("Fetching case by case number for tenantId: {}", tenantId);

        LoanCase loanCase = caseRepository
                .findByCaseNumberAndTenantIdAndDeletedFalse(
                        caseNumber, tenantId)
                .orElseThrow(() -> {
                    log.warn("Case not found for tenantId: {}", tenantId);
                    return new CaseNotFoundException(
                            "Case not found: " + caseNumber);
                });

        return caseMapper.toResponse(loanCase);
    }

    // UPDATE CASE
    @Transactional
    public CaseResponse updateCase(
            Long id,
            Long tenantId,
            CaseUpdateRequest request) {

        log.info("Updating case ID: {} for tenantId: {}", id, tenantId);

        LoanCase existingCase = findActiveCase(id, tenantId);

        caseMapper.updateEntity(existingCase, request);

        LoanCase updatedCase = caseRepository.save(existingCase);

        log.info("Case updated successfully. ID: {}", updatedCase.getId());

        return caseMapper.toResponse(updatedCase);
    }

    // SOFT DELETE CASE
    @Transactional
    public CaseResponse softDeleteCase(Long id, Long tenantId) {

        log.info("Soft deleting case ID: {} for tenantId: {}", id, tenantId);

        LoanCase loanCase = findActiveCase(id, tenantId);

        loanCase.setDeleted(true);
        loanCase.setDeletedAt(LocalDateTime.now());

        LoanCase deletedCase = caseRepository.save(loanCase);

        log.info("Case soft deleted successfully. ID: {}", id);

        return caseMapper.toResponse(deletedCase);
    }

    // RESTORE CASE
    @Transactional
    public CaseResponse restoreCase(Long id, Long tenantId) {

        log.info("Restoring case ID: {} for tenantId: {}", id, tenantId);

        LoanCase loanCase = caseRepository
                .findByIdAndTenantId(id, tenantId)
                .orElseThrow(() -> {
                    log.warn("Case not found for restore. ID: {}", id);
                    return new CaseNotFoundException(
                            "Case not found with ID: " + id);
                });

        if (!loanCase.isDeleted()) {
            log.warn("Restore rejected: case ID {} is not deleted", id);
            throw new IllegalArgumentException(
                    "Case is not deleted");
        }

        loanCase.setDeleted(false);
        loanCase.setDeletedAt(null);

        LoanCase restoredCase = caseRepository.save(loanCase);

        log.info("Case restored successfully. ID: {}", id);

        return caseMapper.toResponse(restoredCase);
    }

    // FILTER CASES BY STATUS
    @Transactional(readOnly = true)
    public PageResponse<CaseResponse> getCasesByStatus(
            Long tenantId,
            CaseStatus status,
            int page,
            int size) {

        log.debug("Filtering cases. Tenant ID: {}, Status: {}",
                tenantId, status);

        validatePagination(page, size);

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(Sort.Direction.DESC, "createdAt")
        );

        Page<LoanCase> casePage =
                caseRepository
                        .findAllByTenantIdAndCaseStatusAndDeletedFalse(
                                tenantId, status, pageable);

        List<CaseResponse> cases =
                casePage.getContent()
                        .stream()
                        .map(caseMapper::toResponse)
                        .toList();

        log.info("Found {} cases with status {} for tenantId: {}",
                casePage.getNumberOfElements(), status, tenantId);

        return new PageResponse<>(
                cases,
                casePage.getNumber(),
                casePage.getSize(),
                casePage.getTotalElements(),
                casePage.getTotalPages(),
                casePage.isFirst(),
                casePage.isLast()
        );
    }

    // COMMON HELPER
    private LoanCase findActiveCase(Long id, Long tenantId) {

        return caseRepository
                .findByIdAndTenantIdAndDeletedFalse(id, tenantId)
                .orElseThrow(() -> {
                    log.warn("Active case not found. ID: {}, Tenant ID: {}",
                            id, tenantId);
                    return new CaseNotFoundException(
                            "Case not found with ID: " + id);
                });
    }

    private void validatePagination(int page, int size) {

        if (page < 0) {
            throw new IllegalArgumentException(
                    "Page number cannot be negative");
        }

        if (size < 1 || size > 100) {
            throw new IllegalArgumentException(
                    "Page size must be between 1 and 100");
        }
    }
}
