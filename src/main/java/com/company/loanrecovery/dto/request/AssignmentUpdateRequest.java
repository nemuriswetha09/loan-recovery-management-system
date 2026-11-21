package com.company.loanrecovery.dto.request;

import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AssignmentUpdateRequest {

    @Size(max = 500)
    private String remarks;
}