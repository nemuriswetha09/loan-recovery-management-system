package com.company.loanrecovery.controller;

import com.company.loanrecovery.dto.request.AssignmentCreateRequest;
import com.company.loanrecovery.dto.request.AssignmentUpdateRequest;
import com.company.loanrecovery.dto.request.AssignmentReassignRequest;

import com.company.loanrecovery.dto.response.AssignmentResponse;
import com.company.loanrecovery.dto.response.PageResponse;
import com.company.loanrecovery.common.ApiResponse;

import com.company.loanrecovery.service.AssignmentService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/assignments")
public class AssignmentController {

    private final AssignmentService assignmentService;

    public AssignmentController(
            AssignmentService assignmentService) {

        this.assignmentService = assignmentService;
    }

    // CREATE ASSIGNMENT

    @PostMapping
    public ResponseEntity<ApiResponse<AssignmentResponse>> createAssignment(
            @Valid @RequestBody AssignmentCreateRequest request) {

        AssignmentResponse response =
                assignmentService.createAssignment(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(
                        true,
                        "Assignment created successfully",
                        response
                ));
    }

    // GET ALL ASSIGNMENTS

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<AssignmentResponse>>> getAllAssignments(
            @RequestParam Long tenantId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        PageResponse<AssignmentResponse> response =
                assignmentService.getAllAssignments(
                        tenantId, page, size);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Assignments fetched successfully",
                        response
                ));
    }

    // GET ASSIGNMENT BY ID

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AssignmentResponse>> getAssignmentById(
            @PathVariable Long id,
            @RequestParam Long tenantId) {

        AssignmentResponse response =
                assignmentService.getAssignmentById(id, tenantId);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Assignment fetched successfully",
                        response
                ));
    }

    // GET ASSIGNMENTS BY CASE ID

    @GetMapping("/case/{caseId}")
    public ResponseEntity<ApiResponse<PageResponse<AssignmentResponse>>> getAssignmentsByCase(
            @PathVariable Long caseId,
            @RequestParam Long tenantId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        PageResponse<AssignmentResponse> response =
                assignmentService.getAssignmentsByCase(
                        caseId, tenantId, page, size);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Case assignments fetched successfully",
                        response
                ));
    }

    // GET ASSIGNMENTS BY EMPLOYEE ID

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<ApiResponse<PageResponse<AssignmentResponse>>> getAssignmentsByEmployee(
            @PathVariable Long employeeId,
            @RequestParam Long tenantId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        PageResponse<AssignmentResponse> response =
                assignmentService.getAssignmentsByEmployee(
                        employeeId, tenantId, page, size);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Employee assignments fetched successfully",
                        response
                ));
    }

    // UPDATE ASSIGNMENT

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<AssignmentResponse>> updateAssignment(
            @PathVariable Long id,
            @RequestParam Long tenantId,
            @Valid @RequestBody AssignmentUpdateRequest request) {

        AssignmentResponse response =
                assignmentService.updateAssignment(
                        id, tenantId, request);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Assignment updated successfully",
                        response
                ));
    }

    // REASSIGN CASE

    @PatchMapping("/{id}/reassign")
    public ResponseEntity<ApiResponse<AssignmentResponse>> reassignCase(
            @PathVariable Long id,
            @RequestParam Long tenantId,
            @Valid @RequestBody AssignmentReassignRequest request) {

        AssignmentResponse response =
                assignmentService.reassignCase(
                        id, tenantId, request);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Case reassigned successfully",
                        response
                ));
    }

    // CLOSE ASSIGNMENT

    @PatchMapping("/{id}/close")
    public ResponseEntity<ApiResponse<AssignmentResponse>> closeAssignment(
            @PathVariable Long id,
            @RequestParam Long tenantId) {

        AssignmentResponse response =
                assignmentService.closeAssignment(id, tenantId);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Assignment closed successfully",
                        response
                ));
    }

    // GET ACTIVE ASSIGNMENTS

    @GetMapping("/active")
    public ResponseEntity<ApiResponse<PageResponse<AssignmentResponse>>> getActiveAssignments(
            @RequestParam Long tenantId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        PageResponse<AssignmentResponse> response =
                assignmentService.getActiveAssignments(
                        tenantId, page, size);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Active assignments fetched successfully",
                        response
                ));
    }
}