
package com.company.loanrecovery.entity;
import com.company.loanrecovery.entity.enums.EmployeeRole;
import com.company.loanrecovery.entity.enums.EmployeeStatus;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "employees")
@Getter
@Setter
@NoArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tenant_id", nullable = false)
    private Long tenantId;

    @Column(name = "emp_id", nullable = false)
    private String empId;

    @Column(name = "emp_name", nullable = false)
    private String empName;

    private String gender;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false ,length = 30, columnDefinition = "VARCHAR(30)")
    private EmployeeRole role;

    @Column(nullable = false)
    private String mobile;

    @Column(nullable = false)
    private String email;

    private String jobTitle;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EmployeeStatus userStatus = EmployeeStatus.ACTIVE;

    private String presentAdd;

    private String permanentAdd;

    private String branch;

    private String tlId;

    private String managerId;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private LocalDateTime deletedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
