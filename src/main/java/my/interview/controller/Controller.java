package my.interview.controller;

import lombok.extern.slf4j.Slf4j;
import my.interview.dto.*;
import my.interview.dto.exceptions.BuzzException;
import my.interview.dto.exceptions.FizzBuzzException;
import my.interview.dto.exceptions.FizzException;
import my.interview.enums.FizzBuzzEnum;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@Slf4j
public class Controller {

  public static final String QUERY_PARAM_FORMAT = "format";
  public static final String QUERY_PARAM_FORMAT_SHORT = "short";
  public static final String QUERY_PARAM_FORMAT_FULL = "full";
  public static final String PATH_PARAM_CODE = "code";

  @RequestMapping(
      value = "/controller_advice/{code}",
      method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<FizzBuzzResponse> getExceptionSuccessResponse(
      @PathVariable(PATH_PARAM_CODE) String code)
      throws FizzException, BuzzException, FizzBuzzException {
    log.info("CONTROLLER - getExceptionSuccessResponse - code: {}", code);

    if (FizzBuzzEnum.FIZZ.getValue().equals(code)) {
      throw new FizzException(
          "Fizz Exception has been thrown", HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
    } else if (FizzBuzzEnum.BUZZ.getValue().equals(code)) {
      throw new BuzzException(
          "Buzz Exception has been thrown", HttpStatus.BAD_REQUEST.getReasonPhrase());
    } else if (FizzBuzzEnum.FIZZBUZZ.getValue().equals(code)) {
      throw new FizzBuzzException(
          "FizzBuzz Exception has been thrown", HttpStatus.INSUFFICIENT_STORAGE.getReasonPhrase());
    }

    FizzBuzzResponse success =
        new FizzBuzzResponse("Successfully completed fizzbuzz test", HttpStatus.OK.value());

    return ResponseEntity.ok(success);
  }

  @GetMapping(value = "/health", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Health> getHealthResponse(@RequestParam(QUERY_PARAM_FORMAT) String format)
      throws FizzException, BuzzException, FizzBuzzException {
    log.info("CONTROLLER - getHealthResponse - format: {}", format);

    if (QUERY_PARAM_FORMAT_SHORT.equals(format)) {
      Health shortHealth =
          Health.builder()
              .status(HttpStatus.OK.getReasonPhrase())
              .message(QUERY_PARAM_FORMAT_SHORT)
              .build();

      return ResponseEntity.ok(shortHealth);
    } else if (QUERY_PARAM_FORMAT_FULL.equals(format)) {
      Health fullHealth =
          Health.builder()
              .status(HttpStatus.OK.getReasonPhrase())
              .message(QUERY_PARAM_FORMAT_FULL)
              .dateTime(LocalDateTime.parse("2019-12-31T23:59:59"))
              .build();

      return ResponseEntity.ok(fullHealth);
    } else {
      throw new IllegalArgumentException("Query parameter not valid");
    }
  }
}
