
package com.company.loanrecovery.dto.request;

import com.company.loanrecovery.entity.enums.CaseStatus;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CaseUpdateRequest {

    @NotBlank
    @Size(min = 3, max = 150)
    private String customerName;

    @NotBlank
    @Pattern(regexp = "^[6-9][0-9]{9}$")
    private String customerMobile;

    private String customerAddress;

    @Pattern(regexp = "^[1-9][0-9]{5}$")
    private String pincode;

    @Size(max = 100)
    private String product;

    @NotNull
    @DecimalMin("0.0")
    private BigDecimal tad;

    @NotNull
    @DecimalMin("0.0")
    private BigDecimal pos;

    @NotNull
    @DecimalMin("0.0")
    private BigDecimal emi;

    @NotNull
    @PositiveOrZero
    private Integer dpd;

    @Size(max = 20)
    private String bucket;

    @NotNull
    private CaseStatus caseStatus;
}
