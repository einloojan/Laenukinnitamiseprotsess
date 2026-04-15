package ee.jan.Laenukinnitamiseprotsess.service;

import ee.jan.Laenukinnitamiseprotsess.config.LoanProperties;
import ee.jan.Laenukinnitamiseprotsess.domain.LoanApplication;
import ee.jan.Laenukinnitamiseprotsess.domain.LoanApplicationStatus;
import ee.jan.Laenukinnitamiseprotsess.domain.LoanScheduleEntry;
import ee.jan.Laenukinnitamiseprotsess.domain.RejectionReason;
import ee.jan.Laenukinnitamiseprotsess.dto.CreateLoanApplicationRequest;
import ee.jan.Laenukinnitamiseprotsess.exception.ActiveLoanApplicationExistsException;
import ee.jan.Laenukinnitamiseprotsess.repository.LoanApplicationRepository;
import ee.jan.Laenukinnitamiseprotsess.repository.LoanScheduleRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service

public class LoanApplicationService {

    private final LoanApplicationRepository repository;
    private final LoanScheduleRepository loanScheduleRepository;
    private final LoanScheduleService loanScheduleService;
    private final LoanProperties loanProperties;
    private final PersonalCodeService personalCodeService;

    public LoanApplicationService(
            LoanApplicationRepository repository,
            LoanScheduleRepository loanScheduleRepository,
            LoanScheduleService loanScheduleService,
            LoanProperties loanProperties,
            PersonalCodeService personalCodeService
    ) {
        this.repository = repository;
        this.loanScheduleRepository = loanScheduleRepository;
        this.loanScheduleService = loanScheduleService;
        this.loanProperties = loanProperties;
        this.personalCodeService = personalCodeService;
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
        entity.setCreatedAt(LocalDateTime.now());

        int age = personalCodeService.calculateAge(request.getPersonalCode());

        if (age > loanProperties.getMaxCustomerAge()) {
            entity.setStatus(LoanApplicationStatus.REJECTED);
            entity.setRejectionReason(RejectionReason.CUSTOMER_TOO_OLD);
            return repository.save(entity);
        }

        entity.setStatus(LoanApplicationStatus.SUBMITTED);
        entity.setRejectionReason(null);

        LoanApplication savedApplication = repository.save(entity);

        List<LoanScheduleEntry> scheduleEntries = loanScheduleService.generateSchedule(savedApplication);
        loanScheduleRepository.saveAll(scheduleEntries);

        savedApplication.setStatus(LoanApplicationStatus.IN_REVIEW);
        return repository.save(savedApplication);
    }
}
