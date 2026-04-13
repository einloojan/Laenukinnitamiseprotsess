package ee.jan.Laenukinnitamiseprotsess.service;

import ee.jan.Laenukinnitamiseprotsess.domain.LoanApplication;
import ee.jan.Laenukinnitamiseprotsess.domain.LoanApplicationStatus;
import ee.jan.Laenukinnitamiseprotsess.dto.CreateLoanApplicationRequest;
import ee.jan.Laenukinnitamiseprotsess.exception.ActiveLoanApplicationExistsException;
import ee.jan.Laenukinnitamiseprotsess.repository.LoanApplicationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service

public class LoanApplicationService {

    private final LoanApplicationRepository repository;

    public LoanApplicationService(LoanApplicationRepository repository) {
        this.repository = repository;
    }

    public LoanApplication createApplication(CreateLoanApplicationRequest request) {
        boolean hasActive = repository.existsByPersonalCodeAndStatusIn(
                request.getPersonalCode(),
                List.of(LoanApplicationStatus.SUBMITTED, LoanApplicationStatus.IN_REVIEW)
        );

        if (hasActive) {
            throw new ActiveLoanApplicationExistsException("Active loan application already exists");
        }

        LoanApplication entity = new LoanApplication();
        entity.setFirstName(request.getFirstName());
        entity.setLastName(request.getLastName());
        entity.setPersonalCode(request.getPersonalCode());
        entity.setLoanPeriodMonths(request.getLoanPeriodMonths());
        entity.setInterestMargin(request.getInterestMargin());
        entity.setBaseInterestRate(request.getBaseInterestRate());
        entity.setLoanAmount(request.getLoanAmount());

        entity.setStatus(LoanApplicationStatus.SUBMITTED);
        entity.setCreatedAt(LocalDateTime.now());

        return repository.save(entity);
    }
}
