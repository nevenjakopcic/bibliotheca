package hr.njakopcic.bibliotheca.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class MembershipExpiredException extends RuntimeException {

    public MembershipExpiredException(final String message) {
        super(message);
    }

    public MembershipExpiredException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public MembershipExpiredException(final Throwable cause) {
        super(cause);
    }
}
