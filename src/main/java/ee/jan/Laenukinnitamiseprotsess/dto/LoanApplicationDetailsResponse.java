package ee.jan.Laenukinnitamiseprotsess.dto;

import ee.jan.Laenukinnitamiseprotsess.domain.LoanApplicationStatus;
import ee.jan.Laenukinnitamiseprotsess.domain.RejectionReason;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class LoanApplicationDetailsResponse {

    private Long id;
    private String firstName;
    private String lastName;
    private String personalCode;
    private Integer loanPeriodMonths;
    private BigDecimal interestMargin;
    private BigDecimal baseInterestRate;
    private BigDecimal loanAmount;
    private LoanApplicationStatus status;
    private LocalDateTime createdAt;
    private RejectionReason rejectionReason;
    private List<LoanScheduleEntryResponse> schedule;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPersonalCode() {
        return personalCode;
    }

    public void setPersonalCode(String personalCode) {
        this.personalCode = personalCode;
    }

    public Integer getLoanPeriodMonths() {
        return loanPeriodMonths;
    }

    public void setLoanPeriodMonths(Integer loanPeriodMonths) {
        this.loanPeriodMonths = loanPeriodMonths;
    }

    public BigDecimal getInterestMargin() {
        return interestMargin;
    }

    public void setInterestMargin(BigDecimal interestMargin) {
        this.interestMargin = interestMargin;
    }

    public BigDecimal getBaseInterestRate() {
        return baseInterestRate;
    }

    public void setBaseInterestRate(BigDecimal baseInterestRate) {
        this.baseInterestRate = baseInterestRate;
    }

    public BigDecimal getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(BigDecimal loanAmount) {
        this.loanAmount = loanAmount;
    }

    public LoanApplicationStatus getStatus() {
        return status;
    }

    public void setStatus(LoanApplicationStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public RejectionReason getRejectionReason() {
        return rejectionReason;
    }

    public void setRejectionReason(RejectionReason rejectionReason) {
        this.rejectionReason = rejectionReason;
    }

    public List<LoanScheduleEntryResponse> getSchedule() {
        return schedule;
    }

    public void setSchedule(List<LoanScheduleEntryResponse> schedule) {
        this.schedule = schedule;
    }
}
