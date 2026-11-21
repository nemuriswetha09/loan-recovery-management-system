package com.company.loanrecovery.dto.request;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
public record LoginRequest(@NotNull Long tenantId,
                           @NotBlank String username,
                           @NotBlank String password) {}
