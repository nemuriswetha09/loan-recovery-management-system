package com.company.loanrecovery.entity;

import com.company.loanrecovery.entity.enums.AssignmentRole;
import com.company.loanrecovery.entity.enums.AssignmentStatus;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "assignments",
        indexes = {
                @Index(name = "idx_assignment_tenant", columnList = "tenant_id"),
                @Index(name = "idx_assignment_case", columnList = "case_id"),
                @Index(name = "idx_assignment_employee", columnList = "employee_id"),
                @Index(name = "idx_assignment_status", columnList = "assignment_status")
        }
)
@Getter
@Setter
@NoArgsConstructor
public class Assignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tenant_id", nullable = false)
    private Long tenantId;

    // Relationship with LoanCase

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "case_id", nullable = false)
    private LoanCase loanCase;

    // Relationship with Employee who receives the assignment

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    // Employee who assigned the case

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_by", nullable = false)
    private Employee assignedBy;

    @Enumerated(EnumType.STRING)
    @Column(name = "assignment_role", nullable = false, length = 30)
    private AssignmentRole assignmentRole;

    @Enumerated(EnumType.STRING)
    @Column(name = "assignment_status", nullable = false, length = 30)
    private AssignmentStatus assignmentStatus = AssignmentStatus.ACTIVE;

    @Column(length = 500)
    private String remarks;

    @Column(name = "assigned_at", nullable = false)
    private LocalDateTime assignedAt;

    @Column(name = "closed_at")
    private LocalDateTime closedAt;

    @PrePersist
    protected void onCreate() {

        assignedAt = LocalDateTime.now();

        if (assignmentStatus == null) {
            assignmentStatus = AssignmentStatus.ACTIVE;
        }
    }
}