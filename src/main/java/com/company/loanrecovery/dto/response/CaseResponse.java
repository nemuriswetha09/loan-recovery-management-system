package com.company.loanrecovery.dto.response;

import com.company.loanrecovery.entity.enums.CaseStatus;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class CaseResponse {

    private Long id;
    private String caseNumber;
    private String loanAccountNumber;

    private String customerName;
    private String customerMobile;
    private String customerAddress;
    private String pincode;

    private String product;

    private BigDecimal tad;
    private BigDecimal pos;
    private BigDecimal emi;

    private Integer dpd;
    private String bucket;

    private CaseStatus caseStatus;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}