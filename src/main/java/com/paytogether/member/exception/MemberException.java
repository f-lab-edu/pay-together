package com.paytogether.member.exception;

public class MemberException extends RuntimeException {

  private final MemberErrors errors;

  public MemberException(MemberErrors errors) {
    super(errors.getExplain());
    this.errors = errors;
  }
}
