package ee.jan.Laenukinnitamiseprotsess.service;

import ee.jan.Laenukinnitamiseprotsess.config.LoanProperties;
import ee.jan.Laenukinnitamiseprotsess.domain.LoanApplication;
import ee.jan.Laenukinnitamiseprotsess.domain.LoanApplicationStatus;
import ee.jan.Laenukinnitamiseprotsess.domain.LoanScheduleEntry;
import ee.jan.Laenukinnitamiseprotsess.domain.RejectionReason;
import ee.jan.Laenukinnitamiseprotsess.dto.CreateLoanApplicationRequest;
import ee.jan.Laenukinnitamiseprotsess.dto.LoanApplicationDetailsResponse;
import ee.jan.Laenukinnitamiseprotsess.dto.LoanScheduleEntryResponse;
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

    public LoanApplicationDetailsResponse getApplicationDetails(Long id) {
        LoanApplication application = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Loan application not found"));

        List<LoanScheduleEntry> scheduleEntries =
                loanScheduleRepository.findByLoanApplicationIdOrderByPaymentNumberAsc(id);

        LoanApplicationDetailsResponse response = new LoanApplicationDetailsResponse();
        response.setId(application.getId());
        response.setFirstName(application.getFirstName());
        response.setLastName(application.getLastName());
        response.setPersonalCode(application.getPersonalCode());
        response.setLoanPeriodMonths(application.getLoanPeriodMonths());
        response.setInterestMargin(application.getInterestMargin());
        response.setBaseInterestRate(application.getBaseInterestRate());
        response.setLoanAmount(application.getLoanAmount());
        response.setStatus(application.getStatus());
        response.setCreatedAt(application.getCreatedAt());
        response.setRejectionReason(application.getRejectionReason());

        List<LoanScheduleEntryResponse> schedule = scheduleEntries.stream().map(entry -> {
            LoanScheduleEntryResponse item = new LoanScheduleEntryResponse();
            item.setPaymentNumber(entry.getPaymentNumber());
            item.setPaymentDate(entry.getPaymentDate());
            item.setTotalPayment(entry.getTotalPayment());
            item.setInterestPayment(entry.getInterestPayment());
            item.setPrincipalPayment(entry.getPrincipalPayment());
            item.setRemainingBalance(entry.getRemainingBalance());
            return item;
        }).toList();

        response.setSchedule(schedule);

        return response;
    }

    public LoanApplication approveApplication(Long id) {
        LoanApplication application = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Loan application not found"));

        if (application.getStatus() != LoanApplicationStatus.IN_REVIEW) {
            throw new RuntimeException("Only IN_REVIEW applications can be approved");
        }

        application.setStatus(LoanApplicationStatus.APPROVED);
        application.setRejectionReason(null);

        return repository.save(application);
    }

    public LoanApplication rejectApplication(Long id, RejectionReason reason) {
        LoanApplication application = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Loan application not found"));

        if (application.getStatus() != LoanApplicationStatus.IN_REVIEW) {
            throw new RuntimeException("Only IN_REVIEW applications can be rejected");
        }

        application.setStatus(LoanApplicationStatus.REJECTED);
        application.setRejectionReason(reason);

        return repository.save(application);
    }
}
