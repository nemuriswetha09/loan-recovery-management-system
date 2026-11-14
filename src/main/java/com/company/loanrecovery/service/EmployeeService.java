package com.company.loanrecovery.service;

import com.company.loanrecovery.entity.Employee;
import com.company.loanrecovery.entity.enums.EmployeeStatus;
import com.company.loanrecovery.repository.EmployeeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository ;

    public EmployeeService(EmployeeRepository employeeRepository){
        this.employeeRepository=employeeRepository;
    }

    public Employee createEmployee(Employee employee){
        return employeeRepository.save(employee);
    }
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }
    public List<Employee> getAllEmployee(){
        return employeeRepository.findAllByUserStatusNot(EmployeeStatus.DELETED);
    }
    public Employee getEmployeeById(Long id) {
        return employeeRepository
                .findByIdAndUserStatusNot(id, EmployeeStatus.DELETED)
                .orElseThrow(() -> new RuntimeException(
                        "Employee not found with ID: " + id
                ));
    }

    // UPDATE EMPLOYEE
    @Transactional
    public Employee updateEmployee(Long id, Employee updatedEmployee) {

        Employee existingEmployee = getEmployeeById(id);

        existingEmployee.setEmpName(updatedEmployee.getEmpName());
        existingEmployee.setGender(updatedEmployee.getGender());
        existingEmployee.setRole(updatedEmployee.getRole());
        existingEmployee.setMobile(updatedEmployee.getMobile());
        existingEmployee.setEmail(updatedEmployee.getEmail());
        existingEmployee.setJobTitle(updatedEmployee.getJobTitle());
        existingEmployee.setPresentAdd(updatedEmployee.getPresentAdd());
        existingEmployee.setPermanentAdd(updatedEmployee.getPermanentAdd());
        existingEmployee.setBranch(updatedEmployee.getBranch());
        existingEmployee.setTlId(updatedEmployee.getTlId());
        existingEmployee.setManagerId(updatedEmployee.getManagerId());

        return employeeRepository.save(existingEmployee);
    }

    // SOFT DELETE EMPLOYEE
    @Transactional
    public Employee softDeleteEmployee(Long id) {

        Employee employee = getEmployeeById(id);

        employee.setUserStatus(EmployeeStatus.DELETED);
        employee.setDeletedAt(LocalDateTime.now());

        return employeeRepository.save(employee);
    }

    // RESTORE EMPLOYEE
    @Transactional
    public Employee restoreEmployee(Long id) {

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Employee not found with ID: " + id
                ));

        if (employee.getUserStatus() != EmployeeStatus.DELETED) {
            throw new RuntimeException("Employee is not deleted");
        }

        employee.setUserStatus(EmployeeStatus.ACTIVE);
        employee.setDeletedAt(null);

        return employeeRepository.save(employee);
    }

}
