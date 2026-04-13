package ee.jan.Laenukinnitamiseprotsess.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public class CreateLoanApplicationRequest {

    @NotBlank
    @Size(max = 32)
    private String firstName;

    @NotBlank
    @Size(max = 32)
    private String lastName;

    @NotBlank
    private String personalCode; //isikukoodi kontroll pärast validatoriga nt, hetkel vähemalt notblank et liikuma saada

    @NotNull
    @Min(6)
    @Max(360)
    private Integer loanPeriodMonths;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = true)
    private BigDecimal interestMargin;

    @NotNull
    private BigDecimal baseInterestRate;

    @NotNull
    @DecimalMin(value = "5000.00", inclusive = true)
    private BigDecimal loanAmount;

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
}
