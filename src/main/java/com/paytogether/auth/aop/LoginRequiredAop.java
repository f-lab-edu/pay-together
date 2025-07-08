package com.paytogether.auth.aop;

import com.paytogether.auth.exception.AuthErrors;
import com.paytogether.auth.exception.AuthException;
import com.paytogether.auth.service.HttpSessionManager;
import com.paytogether.member.exception.MemberException;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class LoginRequiredAop {

  private final HttpSessionManager httpSessionManager;

  @Before("@annotation(com.paytogether.auth.aop.LoginRequired)")
  public void checkLoginRequired() {
    try {
      httpSessionManager.getMemberId();
    } catch (MemberException e) {
      throw new AuthException(AuthErrors.LOGIN_REQUIRED);
    }
  }
}
