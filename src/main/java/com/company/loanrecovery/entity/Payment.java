package com.company.loanrecovery.entity;
   import jakarta.persistence.*;
   import java.time.LocalDateTime;
   import com.company.loanrecovery.entity.enums.PaymentStatus;
   @Entity @Table(name="payments")
   public class Payment {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column(name="tenant_id",nullable=false)
    private Long tenantId;
    private Long caseId;
private java.math.BigDecimal amount;
private String paymentMode;
private String transactionReference;
private java.time.LocalDateTime paidAt;
@Enumerated(EnumType.STRING) @Column(nullable=false)
private PaymentStatus status=PaymentStatus.PENDING;
    @Column(nullable=false) private LocalDateTime createdAt=LocalDateTime.now();
    public Long getId(){
        return id;
    }
    public Long getTenantId(){
        return tenantId;
    }
    public void setTenantId(Long v){
        tenantId=v;
    }
    public Long getCaseId(){
        return caseId;
    } public void setCaseId(Long v){
        caseId=v;
    }
public java.math.BigDecimal getAmount(){
        return amount;
    } public void setAmount(java.math.BigDecimal v){
        amount=v;
    }
public String getPaymentMode(){
        return paymentMode;
    }
    public void setPaymentMode(String v){
        paymentMode=v;
    }
public String getTransactionReference(){
        return transactionReference;
    }
    public void setTransactionReference(String v){
        transactionReference=v;
    }
public java.time.LocalDateTime getPaidAt(){return paidAt;
    }
    public void setPaidAt(java.time.LocalDateTime v){
        paidAt=v;
    }
public PaymentStatus getStatus(){
        return status;
    }
    public void setStatus(PaymentStatus v){
        status=v;
    }
    public LocalDateTime getCreatedAt(){return createdAt;}
   }
