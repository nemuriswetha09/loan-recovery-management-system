package com.company.loanrecovery.controller;
import org.springframework.security.access.prepost.PreAuthorize;
import com.company.loanrecovery.common.ApiResponse;
import com.company.loanrecovery.dto.request.EmployeeCreateRequest;
import com.company.loanrecovery.dto.request.EmployeeUpdateRequest;
import com.company.loanrecovery.dto.response.EmployeeResponse;
import com.company.loanrecovery.dto.response.PageResponse;
import com.company.loanrecovery.service.EmployeeService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'TEAM_LEAD')")
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    // CREATE EMPLOYEE

    @PostMapping
    public ResponseEntity<ApiResponse<EmployeeResponse>> createEmployee(
            @Valid @RequestBody EmployeeCreateRequest request) {

        EmployeeResponse employee =
                employeeService.createEmployee(request);

        ApiResponse<EmployeeResponse> response =
                new ApiResponse<>(
                        true,
                        "Employee created successfully",
                        employee
                );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // GET ALL EMPLOYEES

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<EmployeeResponse>>>
    getAllEmployees(
            @RequestParam Long tenantId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        PageResponse<EmployeeResponse> employees =
                employeeService.getAllEmployees(tenantId, page, size);

        ApiResponse<PageResponse<EmployeeResponse>> response =
                new ApiResponse<>(
                        true,
                        "Employees fetched successfully",
                        employees
                );

        return ResponseEntity.ok(response);
    }

    // GET EMPLOYEE BY ID
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'TEAM_LEAD')")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<EmployeeResponse>> getEmployeeById(
            @PathVariable Long id,
            @RequestParam Long tenantId) {

        EmployeeResponse employee =
                employeeService.getEmployeeById(id, tenantId);

        ApiResponse<EmployeeResponse> response =
                new ApiResponse<>(
                        true,
                        "Employee fetched successfully",
                        employee
                );

        return ResponseEntity.ok(response);
    }

    // UPDATE EMPLOYEE
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<EmployeeResponse>> updateEmployee(
            @PathVariable Long id,
            @RequestParam Long tenantId,
            @Valid @RequestBody EmployeeUpdateRequest request) {

        EmployeeResponse employee =
                employeeService.updateEmployee(id, tenantId, request);

        ApiResponse<EmployeeResponse> response =
                new ApiResponse<>(
                        true,
                        "Employee updated successfully",
                        employee
                );

        return ResponseEntity.ok(response);
    }

    // SOFT DELETE EMPLOYEE
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<EmployeeResponse>> softDeleteEmployee(
            @PathVariable Long id,
            @RequestParam Long tenantId) {

        EmployeeResponse employee =
                employeeService.softDeleteEmployee(id, tenantId);

        ApiResponse<EmployeeResponse> response =
                new ApiResponse<>(
                        true,
                        "Employee deleted successfully",
                        employee
                );

        return ResponseEntity.ok(response);
    }

    // RESTORE EMPLOYEE
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")

    @PatchMapping("/{id}/restore")
    public ResponseEntity<ApiResponse<EmployeeResponse>> restoreEmployee(
            @PathVariable Long id,
            @RequestParam Long tenantId) {

        EmployeeResponse employee =
                employeeService.restoreEmployee(id, tenantId);

        ApiResponse<EmployeeResponse> response =
                new ApiResponse<>(
                        true,
                        "Employee restored successfully",
                        employee
                );

        return ResponseEntity.ok(response);
    }
}