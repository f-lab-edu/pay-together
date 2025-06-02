package com.paytogether.member.service;

public interface LoginService {

  LoginResponse login(String email, String password);

  void logout();

  boolean sessionExists();
}
