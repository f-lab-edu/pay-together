package com.paytogether.member.service;

import com.paytogether.member.controller.response.LoginResponse;

public interface LoginService {

  LoginResponse login(String email, String password);

  void logout();

  boolean sessionExists();
}
