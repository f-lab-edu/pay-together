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

  public boolean check(String key) {
    return Optional.ofNullable(httpSession.getAttribute(key)).isPresent();
  }

  public void set(String key, Object obj) {
    httpSession.setAttribute(key, obj);
  }

  public <T> T get(String key, Class<T> type) {
    Object value = httpSession.getAttribute(key);
    return type.cast(value);
  }
}
