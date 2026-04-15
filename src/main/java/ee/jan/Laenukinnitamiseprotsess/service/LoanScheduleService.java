package ee.jan.Laenukinnitamiseprotsess.service;

import ee.jan.Laenukinnitamiseprotsess.domain.LoanApplication;
import ee.jan.Laenukinnitamiseprotsess.domain.LoanScheduleEntry;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class LoanScheduleService {

    private static final MathContext MATH_CONTEXT = new MathContext(20, RoundingMode.HALF_UP);
    private static final BigDecimal MONTHS_IN_YEAR = BigDecimal.valueOf(12);
    private static final BigDecimal HUNDRED = BigDecimal.valueOf(100);

    public List<LoanScheduleEntry> generateSchedule(LoanApplication application) {
        List<LoanScheduleEntry> schedule = new ArrayList<>();

        BigDecimal principal = application.getLoanAmount();
        int months = application.getLoanPeriodMonths();

        BigDecimal annualInterestRate = application.getInterestMargin()
                .add(application.getBaseInterestRate());

        BigDecimal monthlyInterestRate = annualInterestRate
                .divide(HUNDRED, MATH_CONTEXT)
                .divide(MONTHS_IN_YEAR, MATH_CONTEXT);

        BigDecimal monthlyPayment = calculateMonthlyPayment(principal, monthlyInterestRate, months);
        BigDecimal remainingBalance = principal;

        LocalDate firstPaymentDate = LocalDate.now();

        for (int month = 1; month <= months; month++) {
            BigDecimal interestPayment = remainingBalance.multiply(monthlyInterestRate, MATH_CONTEXT)
                    .setScale(2, RoundingMode.HALF_UP);

            BigDecimal principalPayment = monthlyPayment.subtract(interestPayment)
                    .setScale(2, RoundingMode.HALF_UP);

            if (month == months) {
                principalPayment = remainingBalance.setScale(2, RoundingMode.HALF_UP);
                monthlyPayment = principalPayment.add(interestPayment).setScale(2, RoundingMode.HALF_UP);
            }

            remainingBalance = remainingBalance.subtract(principalPayment)
                    .setScale(2, RoundingMode.HALF_UP);

            if (remainingBalance.compareTo(BigDecimal.ZERO) < 0) {
                remainingBalance = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
            }

            LoanScheduleEntry entry = new LoanScheduleEntry();
            entry.setLoanApplication(application);
            entry.setPaymentNumber(month);
            entry.setPaymentDate(firstPaymentDate.plusMonths(month -1));
            entry.setTotalPayment(monthlyPayment.setScale(2, RoundingMode.HALF_UP));
            entry.setInterestPayment(interestPayment);
            entry.setPrincipalPayment(principalPayment);
            entry.setRemainingBalance(remainingBalance);

            schedule.add(entry);
        }

        return schedule;
    }

    private BigDecimal calculateMonthlyPayment(BigDecimal principal, BigDecimal monthlyInterestRate, int months) {
        if (monthlyInterestRate.compareTo(BigDecimal.ZERO) == 0) {
            return principal.divide(BigDecimal.valueOf(months), 2, RoundingMode.HALF_UP);
        }

        double r = monthlyInterestRate.doubleValue();
        double payment = principal.doubleValue() * r / (1 - Math.pow(1 + r, -months));

        return BigDecimal.valueOf(payment).setScale(2, RoundingMode.HALF_UP);
    }
}
