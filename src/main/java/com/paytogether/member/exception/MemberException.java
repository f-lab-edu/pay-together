package com.paytogether.member.exception;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MemberException extends RuntimeException{

  private final MemberErrors errors;

}
