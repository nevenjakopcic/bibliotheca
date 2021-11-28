package hr.njakopcic.bibliotheca.advice;

import hr.njakopcic.bibliotheca.dto.ApiResponse;
import hr.njakopcic.bibliotheca.exception.BookAlreadyBorrowedException;
import hr.njakopcic.bibliotheca.exception.EmailAlreadyTakenException;
import hr.njakopcic.bibliotheca.exception.MembershipExpiredException;
import hr.njakopcic.bibliotheca.exception.NotFoundException;
import hr.njakopcic.bibliotheca.exception.UsernameAlreadyTakenException;
import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> handleValidationExceptions(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return new ResponseEntity<>(new ApiResponse(errors, "Validation failed."), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<ApiResponse> handleNotFoundException(RuntimeException e) {
        return new ResponseEntity<>(new ApiResponse(e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({UsernameAlreadyTakenException.class,
                        EmailAlreadyTakenException.class,
                        MembershipExpiredException.class,
                        BookAlreadyBorrowedException.class})
    public ResponseEntity<ApiResponse> handleBadRequestException(RuntimeException e) {
        return new ResponseEntity<>(new ApiResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({BadCredentialsException.class})
    public ResponseEntity<ApiResponse> handleBadCredentialsException(BadCredentialsException e) {
        return new ResponseEntity<>(new ApiResponse("Incorrect username and/or password."), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity<ApiResponse> handleAccessDeniedException(AccessDeniedException e) {
        return new ResponseEntity<>(new ApiResponse(e.getMessage()), HttpStatus.FORBIDDEN);
    }
}
