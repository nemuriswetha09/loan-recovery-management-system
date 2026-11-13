package com.company.loanrecovery.controller;

import com.company.loanrecovery.entity.Employee;
import com.company.loanrecovery.service.EmployeeService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService){
        this.employeeService= employeeService;
    }
@PostMapping
    public Employee createEmployee( @RequestBody Employee employee){
        return employeeService.createEmployee(employee);
    }
}
