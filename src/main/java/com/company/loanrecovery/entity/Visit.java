package com.company.loanrecovery.entity;
   import jakarta.persistence.*;
   import java.time.LocalDateTime;
   import com.company.loanrecovery.entity.enums.VisitStatus;
   @Entity @Table(name="visit_records")
   public class Visit {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @Column(name="tenant_id",nullable=false) private Long tenantId;
    private Long caseId;
private Long employeeId;
private java.time.LocalDateTime visitDate;
private String outcome;
private String remarks;
@Enumerated(EnumType.STRING) @Column(nullable=false) private VisitStatus status=VisitStatus.OPEN;
    @Column(nullable=false) private LocalDateTime createdAt=LocalDateTime.now();
    public Long getId(){return id;} public Long getTenantId(){return tenantId;} public void setTenantId(Long v){tenantId=v;}
    public Long getCaseId(){return caseId;} public void setCaseId(Long v){caseId=v;}
public Long getEmployeeId(){return employeeId;} public void setEmployeeId(Long v){employeeId=v;}
public java.time.LocalDateTime getVisitDate(){return visitDate;} public void setVisitDate(java.time.LocalDateTime v){visitDate=v;}
public String getOutcome(){return outcome;} public void setOutcome(String v){outcome=v;}
public String getRemarks(){return remarks;} public void setRemarks(String v){remarks=v;}
public VisitStatus getStatus(){return status;} public void setStatus(VisitStatus v){status=v;}
    public LocalDateTime getCreatedAt(){return createdAt;}
   }
