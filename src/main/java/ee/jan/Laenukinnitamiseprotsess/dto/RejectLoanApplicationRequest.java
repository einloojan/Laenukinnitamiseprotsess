package ee.jan.Laenukinnitamiseprotsess.dto;

import ee.jan.Laenukinnitamiseprotsess.domain.RejectionReason;
import jakarta.validation.constraints.NotNull;

public class RejectLoanApplicationRequest {

    @NotNull
    private RejectionReason reason;
    public RejectionReason getReason() {
        return reason;
    }

    public void setReason(RejectionReason reason) {
        this.reason = reason;
    }
}
