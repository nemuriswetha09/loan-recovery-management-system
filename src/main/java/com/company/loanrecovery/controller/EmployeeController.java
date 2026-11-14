package com.company.loanrecovery.controller;

import com.company.loanrecovery.entity.Employee;
import com.company.loanrecovery.service.EmployeeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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


    @GetMapping
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @PutMapping("/{id}")
    public Employee updateEmployee(
            @PathVariable Long id,
            @RequestBody Employee employee) {
        return employeeService.updateEmployee(id, employee);
    }
    // GET BY ID
    @GetMapping("/{id}")
    public Employee getEmployeeById(@PathVariable Long id) {
        return employeeService.getEmployeeById(id);
    }

    // SOFT DELETE
    @DeleteMapping("/{id}")
    public Employee softDeleteEmployee(@PathVariable Long id) {
        return employeeService.softDeleteEmployee(id);
    }

    // RESTORE
    @PatchMapping("/{id}/restore")
    public Employee restoreEmployee(@PathVariable Long id) {
        return employeeService.restoreEmployee(id);
    }
}

