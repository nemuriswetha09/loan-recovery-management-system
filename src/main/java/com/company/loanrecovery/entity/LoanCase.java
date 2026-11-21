
package com.company.loanrecovery.entity;

import com.company.loanrecovery.entity.enums.CaseStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "loan_cases",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_case_tenant_case_number",
                        columnNames = {"tenant_id", "case_number"}
                ),
                @UniqueConstraint(
                        name = "uk_case_tenant_loan_account",
                        columnNames = {"tenant_id", "loan_account_number"}
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
public class LoanCase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tenant_id", nullable = false)
    private Long tenantId;

    @Column(name = "case_number", nullable = false, length = 50)
    private String caseNumber;

    @Column(name = "loan_account_number", nullable = false, length = 50)
    private String loanAccountNumber;

    @Column(name = "customer_name", nullable = false, length = 150)
    private String customerName;

    @Column(name = "customer_mobile", nullable = false, length = 10)
    private String customerMobile;

    @Column(name = "customer_address", columnDefinition = "TEXT")
    private String customerAddress;

    @Column(length = 10)
    private String pincode;

    @Column(length = 100)
    private String product;

    // Total Amount Due
    @Column(precision = 15, scale = 2)
    private BigDecimal tad;

    // Principal Outstanding
    @Column(precision = 15, scale = 2)
    private BigDecimal pos;

    @Column(precision = 15, scale = 2)
    private BigDecimal emi;

    private Integer dpd;

    @Column(length = 20)
    private String bucket;

    @Enumerated(EnumType.STRING)
    @Column(name = "case_status", nullable = false, length = 20)
    private CaseStatus caseStatus = CaseStatus.OPEN;

    @Column(name = "is_deleted", nullable = false)
    private boolean deleted = false;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private LocalDateTime deletedAt;

    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        createdAt = now;
        updatedAt = now;

        if (caseStatus == null) {
            caseStatus = CaseStatus.OPEN;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
