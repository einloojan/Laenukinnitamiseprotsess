package ee.jan.Laenukinnitamiseprotsess.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidPersonalCodeException extends RuntimeException {

    public InvalidPersonalCodeException(String message) {
        super(message);
    }
}