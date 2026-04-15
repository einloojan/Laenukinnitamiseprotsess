package ee.jan.Laenukinnitamiseprotsess.controller;

import ee.jan.Laenukinnitamiseprotsess.domain.LoanApplication;
import ee.jan.Laenukinnitamiseprotsess.dto.CreateLoanApplicationRequest;
import ee.jan.Laenukinnitamiseprotsess.dto.LoanApplicationDetailsResponse;
import ee.jan.Laenukinnitamiseprotsess.dto.RejectLoanApplicationRequest;
import ee.jan.Laenukinnitamiseprotsess.service.LoanApplicationService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/loan-applications")

public class LoanApplicationController {

    private final LoanApplicationService service;

    public LoanApplicationController(LoanApplicationService service) {
        this.service = service;
    }

    @PostMapping
    public LoanApplication create(@Valid @RequestBody CreateLoanApplicationRequest request) {
        return service.createApplication(request);
    }

    @GetMapping("/{id}")
    public LoanApplicationDetailsResponse getById(@PathVariable Long id) {
        return service.getApplicationDetails(id);
    }

    @PostMapping("/{id}/approve")
    public LoanApplication approve(@PathVariable Long id) {
        return service.approveApplication(id);
    }

    @PostMapping("/{id}/reject")
    public LoanApplication reject(@PathVariable Long id,
                                  @Valid @RequestBody RejectLoanApplicationRequest request) {
        return service.rejectApplication(id, request.getReason());
    }
}
