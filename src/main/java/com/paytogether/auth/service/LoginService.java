package com.paytogether.auth.service;

import com.paytogether.auth.controller.response.LoginResponse;

public interface LoginService {

  LoginResponse login(String email, String password);

  void logout();

  boolean sessionExists();
}
