package com.company.loanrecovery.dto.response;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import com.company.loanrecovery.entity.enums.ResolutionStatus;
public record ResolutionResponse(Long id, Long tenantId, Long caseId, String resolutionType, String description, java.time.LocalDateTime resolvedAt, ResolutionStatus status, LocalDateTime createdAt) {}
