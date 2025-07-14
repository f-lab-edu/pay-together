package com.paytogether.member.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MemberExceptionAdvice {

  @ExceptionHandler(MemberException.class)
  public ResponseEntity<MemberErrorResponse> advice(MemberException e) {
    MemberErrors errors = e.getErrors();
    MemberErrorResponse memberErrorResponse = MemberErrorResponse.from(errors);
    return ResponseEntity.status(errors.getStatus()).body(memberErrorResponse);
  }
}
