package com.company.loanrecovery.service;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.company.loanrecovery.entity.Resolution;
import com.company.loanrecovery.dto.request.ResolutionCreateRequest;
import com.company.loanrecovery.dto.response.ResolutionResponse;
import com.company.loanrecovery.mapper.ResolutionMapper;
import com.company.loanrecovery.repository.ResolutionRepository;
@Service public class ResolutionService {
  private final ResolutionRepository repo; private final ResolutionMapper mapper;
  public ResolutionService(ResolutionRepository repo,ResolutionMapper mapper){this.repo=repo;this.mapper=mapper;}
  @Transactional public ResolutionResponse create(ResolutionCreateRequest r){return mapper.toResponse(repo.save(mapper.toEntity(r)));}
  @Transactional(readOnly=true) public List<ResolutionResponse> all(Long tenantId){if(tenantId==null||tenantId<=0)throw new IllegalArgumentException("Valid tenantId is required");return repo.findAllByTenantIdOrderByCreatedAtDesc(tenantId).stream().map(mapper::toResponse).toList();}
  @Transactional(readOnly=true) public ResolutionResponse get(Long id,Long tenantId){return repo.findByIdAndTenantId(id,tenantId).map(mapper::toResponse).orElseThrow(()->new IllegalArgumentException("Resolution not found"));}
  @Transactional public void delete(Long id,Long tenantId){Resolution e=repo.findByIdAndTenantId(id,tenantId).orElseThrow(()->new IllegalArgumentException("Resolution not found"));repo.delete(e);}
}
