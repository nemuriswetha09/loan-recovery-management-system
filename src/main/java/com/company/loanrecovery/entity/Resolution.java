package com.company.loanrecovery.entity;
   import jakarta.persistence.*;
   import java.time.LocalDateTime;
   import com.company.loanrecovery.entity.enums.ResolutionStatus;
   @Entity @Table(name="case_resolutions")
   public class Resolution {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @Column(name="tenant_id",nullable=false) private Long tenantId;
    private Long caseId;
private String resolutionType;
private String description;
private java.time.LocalDateTime resolvedAt;
@Enumerated(EnumType.STRING) @Column(nullable=false) private ResolutionStatus status=ResolutionStatus.PROPOSED;
    @Column(nullable=false) private LocalDateTime createdAt=LocalDateTime.now();
    public Long getId(){return id;} public Long getTenantId(){return tenantId;} public void setTenantId(Long v){tenantId=v;}
    public Long getCaseId(){return caseId;} public void setCaseId(Long v){caseId=v;}
public String getResolutionType(){return resolutionType;} public void setResolutionType(String v){resolutionType=v;}
public String getDescription(){return description;} public void setDescription(String v){description=v;}
public java.time.LocalDateTime getResolvedAt(){return resolvedAt;} public void setResolvedAt(java.time.LocalDateTime v){resolvedAt=v;}
public ResolutionStatus getStatus(){return status;} public void setStatus(ResolutionStatus v){status=v;}
    public LocalDateTime getCreatedAt(){return createdAt;}
   }
