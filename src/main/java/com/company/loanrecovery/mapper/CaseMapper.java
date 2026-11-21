
package com.company.loanrecovery.mapper;

import com.company.loanrecovery.dto.request.CaseCreateRequest;
import com.company.loanrecovery.dto.request.CaseUpdateRequest;
import com.company.loanrecovery.dto.response.CaseResponse;
import com.company.loanrecovery.entity.LoanCase;
import org.springframework.stereotype.Component;

@Component
public class CaseMapper {

    // CREATE DTO -> ENTITY
    public LoanCase toEntity(CaseCreateRequest request) {

        LoanCase loanCase = new LoanCase();

        loanCase.setTenantId(request.getTenantId());
        loanCase.setCaseNumber(request.getCaseNumber());
        loanCase.setLoanAccountNumber(request.getLoanAccountNumber());

        loanCase.setCustomerName(request.getCustomerName());
        loanCase.setCustomerMobile(request.getCustomerMobile());
        loanCase.setCustomerAddress(request.getCustomerAddress());
        loanCase.setPincode(request.getPincode());

        loanCase.setProduct(request.getProduct());

        loanCase.setTad(request.getTad());
        loanCase.setPos(request.getPos());
        loanCase.setEmi(request.getEmi());

        loanCase.setDpd(request.getDpd());
        loanCase.setBucket(request.getBucket());

        return loanCase;
    }

    // UPDATE EXISTING ENTITY
    public void updateEntity(
            LoanCase loanCase,
            CaseUpdateRequest request) {

        loanCase.setCustomerName(request.getCustomerName());
        loanCase.setCustomerMobile(request.getCustomerMobile());
        loanCase.setCustomerAddress(request.getCustomerAddress());
        loanCase.setPincode(request.getPincode());

        loanCase.setProduct(request.getProduct());

        loanCase.setTad(request.getTad());
        loanCase.setPos(request.getPos());
        loanCase.setEmi(request.getEmi());

        loanCase.setDpd(request.getDpd());
        loanCase.setBucket(request.getBucket());

        loanCase.setCaseStatus(request.getCaseStatus());
    }

    // ENTITY -> RESPONSE DTO
    public CaseResponse toResponse(LoanCase loanCase) {

        CaseResponse response = new CaseResponse();

        response.setId(loanCase.getId());
        response.setCaseNumber(loanCase.getCaseNumber());
        response.setLoanAccountNumber(loanCase.getLoanAccountNumber());

        response.setCustomerName(loanCase.getCustomerName());
        response.setCustomerMobile(loanCase.getCustomerMobile());
        response.setCustomerAddress(loanCase.getCustomerAddress());
        response.setPincode(loanCase.getPincode());

        response.setProduct(loanCase.getProduct());

        response.setTad(loanCase.getTad());
        response.setPos(loanCase.getPos());
        response.setEmi(loanCase.getEmi());

        response.setDpd(loanCase.getDpd());
        response.setBucket(loanCase.getBucket());

        response.setCaseStatus(loanCase.getCaseStatus());

        response.setCreatedAt(loanCase.getCreatedAt());
        response.setUpdatedAt(loanCase.getUpdatedAt());

        return response;
    }
}
