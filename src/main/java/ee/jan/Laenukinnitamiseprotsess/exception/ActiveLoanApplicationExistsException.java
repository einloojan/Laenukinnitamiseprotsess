package ee.jan.Laenukinnitamiseprotsess.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ActiveLoanApplicationExistsException extends RuntimeException {
    public ActiveLoanApplicationExistsException(String message) {

        super(message);
    }
}
