package com.company.loanrecovery.mapper;
    import org.springframework.stereotype.Component;
    import com.company.loanrecovery.entity.Visit;
    import com.company.loanrecovery.dto.request.VisitCreateRequest;
    import com.company.loanrecovery.dto.response.VisitResponse;
    @Component public class VisitMapper {
      public Visit toEntity(VisitCreateRequest r){Visit e=new Visit();e.setTenantId(r.tenantId());e.setCaseId(r.caseId());
e.setEmployeeId(r.employeeId());
e.setVisitDate(r.visitDate());
e.setOutcome(r.outcome());
e.setRemarks(r.remarks());
e.setStatus(r.status());return e;}
      public VisitResponse toResponse(Visit e){return new VisitResponse(e.getId(), e.getTenantId(), e.getCaseId(), e.getEmployeeId(), e.getVisitDate(), e.getOutcome(), e.getRemarks(), e.getStatus(), e.getCreatedAt());}
    }
