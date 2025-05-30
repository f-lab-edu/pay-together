package com.paytogether.domain.member.service;

public interface LoginService {

  void login(String email, String password);

  void logout();

  boolean sessionExists();
}
