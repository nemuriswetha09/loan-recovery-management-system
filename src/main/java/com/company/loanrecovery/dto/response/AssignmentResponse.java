package com.company.loanrecovery.dto.response;

import com.company.loanrecovery.entity.enums.AssignmentRole;
import com.company.loanrecovery.entity.enums.AssignmentStatus;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AssignmentResponse {

    private Long id;

    private Long tenantId;

    private Long caseId;
    private String caseNumber;
    private String customerName;

    private Long employeeId;
    private String empId;
    private String employeeName;

    private Long assignedById;
    private String assignedByName;

    private AssignmentRole assignmentRole;
    private AssignmentStatus assignmentStatus;

    private String remarks;

    private LocalDateTime assignedAt;
    private LocalDateTime closedAt;
}