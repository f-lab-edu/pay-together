package com.paytogether.member.exception;

import java.time.LocalDateTime;

public record MemberErrorResponse(String code, String explain, int status, LocalDateTime now) {

  public static MemberErrorResponse from(MemberErrors errors) {
    return new MemberErrorResponse(errors.getCode(), errors.getExplain(),
        errors.getStatus().value(), LocalDateTime.now());
  }
}
