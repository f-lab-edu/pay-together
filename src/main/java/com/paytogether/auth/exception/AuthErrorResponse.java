package com.paytogether.auth.exception;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AuthErrorResponse {

  private final String code;
  private final String explain;
  private final int status;
  private final LocalDateTime now;
}
