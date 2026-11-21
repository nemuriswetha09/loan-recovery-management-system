package com.company.loanrecovery.mapper;

import com.company.loanrecovery.dto.response.AssignmentResponse;
import com.company.loanrecovery.entity.Assignment;

import org.springframework.stereotype.Component;

@Component
public class AssignmentMapper {

    public AssignmentResponse toResponse(Assignment assignment) {

        AssignmentResponse response = new AssignmentResponse();

        response.setId(assignment.getId());
        response.setTenantId(assignment.getTenantId());

        response.setCaseId(assignment.getLoanCase().getId());
        response.setCaseNumber(assignment.getLoanCase().getCaseNumber());
        response.setCustomerName(assignment.getLoanCase().getCustomerName());

        response.setEmployeeId(assignment.getEmployee().getId());
        response.setEmpId(assignment.getEmployee().getEmpId());
        response.setEmployeeName(assignment.getEmployee().getEmpName());

        response.setAssignedById(assignment.getAssignedBy().getId());
        response.setAssignedByName(assignment.getAssignedBy().getEmpName());

        response.setAssignmentRole(assignment.getAssignmentRole());
        response.setAssignmentStatus(assignment.getAssignmentStatus());

        response.setRemarks(assignment.getRemarks());

        response.setAssignedAt(assignment.getAssignedAt());
        response.setClosedAt(assignment.getClosedAt());

        return response;
    }
}