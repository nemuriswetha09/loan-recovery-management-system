package com.company.loanrecovery.controller;
import com.company.loanrecovery.common.ApiResponse;
import com.company.loanrecovery.dto.request.ResolutionCreateRequest;
import com.company.loanrecovery.dto.response.ResolutionResponse;
import com.company.loanrecovery.service.ResolutionService;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController @RequestMapping("/api/resolutions")
public class ResolutionController {
  private final ResolutionService service; public ResolutionController(ResolutionService service){this.service=service;}
  @PostMapping public ResponseEntity<ApiResponse<ResolutionResponse>> create(@Valid @RequestBody ResolutionCreateRequest r){return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(true,"Resolution created",service.create(r)));}
  @GetMapping public ResponseEntity<ApiResponse<List<ResolutionResponse>>> all(@RequestParam Long tenantId){return ResponseEntity.ok(new ApiResponse<>(true,"Resolutions fetched",service.all(tenantId)));}
  @GetMapping("/<built-in function id>") public ResponseEntity<ApiResponse<ResolutionResponse>> get(@PathVariable Long id,@RequestParam Long tenantId){return ResponseEntity.ok(new ApiResponse<>(true,"Resolution fetched",service.get(id,tenantId)));}
  @DeleteMapping("/<built-in function id>") public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id,@RequestParam Long tenantId){service.delete(id,tenantId);return ResponseEntity.ok(new ApiResponse<>(true,"Resolution deleted",null));}
}
