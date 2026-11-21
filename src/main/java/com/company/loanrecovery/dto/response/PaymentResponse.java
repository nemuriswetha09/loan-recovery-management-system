package com.company.loanrecovery.dto.response;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import com.company.loanrecovery.entity.enums.PaymentStatus;
public record PaymentResponse(Long id, Long tenantId, Long caseId, java.math.BigDecimal amount, String paymentMode, String transactionReference, java.time.LocalDateTime paidAt, PaymentStatus status, LocalDateTime createdAt) {}
