package com.paytogether.member.service;

import jakarta.servlet.http.HttpSession;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class HttpSessionManager {
  private static final String LOGIN_MEMBER = "LOGIN_MEMBER";
  private final HttpSession httpSession;

  public void create(Long memberId) {
    httpSession.setAttribute(LOGIN_MEMBER, memberId);
  }

  public void invalidate() {
    httpSession.invalidate();
  }

  public Long getMemberId() {
    return (Long) httpSession.getAttribute(LOGIN_MEMBER);
  }

  public boolean check() {
    return Optional.ofNullable(httpSession.getAttribute(LOGIN_MEMBER)).isPresent();
  }
}
