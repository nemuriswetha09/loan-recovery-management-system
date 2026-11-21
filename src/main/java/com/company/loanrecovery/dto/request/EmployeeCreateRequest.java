package com.company.loanrecovery.dto.request;

import com.company.loanrecovery.entity.enums.EmployeeRole;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeCreateRequest {

    @NotNull
    @Positive
    private Long tenantId;

    @NotBlank
    @Size(max = 30)
    private String empId;

    @NotBlank
    @Size(min = 3, max = 100)
    private String empName;

    @Size(max = 20)
    private String gender;

    @NotNull
    private EmployeeRole role;

    @NotBlank
    @Pattern(regexp = "^[6-9][0-9]{9}$",
            message = "Enter a valid 10-digit Indian mobile number")
    private String mobile;

    @NotBlank
    @Email
    @Size(max = 150)
    private String email;

    @Size(max = 100)
    private String jobTitle;

    private String presentAdd;
    private String permanentAdd;

    @Size(max = 100)
    private String branch;

    private String tlId;
    private String managerId;
}