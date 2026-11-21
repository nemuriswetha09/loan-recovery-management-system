package com.company.loanrecovery.service;

import com.company.loanrecovery.dto.request.AssignmentCreateRequest;
import com.company.loanrecovery.dto.request.AssignmentUpdateRequest;
import com.company.loanrecovery.dto.request.AssignmentReassignRequest;

import com.company.loanrecovery.dto.response.AssignmentResponse;
import com.company.loanrecovery.dto.response.PageResponse;

import com.company.loanrecovery.entity.Assignment;
import com.company.loanrecovery.entity.Employee;
import com.company.loanrecovery.entity.LoanCase;

import com.company.loanrecovery.entity.enums.AssignmentStatus;
import com.company.loanrecovery.entity.enums.EmployeeStatus;

import com.company.loanrecovery.exception.AssignmentNotFoundException;
import com.company.loanrecovery.exception.DuplicateResourceException;

import com.company.loanrecovery.mapper.AssignmentMapper;

import com.company.loanrecovery.repository.AssignmentRepository;
import com.company.loanrecovery.repository.EmployeeRepository;
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
public class AssignmentService {

    private final AssignmentRepository assignmentRepository;
    private final EmployeeRepository employeeRepository;
    private final CaseRepository caseRepository;
    private final AssignmentMapper assignmentMapper;

    public AssignmentService(
            AssignmentRepository assignmentRepository,
            EmployeeRepository employeeRepository,
            CaseRepository caseRepository,
            AssignmentMapper assignmentMapper) {

        this.assignmentRepository = assignmentRepository;
        this.employeeRepository = employeeRepository;
        this.caseRepository = caseRepository;
        this.assignmentMapper = assignmentMapper;
    }

    // CREATE ASSIGNMENT

    @Transactional
    public AssignmentResponse createAssignment(
            AssignmentCreateRequest request) {

        Long tenantId = request.getTenantId();

        LoanCase loanCase = findActiveCase(
                request.getCaseId(), tenantId);

        Employee employee = findActiveEmployee(
                request.getEmployeeId(), tenantId);

        Employee assignedBy = findActiveEmployee(
                request.getAssignedById(), tenantId);

        // Prevent assigning an employee to their own assignment
        // only if your business rules require it.
        // We do not enforce that restriction here.

        boolean alreadyAssigned =
                assignmentRepository
                        .existsByTenantIdAndLoanCase_IdAndAssignmentRoleAndAssignmentStatus(
                                tenantId,
                                loanCase.getId(),
                                request.getAssignmentRole(),
                                AssignmentStatus.ACTIVE
                        );

        if (alreadyAssigned) {
            throw new DuplicateResourceException(
                    "An active " + request.getAssignmentRole()
                            + " assignment already exists for this case");
        }

        Assignment assignment = new Assignment();

        assignment.setTenantId(tenantId);
        assignment.setLoanCase(loanCase);
        assignment.setEmployee(employee);
        assignment.setAssignedBy(assignedBy);
        assignment.setAssignmentRole(request.getAssignmentRole());
        assignment.setAssignmentStatus(AssignmentStatus.ACTIVE);
        assignment.setRemarks(request.getRemarks());

        Assignment savedAssignment =
                assignmentRepository.save(assignment);

        return assignmentMapper.toResponse(savedAssignment);
    }

    // GET ALL ASSIGNMENTS

    @Transactional(readOnly = true)
    public PageResponse<AssignmentResponse> getAllAssignments(
            Long tenantId,
            int page,
            int size) {

        validatePagination(page, size);

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(Sort.Direction.DESC, "assignedAt")
        );

        Page<Assignment> assignmentPage =
                assignmentRepository.findAllByTenantId(
                        tenantId, pageable);

        return toPageResponse(assignmentPage);
    }

    // GET ASSIGNMENT BY ID

    @Transactional(readOnly = true)
    public AssignmentResponse getAssignmentById(
            Long id,
            Long tenantId) {

        Assignment assignment = findAssignment(id, tenantId);

        return assignmentMapper.toResponse(assignment);
    }

    // GET ASSIGNMENTS BY CASE ID

    @Transactional(readOnly = true)
    public PageResponse<AssignmentResponse> getAssignmentsByCase(
            Long caseId,
            Long tenantId,
            int page,
            int size) {

        validatePagination(page, size);

        // Verify case belongs to this tenant and is active
        findActiveCase(caseId, tenantId);

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(Sort.Direction.DESC, "assignedAt")
        );

        Page<Assignment> assignmentPage =
                assignmentRepository.findAllByTenantIdAndLoanCase_Id(
                        tenantId, caseId, pageable);

        return toPageResponse(assignmentPage);
    }

    // GET ASSIGNMENTS BY EMPLOYEE ID

    @Transactional(readOnly = true)
    public PageResponse<AssignmentResponse> getAssignmentsByEmployee(
            Long employeeId,
            Long tenantId,
            int page,
            int size) {

        validatePagination(page, size);

        // Verify employee belongs to this tenant
        findEmployee(employeeId, tenantId);

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(Sort.Direction.DESC, "assignedAt")
        );

        Page<Assignment> assignmentPage =
                assignmentRepository.findAllByTenantIdAndEmployee_Id(
                        tenantId, employeeId, pageable);

        return toPageResponse(assignmentPage);
    }

    // UPDATE ASSIGNMENT REMARKS

    @Transactional
    public AssignmentResponse updateAssignment(
            Long id,
            Long tenantId,
            AssignmentUpdateRequest request) {

        Assignment assignment = findAssignment(id, tenantId);

        if (assignment.getAssignmentStatus()
                != AssignmentStatus.ACTIVE) {

            throw new IllegalArgumentException(
                    "Only active assignments can be updated");
        }

        assignment.setRemarks(request.getRemarks());

        Assignment updatedAssignment =
                assignmentRepository.save(assignment);

        return assignmentMapper.toResponse(updatedAssignment);
    }

    // REASSIGN CASE

    @Transactional
    public AssignmentResponse reassignCase(
            Long id,
            Long tenantId,
            AssignmentReassignRequest request) {

        Assignment oldAssignment = findAssignment(id, tenantId);

        if (oldAssignment.getAssignmentStatus()
                != AssignmentStatus.ACTIVE) {

            throw new IllegalArgumentException(
                    "Only active assignments can be reassigned");
        }

        Employee newEmployee = findActiveEmployee(
                request.getNewEmployeeId(), tenantId);

        Employee assignedBy = findActiveEmployee(
                request.getAssignedById(), tenantId);

        if (oldAssignment.getEmployee().getId()
                .equals(newEmployee.getId())) {

            throw new IllegalArgumentException(
                    "New employee is already assigned to this case");
        }

        // Close the old assignment but retain its history
        oldAssignment.setAssignmentStatus(
                AssignmentStatus.REASSIGNED);

        oldAssignment.setClosedAt(LocalDateTime.now());

        assignmentRepository.save(oldAssignment);

        // Create a new assignment for the replacement employee
        Assignment newAssignment = new Assignment();

        newAssignment.setTenantId(tenantId);
        newAssignment.setLoanCase(oldAssignment.getLoanCase());
        newAssignment.setEmployee(newEmployee);
        newAssignment.setAssignedBy(assignedBy);

        // Preserve the old assignment's role
        newAssignment.setAssignmentRole(
                oldAssignment.getAssignmentRole());

        newAssignment.setAssignmentStatus(
                AssignmentStatus.ACTIVE);

        newAssignment.setRemarks(request.getRemarks());

        Assignment savedAssignment =
                assignmentRepository.save(newAssignment);

        return assignmentMapper.toResponse(savedAssignment);
    }

    // CLOSE ASSIGNMENT

    @Transactional
    public AssignmentResponse closeAssignment(
            Long id,
            Long tenantId) {

        Assignment assignment = findAssignment(id, tenantId);

        if (assignment.getAssignmentStatus()
                != AssignmentStatus.ACTIVE) {

            throw new IllegalArgumentException(
                    "Only active assignments can be completed");
        }

        assignment.setAssignmentStatus(
                AssignmentStatus.COMPLETED);

        assignment.setClosedAt(LocalDateTime.now());

        Assignment closedAssignment =
                assignmentRepository.save(assignment);

        return assignmentMapper.toResponse(closedAssignment);
    }

    // GET ACTIVE ASSIGNMENTS

    @Transactional(readOnly = true)
    public PageResponse<AssignmentResponse> getActiveAssignments(
            Long tenantId,
            int page,
            int size) {

        validatePagination(page, size);

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(Sort.Direction.DESC, "assignedAt")
        );

        Page<Assignment> assignmentPage =
                assignmentRepository
                        .findAllByTenantIdAndAssignmentStatus(
                                tenantId,
                                AssignmentStatus.ACTIVE,
                                pageable
                        );

        return toPageResponse(assignmentPage);
    }

    // COMMON HELPER METHODS

    private Assignment findAssignment(Long id, Long tenantId) {

        return assignmentRepository
                .findByIdAndTenantId(id, tenantId)
                .orElseThrow(() -> new AssignmentNotFoundException(
                        "Assignment not found with ID: " + id));
    }

    private LoanCase findActiveCase(Long caseId, Long tenantId) {

        return caseRepository
                .findByIdAndTenantIdAndDeletedFalse(caseId, tenantId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Active case not found with ID: " + caseId));
    }

    private Employee findActiveEmployee(
            Long employeeId,
            Long tenantId) {

        return employeeRepository
                .findByIdAndTenantIdAndUserStatus(
                        employeeId,
                        tenantId,
                        EmployeeStatus.ACTIVE
                )
                .orElseThrow(() -> new IllegalArgumentException(
                        "Active employee not found with ID: " + employeeId));
    }

    private Employee findEmployee(Long employeeId, Long tenantId) {

        return employeeRepository
                .findByIdAndTenantId(employeeId, tenantId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Employee not found with ID: " + employeeId));
    }

    private PageResponse<AssignmentResponse> toPageResponse(
            Page<Assignment> assignmentPage) {

        List<AssignmentResponse> assignments =
                assignmentPage.getContent()
                        .stream()
                        .map(assignmentMapper::toResponse)
                        .toList();

        return new PageResponse<>(
                assignments,
                assignmentPage.getNumber(),
                assignmentPage.getSize(),
                assignmentPage.getTotalElements(),
                assignmentPage.getTotalPages(),
                assignmentPage.isFirst(),
                assignmentPage.isLast()
        );
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