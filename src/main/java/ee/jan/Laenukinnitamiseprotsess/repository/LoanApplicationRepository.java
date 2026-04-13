package ee.jan.Laenukinnitamiseprotsess.repository;

import ee.jan.Laenukinnitamiseprotsess.domain.LoanApplication;
import ee.jan.Laenukinnitamiseprotsess.domain.LoanApplicationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoanApplicationRepository extends JpaRepository<LoanApplication, Long> {
    boolean existsByPersonalCodeAndStatusIn(String personalCode, List<LoanApplicationStatus> statuses);
}