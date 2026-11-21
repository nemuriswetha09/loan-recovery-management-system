package com.company.loanrecovery.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AssignmentReassignRequest {

    @NotNull
    @Positive
    private Long newEmployeeId;

    @NotNull
    @Positive
    private Long assignedById;

    @Size(max = 500)
    private String remarks;
}