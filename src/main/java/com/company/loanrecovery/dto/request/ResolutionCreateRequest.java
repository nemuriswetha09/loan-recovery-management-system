package com.company.loanrecovery.dto.request;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import com.company.loanrecovery.entity.enums.ResolutionStatus;
public record ResolutionCreateRequest(@NotNull Long tenantId,@NotNull Long caseId, String resolutionType, String description, java.time.LocalDateTime resolvedAt, ResolutionStatus status) {}
