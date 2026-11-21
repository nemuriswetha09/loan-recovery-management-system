package com.company.loanrecovery.controller;
import com.company.loanrecovery.common.ApiResponse;
import com.company.loanrecovery.dto.request.VisitCreateRequest;
import com.company.loanrecovery.dto.response.VisitResponse;
import com.company.loanrecovery.service.VisitService;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController @RequestMapping("/api/visits")
public class VisitController {
  private final VisitService service; public VisitController(VisitService service){this.service=service;}
  @PostMapping public ResponseEntity<ApiResponse<VisitResponse>> create(@Valid @RequestBody VisitCreateRequest r){return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(true,"Visit created",service.create(r)));}
  @GetMapping public ResponseEntity<ApiResponse<List<VisitResponse>>> all(@RequestParam Long tenantId){return ResponseEntity.ok(new ApiResponse<>(true,"Visits fetched",service.all(tenantId)));}
  @GetMapping("/<built-in function id>") public ResponseEntity<ApiResponse<VisitResponse>> get(@PathVariable Long id,@RequestParam Long tenantId){return ResponseEntity.ok(new ApiResponse<>(true,"Visit fetched",service.get(id,tenantId)));}
  @DeleteMapping("/<built-in function id>") public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id,@RequestParam Long tenantId){service.delete(id,tenantId);return ResponseEntity.ok(new ApiResponse<>(true,"Visit deleted",null));}
}
