package com.company.loanrecovery.service;

import com.company.loanrecovery.dto.request.EmployeeCreateRequest;
import com.company.loanrecovery.dto.request.EmployeeUpdateRequest;
import com.company.loanrecovery.dto.response.EmployeeResponse;
import com.company.loanrecovery.dto.response.PageResponse;
import com.company.loanrecovery.entity.Employee;
import com.company.loanrecovery.entity.enums.EmployeeStatus;
import com.company.loanrecovery.exception.DuplicateResourceException;
import com.company.loanrecovery.exception.EmployeeNotFoundException;
import com.company.loanrecovery.mapper.EmployeeMapper;
import com.company.loanrecovery.repository.EmployeeRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;

    public EmployeeService(
            EmployeeRepository employeeRepository,
            EmployeeMapper employeeMapper) {

        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
    }

    // CREATE EMPLOYEE

    @Transactional
    public EmployeeResponse createEmployee(EmployeeCreateRequest request) {

        Long tenantId = request.getTenantId();

        if (employeeRepository.existsByTenantIdAndEmpId(
                tenantId, request.getEmpId())) {

            throw new DuplicateResourceException(
                    "Employee ID already exists");
        }

        if (employeeRepository.existsByTenantIdAndEmail(
                tenantId, request.getEmail())) {

            throw new DuplicateResourceException(
                    "Email already exists");
        }

        if (employeeRepository.existsByTenantIdAndMobile(
                tenantId, request.getMobile())) {

            throw new DuplicateResourceException(
                    "Mobile number already exists");
        }

        Employee employee = employeeMapper.toEntity(request);

        Employee savedEmployee = employeeRepository.save(employee);

        return employeeMapper.toResponse(savedEmployee);
    }

    // GET ALL EMPLOYEES WITH PAGINATION

    @Transactional(readOnly = true)
    public PageResponse<EmployeeResponse> getAllEmployees(
            Long tenantId,
            int page,
            int size) {

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

        Page<Employee> employeePage =
                employeeRepository.findAllByTenantIdAndUserStatusNot(
                        tenantId,
                        EmployeeStatus.DELETED,
                        pageable
                );

        List<EmployeeResponse> employees =
                employeePage.getContent()
                        .stream()
                        .map(employeeMapper::toResponse)
                        .toList();

        return new PageResponse<>(
                employees,
                employeePage.getNumber(),
                employeePage.getSize(),
                employeePage.getTotalElements(),
                employeePage.getTotalPages(),
                employeePage.isFirst(),
                employeePage.isLast()
        );
    }

    // GET EMPLOYEE BY ID

    @Transactional(readOnly = true)
    public EmployeeResponse getEmployeeById(
            Long id,
            Long tenantId) {

        Employee employee = findActiveEmployee(id, tenantId);

        return employeeMapper.toResponse(employee);
    }

    // UPDATE EMPLOYEE

    @Transactional
    public EmployeeResponse updateEmployee(
            Long id,
            Long tenantId,
            EmployeeUpdateRequest request) {

        Employee existingEmployee = findActiveEmployee(id, tenantId);

        if (employeeRepository.existsByTenantIdAndEmailAndIdNot(
                tenantId, request.getEmail(), id)) {

            throw new DuplicateResourceException(
                    "Email already exists");
        }

        if (employeeRepository.existsByTenantIdAndMobileAndIdNot(
                tenantId, request.getMobile(), id)) {

            throw new DuplicateResourceException(
                    "Mobile number already exists");
        }

        employeeMapper.updateEntity(existingEmployee, request);

        Employee updatedEmployee =
                employeeRepository.save(existingEmployee);

        return employeeMapper.toResponse(updatedEmployee);
    }

    // SOFT DELETE EMPLOYEE

    @Transactional
    public EmployeeResponse softDeleteEmployee(
            Long id,
            Long tenantId) {

        Employee employee = findActiveEmployee(id, tenantId);

        employee.setUserStatus(EmployeeStatus.DELETED);
        employee.setDeletedAt(LocalDateTime.now());

        Employee deletedEmployee =
                employeeRepository.save(employee);

        return employeeMapper.toResponse(deletedEmployee);
    }

    // RESTORE EMPLOYEE

    @Transactional
    public EmployeeResponse restoreEmployee(
            Long id,
            Long tenantId) {

        Employee employee = employeeRepository
                .findByIdAndTenantId(id, tenantId)
                .orElseThrow(() -> new EmployeeNotFoundException(
                        "Employee not found with ID: " + id));

        if (employee.getUserStatus() != EmployeeStatus.DELETED) {
            throw new IllegalArgumentException(
                    "Employee is not deleted");
        }

        employee.setUserStatus(EmployeeStatus.ACTIVE);
        employee.setDeletedAt(null);

        Employee restoredEmployee =
                employeeRepository.save(employee);

        return employeeMapper.toResponse(restoredEmployee);
    }

    // COMMON HELPER METHOD

    private Employee findActiveEmployee(Long id, Long tenantId) {

        return employeeRepository
                .findByIdAndTenantIdAndUserStatusNot(
                        id,
                        tenantId,
                        EmployeeStatus.DELETED
                )
                .orElseThrow(() -> new EmployeeNotFoundException(
                        "Employee not found with ID: " + id));
    }
}