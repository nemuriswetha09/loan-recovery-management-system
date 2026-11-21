package com.company.loanrecovery.dto.request;

import com.company.loanrecovery.entity.enums.AssignmentRole;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AssignmentCreateRequest {

    @NotNull
    @Positive
    private Long tenantId;

    @NotNull
    @Positive
    private Long caseId;

    @NotNull
    @Positive
    private Long employeeId;

    @NotNull
    @Positive
    private Long assignedById;

    @NotNull
    private AssignmentRole assignmentRole;

    @Size(max = 500)
    private String remarks;
}