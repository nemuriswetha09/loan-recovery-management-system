package com.company.loanrecovery.mapper;
    import org.springframework.stereotype.Component;
    import com.company.loanrecovery.entity.Resolution;
    import com.company.loanrecovery.dto.request.ResolutionCreateRequest;
    import com.company.loanrecovery.dto.response.ResolutionResponse;
    @Component public class ResolutionMapper {
      public Resolution toEntity(ResolutionCreateRequest r){Resolution e=new Resolution();e.setTenantId(r.tenantId());e.setCaseId(r.caseId());
e.setResolutionType(r.resolutionType());
e.setDescription(r.description());
e.setResolvedAt(r.resolvedAt());
e.setStatus(r.status());return e;}
      public ResolutionResponse toResponse(Resolution e){return new ResolutionResponse(e.getId(), e.getTenantId(), e.getCaseId(), e.getResolutionType(), e.getDescription(), e.getResolvedAt(), e.getStatus(), e.getCreatedAt());}
    }
