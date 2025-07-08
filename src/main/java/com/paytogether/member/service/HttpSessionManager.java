package com.paytogether.member.service;

import com.paytogether.member.exception.MemberErrors;
import com.paytogether.member.exception.MemberException;
import jakarta.servlet.http.HttpSession;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class HttpSessionManager {

  private static final String LOGIN_MEMBER = "LOGIN_MEMBER";
  private final HttpSession httpSession;

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
    if (value == null) {
      throw new MemberException(MemberErrors.MEMBER_NOT_FOUND);
    }
    return Long.parseLong(value.toString());
  }
}
