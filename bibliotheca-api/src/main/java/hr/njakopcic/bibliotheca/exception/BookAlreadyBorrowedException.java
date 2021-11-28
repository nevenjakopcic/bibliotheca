package hr.njakopcic.bibliotheca.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BookAlreadyBorrowedException extends RuntimeException {

    public BookAlreadyBorrowedException(final String message) {
        super(message);
    }

    public BookAlreadyBorrowedException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public BookAlreadyBorrowedException(final Throwable cause) {
        super(cause);
    }
}
