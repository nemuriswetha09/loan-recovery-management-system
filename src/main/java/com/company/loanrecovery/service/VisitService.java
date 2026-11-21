package com.company.loanrecovery.service;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.company.loanrecovery.entity.Visit;
import com.company.loanrecovery.dto.request.VisitCreateRequest;
import com.company.loanrecovery.dto.response.VisitResponse;
import com.company.loanrecovery.mapper.VisitMapper;
import com.company.loanrecovery.repository.VisitRepository;
@Service public class VisitService {
  private final VisitRepository repo; private final VisitMapper mapper;
  public VisitService(VisitRepository repo,VisitMapper mapper){this.repo=repo;this.mapper=mapper;}
  @Transactional public VisitResponse create(VisitCreateRequest r){return mapper.toResponse(repo.save(mapper.toEntity(r)));}
  @Transactional(readOnly=true) public List<VisitResponse> all(Long tenantId){if(tenantId==null||tenantId<=0)throw new IllegalArgumentException("Valid tenantId is required");return repo.findAllByTenantIdOrderByCreatedAtDesc(tenantId).stream().map(mapper::toResponse).toList();}
  @Transactional(readOnly=true) public VisitResponse get(Long id,Long tenantId){return repo.findByIdAndTenantId(id,tenantId).map(mapper::toResponse).orElseThrow(()->new IllegalArgumentException("Visit not found"));}
  @Transactional public void delete(Long id,Long tenantId){Visit e=repo.findByIdAndTenantId(id,tenantId).orElseThrow(()->new IllegalArgumentException("Visit not found"));repo.delete(e);}
}
