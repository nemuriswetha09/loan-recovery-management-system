package com.company.loanrecovery.dto.response;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import com.company.loanrecovery.entity.enums.VisitStatus;
public record VisitResponse(Long id, Long tenantId, Long caseId, Long employeeId, java.time.LocalDateTime visitDate, String outcome, String remarks, VisitStatus status, LocalDateTime createdAt) {}
