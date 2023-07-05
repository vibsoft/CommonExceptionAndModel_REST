package my.interview.exception;

import my.interview.dto.exceptions.BuzzException;
import my.interview.dto.exceptions.FizzBuzzException;
import my.interview.dto.exceptions.FizzException;
import my.interview.dto.exceptions.GlobalError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ServiceExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity handleIllegalArgumentException(IllegalArgumentException ex) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(
            GlobalError.builder()
                .errorReason(ex.getMessage())
                .message(HttpStatus.BAD_REQUEST.name())
                .build());
  }

  @ExceptionHandler(FizzException.class)
  public ResponseEntity handleFizzException(FizzException ex) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(new GlobalError(ex.getMessage(), ex.getErrorReason()));
  }

  @ExceptionHandler(BuzzException.class)
  public ResponseEntity handleBuzzException(BuzzException ex) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(new GlobalError(ex.getMessage(), ex.getErrorReason()));
  }

  @ExceptionHandler(FizzBuzzException.class)
  public ResponseEntity handleFizzBuzzException(FizzBuzzException ex) {
    return ResponseEntity.status(HttpStatus.INSUFFICIENT_STORAGE)
        .body(new GlobalError(ex.getMessage(), ex.getErrorReason()));
  }
}
