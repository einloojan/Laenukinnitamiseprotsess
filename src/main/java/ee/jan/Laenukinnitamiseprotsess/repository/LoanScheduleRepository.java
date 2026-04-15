package ee.jan.Laenukinnitamiseprotsess.repository;

import ee.jan.Laenukinnitamiseprotsess.domain.LoanScheduleEntry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoanScheduleRepository extends JpaRepository<LoanScheduleEntry, Long> {
    List<LoanScheduleEntry> findByLoanApplicationIdOrderByPaymentNumberAsc(Long loanApplicationId);
}