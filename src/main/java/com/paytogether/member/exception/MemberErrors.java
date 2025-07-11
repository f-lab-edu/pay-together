package com.paytogether.member.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MemberErrors {

  MEMBER_NOT_FOUND("M001", HttpStatus.NOT_FOUND, "등록된 회원이 아닙니다"),
  INVALID_INFO("M002", HttpStatus.BAD_REQUEST, "잘못된 정보를 입력했습니다");

  private final String code;
  private final HttpStatus status;
  private final String explain;

}
