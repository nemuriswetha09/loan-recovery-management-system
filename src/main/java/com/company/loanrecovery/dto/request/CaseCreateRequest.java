
package com.company.loanrecovery.dto.request;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CaseCreateRequest {

    @NotNull
    @Positive
    private Long tenantId;

    @NotBlank
    @Size(max = 50)
    private String caseNumber;

    @NotBlank
    @Size(max = 50)
    private String loanAccountNumber;

    @NotBlank
    @Size(min = 3, max = 150)
    private String customerName;

    @NotBlank
    @Pattern(regexp = "^[6-9][0-9]{9}$",
            message = "Enter a valid 10-digit mobile number")
    private String customerMobile;

    private String customerAddress;

    @Pattern(regexp = "^[1-9][0-9]{5}$",
            message = "Enter a valid 6-digit pincode")
    private String pincode;

    @Size(max = 100)
    private String product;

    @NotNull
    @DecimalMin(value = "0.0")
    private BigDecimal tad;

    @NotNull
    @DecimalMin(value = "0.0")
    private BigDecimal pos;

    @NotNull
    @DecimalMin(value = "0.0")
    private BigDecimal emi;

    @NotNull
    @PositiveOrZero
    private Integer dpd;

    @Size(max = 20)
    private String bucket;
}
