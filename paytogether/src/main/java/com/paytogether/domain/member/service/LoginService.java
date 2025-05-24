package com.paytogether.domain.member.service;

import jakarta.servlet.http.HttpSession;

public interface LoginService {

  void login(String email, String password);

  void logout();

  boolean sessionExists(HttpSession session);
}
