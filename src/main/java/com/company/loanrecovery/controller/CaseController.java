
package com.company.loanrecovery.controller;
import org.springframework.security.access.prepost.PreAuthorize;
import com.company.loanrecovery.common.ApiResponse;

import com.company.loanrecovery.dto.request.CaseCreateRequest;
import com.company.loanrecovery.dto.request.CaseUpdateRequest;

import com.company.loanrecovery.dto.response.CaseResponse;
import com.company.loanrecovery.dto.response.PageResponse;

import com.company.loanrecovery.entity.enums.CaseStatus;

import com.company.loanrecovery.service.CaseService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

@RestController
@PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'TEAM_LEAD', 'AGENT')")
@RequestMapping("/api/cases")
public class CaseController {

    private final CaseService caseService;

    public CaseController(CaseService caseService) {
        this.caseService = caseService;
    }

    // CREATE CASE
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'TEAM_LEAD')")
    @PostMapping
    public ResponseEntity<ApiResponse<CaseResponse>> createCase(
            @Valid @RequestBody CaseCreateRequest request) {

        CaseResponse loanCase = caseService.createCase(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(
                        true,
                        "Case created successfully",
                        loanCase
                ));
    }

    // GET ALL CASES

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<CaseResponse>>>
    getAllCases(
            @RequestParam Long tenantId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        PageResponse<CaseResponse> cases =
                caseService.getAllCases(tenantId, page, size);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Cases fetched successfully",
                        cases
                )
        );
    }

    // GET CASE BY DATABASE ID

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CaseResponse>> getCaseById(
            @PathVariable Long id,
            @RequestParam Long tenantId) {

        CaseResponse loanCase =
                caseService.getCaseById(id, tenantId);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Case fetched successfully",
                        loanCase
                )
        );
    }

    // GET CASE BY BUSINESS CASE NUMBER

    @GetMapping("/by-case-number/{caseNumber}")
    public ResponseEntity<ApiResponse<CaseResponse>>
    getCaseByCaseNumber(
            @PathVariable String caseNumber,
            @RequestParam Long tenantId) {

        CaseResponse loanCase =
                caseService.getCaseByCaseNumber(
                        caseNumber, tenantId);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Case fetched successfully",
                        loanCase
                )
        );
    }

    // UPDATE CASE
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'TEAM_LEAD')")

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CaseResponse>> updateCase(
            @PathVariable Long id,
            @RequestParam Long tenantId,
            @Valid @RequestBody CaseUpdateRequest request) {

        CaseResponse loanCase =
                caseService.updateCase(id, tenantId, request);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Case updated successfully",
                        loanCase
                )
        );
    }

    // SOFT DELETE CASE
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<CaseResponse>> deleteCase(
            @PathVariable Long id,
            @RequestParam Long tenantId) {

        CaseResponse loanCase =
                caseService.softDeleteCase(id, tenantId);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Case deleted successfully",
                        loanCase
                )
        );
    }

    // RESTORE CASE
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
    @PatchMapping("/{id}/restore")
    public ResponseEntity<ApiResponse<CaseResponse>> restoreCase(
            @PathVariable Long id,
            @RequestParam Long tenantId) {

        CaseResponse loanCase =
                caseService.restoreCase(id, tenantId);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Case restored successfully",
                        loanCase
                )
        );
    }

    // FILTER CASES BY STATUS

    @GetMapping("/by-status")
    public ResponseEntity<ApiResponse<PageResponse<CaseResponse>>>
    getCasesByStatus(
            @RequestParam Long tenantId,
            @RequestParam CaseStatus status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        PageResponse<CaseResponse> cases =
                caseService.getCasesByStatus(
                        tenantId, status, page, size);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Cases fetched successfully",
                        cases
                )
        );
    }
}
