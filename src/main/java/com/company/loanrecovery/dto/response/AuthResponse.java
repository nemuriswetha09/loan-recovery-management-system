package com.company.loanrecovery.dto.response;
public record AuthResponse(String accessToken,String refreshToken,String tokenType,long expiresIn,Long userId,Long tenantId,String username,String role) {}
