package com.paytogether.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import com.paytogether.common.api.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(annotations = RestController.class)
public class ControllerAdvice {

  @ResponseStatus(code = INTERNAL_SERVER_ERROR)
  @ExceptionHandler(Exception.class)
  public ErrorResponse error(Exception e) {
    log.error("예외 발생 = {}", e.getMessage(), e);
    return new ErrorResponse(
        INTERNAL_SERVER_ERROR.value(),
        INTERNAL_SERVER_ERROR.getReasonPhrase());
  }

  @ResponseStatus(code = BAD_REQUEST)
  @ExceptionHandler(PayTogetherException.class)
  public ErrorResponse payTogetherException(PayTogetherException e) {
    log.error("예외 발생 = {}, {}", e.getMessage(), e.getError().getErrorMessage(), e);
    return new ErrorResponse(
        BAD_REQUEST.value(),
        e.getError().getErrorMessage());
  }

  @ResponseStatus(code = BAD_REQUEST)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ErrorResponse validationException(MethodArgumentNotValidException e) {
    log.error("예외 발생 = {}", e.getMessage(), e);
    return new ErrorResponse(
        BAD_REQUEST.value(),
        e.getMessage());
  }
}
