package com.company.loanrecovery.dto.request;
import jakarta.validation.constraints.*;
public record UserCreateRequest(@NotNull Long tenantId,@NotBlank @Size(max=80) String username,@NotBlank @Size(min=12,max=72) String password,@NotBlank String role,Long employeeId) {}
