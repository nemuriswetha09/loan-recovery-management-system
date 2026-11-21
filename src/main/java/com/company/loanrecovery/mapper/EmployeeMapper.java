
package com.company.loanrecovery.mapper;

import com.company.loanrecovery.dto.request.EmployeeCreateRequest;
import com.company.loanrecovery.dto.request.EmployeeUpdateRequest;
import com.company.loanrecovery.dto.response.EmployeeResponse;
import com.company.loanrecovery.entity.Employee;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapper {

    // CREATE: DTO -> Entity
    public Employee toEntity(EmployeeCreateRequest request) {

        Employee employee = new Employee();

        employee.setTenantId(request.getTenantId());
        employee.setEmpId(request.getEmpId());
        employee.setEmpName(request.getEmpName());
        employee.setGender(request.getGender());
        employee.setRole(request.getRole());
        employee.setMobile(request.getMobile());
        employee.setEmail(request.getEmail());
        employee.setJobTitle(request.getJobTitle());
        employee.setPresentAdd(request.getPresentAdd());
        employee.setPermanentAdd(request.getPermanentAdd());
        employee.setBranch(request.getBranch());
        employee.setTlId(request.getTlId());
        employee.setManagerId(request.getManagerId());

        return employee;
    }

    // UPDATE: Update existing Entity using DTO
    public void updateEntity(
            Employee employee,
            EmployeeUpdateRequest request) {

        employee.setEmpName(request.getEmpName());
        employee.setGender(request.getGender());
        employee.setRole(request.getRole());
        employee.setMobile(request.getMobile());
        employee.setEmail(request.getEmail());
        employee.setJobTitle(request.getJobTitle());
        employee.setPresentAdd(request.getPresentAdd());
        employee.setPermanentAdd(request.getPermanentAdd());
        employee.setBranch(request.getBranch());
        employee.setTlId(request.getTlId());
        employee.setManagerId(request.getManagerId());
    }

    // RESPONSE: Entity -> Response DTO
    public EmployeeResponse toResponse(Employee employee) {

        EmployeeResponse response = new EmployeeResponse();

        response.setId(employee.getId());
        response.setEmpId(employee.getEmpId());
        response.setEmpName(employee.getEmpName());
        response.setRole(employee.getRole());
        response.setMobile(employee.getMobile());
        response.setEmail(employee.getEmail());
        response.setJobTitle(employee.getJobTitle());
        response.setUserStatus(employee.getUserStatus());

        return response;
    }
}
