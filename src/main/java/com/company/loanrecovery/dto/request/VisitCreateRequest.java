package com.company.loanrecovery.dto.request;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import com.company.loanrecovery.entity.enums.VisitStatus;
public record VisitCreateRequest(@NotNull Long tenantId,@NotNull Long caseId, @NotNull Long employeeId, @NotNull java.time.LocalDateTime visitDate, String outcome, String remarks, VisitStatus status) {}
