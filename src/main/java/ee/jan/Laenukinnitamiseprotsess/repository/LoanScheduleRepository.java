package ee.jan.Laenukinnitamiseprotsess.repository;

import ee.jan.Laenukinnitamiseprotsess.domain.LoanScheduleEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanScheduleRepository extends JpaRepository<LoanScheduleEntry, Long> {
}