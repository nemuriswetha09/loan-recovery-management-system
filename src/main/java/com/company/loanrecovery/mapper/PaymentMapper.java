package com.company.loanrecovery.mapper;
    import org.springframework.stereotype.Component;
    import com.company.loanrecovery.entity.Payment;
    import com.company.loanrecovery.dto.request.PaymentCreateRequest;
    import com.company.loanrecovery.dto.response.PaymentResponse;
    @Component public class PaymentMapper {
      public Payment toEntity(PaymentCreateRequest r){Payment e=new Payment();e.setTenantId(r.tenantId());e.setCaseId(r.caseId());
e.setAmount(r.amount());
e.setPaymentMode(r.paymentMode());
e.setTransactionReference(r.transactionReference());
e.setPaidAt(r.paidAt());
e.setStatus(r.status());return e;}
      public PaymentResponse toResponse(Payment e){return new PaymentResponse(e.getId(), e.getTenantId(), e.getCaseId(), e.getAmount(), e.getPaymentMode(), e.getTransactionReference(), e.getPaidAt(), e.getStatus(), e.getCreatedAt());}
    }
