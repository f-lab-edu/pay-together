package com.paytogether.member.service;

import com.paytogether.exception.PayTogetherErrors;
import com.paytogether.exception.PayTogetherException;
import jakarta.servlet.http.HttpSession;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class HttpSessionManager {

  private final HttpSession httpSession;
  private static final String LOGIN_MEMBER = "LOGIN_MEMBER";

  public void invalidate() {
    httpSession.invalidate();
  }

  public boolean check(String key) {
    return Optional.ofNullable(httpSession.getAttribute(key)).isPresent();
  }

  public void set(String key, Long id) {
    httpSession.setAttribute(key, id.toString());
  }

  public Long getMemberId() {
    Object value = httpSession.getAttribute(LOGIN_MEMBER);
    if (value == null) throw new PayTogetherException(PayTogetherErrors.MEMBER_NOT_FOUND);
    return Long.parseLong(value.toString());
  }
}
