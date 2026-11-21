package com.company.loanrecovery.dto.response;

import com.company.loanrecovery.entity.enums.EmployeeRole;
import com.company.loanrecovery.entity.enums.EmployeeStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeResponse {

    private Long id;
    private String empId;
    private String empName;
    private EmployeeRole role;
    private String mobile;
    private String email;
    private String jobTitle;
    private EmployeeStatus userStatus;
}