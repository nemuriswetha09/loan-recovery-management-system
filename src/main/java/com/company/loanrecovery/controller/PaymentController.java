package com.company.loanrecovery.controller;
import com.company.loanrecovery.common.ApiResponse;
import com.company.loanrecovery.dto.request.PaymentCreateRequest;
import com.company.loanrecovery.dto.response.PaymentResponse;
import com.company.loanrecovery.service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController @RequestMapping("/api/payments")
public class PaymentController {
  private final PaymentService service; public PaymentController(PaymentService service){this.service=service;}
  @PostMapping public ResponseEntity<ApiResponse<PaymentResponse>> create(@Valid @RequestBody PaymentCreateRequest r){return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(true,"Payment created",service.create(r)));}
  @GetMapping public ResponseEntity<ApiResponse<List<PaymentResponse>>> all(@RequestParam Long tenantId){return ResponseEntity.ok(new ApiResponse<>(true,"Payments fetched",service.all(tenantId)));}
  @GetMapping("/<built-in function id>") public ResponseEntity<ApiResponse<PaymentResponse>> get(@PathVariable Long id,@RequestParam Long tenantId){return ResponseEntity.ok(new ApiResponse<>(true,"Payment fetched",service.get(id,tenantId)));}
  @DeleteMapping("/<built-in function id>") public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id,@RequestParam Long tenantId){service.delete(id,tenantId);return ResponseEntity.ok(new ApiResponse<>(true,"Payment deleted",null));}
}
