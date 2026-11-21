package com.company.loanrecovery.controller;
import com.company.loanrecovery.common.ApiResponse;
import com.company.loanrecovery.repository.*;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
@RestController @RequestMapping("/api/dashboard")
public class DashboardController {
 private final EmployeeRepository employees;
 private final CaseRepository cases;
 private final AssignmentRepository assignments;
 private final VisitRepository visits;
 private final PaymentRepository payments;
 private final ResolutionRepository resolutions;
 public DashboardController(EmployeeRepository e,CaseRepository c,AssignmentRepository a,VisitRepository v,PaymentRepository p,ResolutionRepository r){employees=e;cases=c;assignments=a;visits=v;payments=p;resolutions=r;}
 @GetMapping("/summary")
 public ApiResponse<Map<String,Long>> summary(@RequestParam Long tenantId){
  if(tenantId==null||tenantId<=0) throw new IllegalArgumentException("Valid tenantId is required");
  return new ApiResponse<>(true,"Dashboard summary fetched",
          Map.of("employees",employees.countByTenantId(tenantId),
                  "cases",cases.countByTenantId(tenantId),
                  "assignments",assignments.countByTenantId(tenantId),
                  "visits",visits.countByTenantId(tenantId),
                  "payments",payments.countByTenantId(tenantId),
                  "resolutions",resolutions.countByTenantId(tenantId)));
 }
}
