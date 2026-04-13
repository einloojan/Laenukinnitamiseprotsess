package ee.jan.Laenukinnitamiseprotsess.controller;

import ee.jan.Laenukinnitamiseprotsess.domain.LoanApplication;
import ee.jan.Laenukinnitamiseprotsess.dto.CreateLoanApplicationRequest;
import ee.jan.Laenukinnitamiseprotsess.service.LoanApplicationService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
