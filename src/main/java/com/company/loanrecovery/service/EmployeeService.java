package com.company.loanrecovery.service;

import com.company.loanrecovery.entity.Employee;
import com.company.loanrecovery.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository ;

    public EmployeeService(EmployeeRepository employeeRepository){
        this.employeeRepository=employeeRepository;
    }

    public Employee createEmployee(Employee employee){
        return employeeRepository.save(employee);
    }
}
