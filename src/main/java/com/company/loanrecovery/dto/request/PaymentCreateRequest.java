package com.company.loanrecovery.dto.request;
import jakarta.validation.constraints.*;
import com.company.loanrecovery.entity.enums.PaymentStatus;
public record PaymentCreateRequest(@NotNull Long tenantId,@NotNull Long caseId, @NotNull java.math.BigDecimal amount, String paymentMode, String transactionReference, java.time.LocalDateTime paidAt, PaymentStatus status) {}
