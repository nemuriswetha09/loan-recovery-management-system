package com.company.loanrecovery.service;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.company.loanrecovery.entity.Payment;
import com.company.loanrecovery.dto.request.PaymentCreateRequest;
import com.company.loanrecovery.dto.response.PaymentResponse;
import com.company.loanrecovery.mapper.PaymentMapper;
import com.company.loanrecovery.repository.PaymentRepository;
@Service public class PaymentService {
  private final PaymentRepository repo; private final PaymentMapper mapper;
  public PaymentService(PaymentRepository repo,PaymentMapper mapper){this.repo=repo;this.mapper=mapper;}
  @Transactional public PaymentResponse create(PaymentCreateRequest r){return mapper.toResponse(repo.save(mapper.toEntity(r)));}
  @Transactional(readOnly=true) public List<PaymentResponse> all(Long tenantId){if(tenantId==null||tenantId<=0)throw new IllegalArgumentException("Valid tenantId is required");return repo.findAllByTenantIdOrderByCreatedAtDesc(tenantId).stream().map(mapper::toResponse).toList();}
  @Transactional(readOnly=true) public PaymentResponse get(Long id,Long tenantId){return repo.findByIdAndTenantId(id,tenantId).map(mapper::toResponse).orElseThrow(()->new IllegalArgumentException("Payment not found"));}
  @Transactional public void delete(Long id,Long tenantId){Payment e=repo.findByIdAndTenantId(id,tenantId).orElseThrow(()->new IllegalArgumentException("Payment not found"));repo.delete(e);}
}
