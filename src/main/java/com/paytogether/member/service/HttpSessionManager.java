package com.paytogether.member.service;

import jakarta.servlet.http.HttpSession;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class HttpSessionManager {
  private final HttpSession httpSession;

  public void invalidate() {
    httpSession.invalidate();
  }

  public Long getMemberId(String key) {
    return (Long) httpSession.getAttribute(key);
  }

  public boolean check(String key) {
    return Optional.ofNullable(httpSession.getAttribute(key)).isPresent();
  }

  public void set(String key, Object obj) {
    httpSession.setAttribute(key, obj);
  }

  public Object get(String key) {
    return httpSession.getAttribute(key);
  }
}
