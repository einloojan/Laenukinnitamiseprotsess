package ee.jan.Laenukinnitamiseprotsess.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class LoanScheduleEntryResponse {

    private Integer paymentNumber;
    private LocalDate paymentDate;
    private BigDecimal totalPayment;
    private BigDecimal interestPayment;
    private BigDecimal principalPayment;
    private BigDecimal remainingBalance;

    public Integer getPaymentNumber() {
        return paymentNumber;
    }

    public void setPaymentNumber(Integer paymentNumber) {
        this.paymentNumber = paymentNumber;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public BigDecimal getTotalPayment() {
        return totalPayment;
    }

    public void setTotalPayment(BigDecimal totalPayment) {
        this.totalPayment = totalPayment;
    }

    public BigDecimal getInterestPayment() {
        return interestPayment;
    }

    public void setInterestPayment(BigDecimal interestPayment) {
        this.interestPayment = interestPayment;
    }

    public BigDecimal getPrincipalPayment() {
        return principalPayment;
    }

    public void setPrincipalPayment(BigDecimal principalPayment) {
        this.principalPayment = principalPayment;
    }

    public BigDecimal getRemainingBalance() {
        return remainingBalance;
    }

    public void setRemainingBalance(BigDecimal remainingBalance) {
        this.remainingBalance = remainingBalance;
    }
}
