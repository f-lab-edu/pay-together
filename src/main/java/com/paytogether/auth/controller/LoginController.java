package com.paytogether.auth.controller;

import com.paytogether.auth.aop.LoginRequired;
import com.paytogether.auth.service.LoginService;
import com.paytogether.auth.controller.request.LoginRequest;
import com.paytogether.auth.controller.response.LoginResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class LoginController {

  private final LoginService loginService;

  @PostMapping("/login")
  public LoginResponse login(@RequestBody @Valid LoginRequest request) {
    return loginService.login(request.email(), request.password());
  }

  @LoginRequired
  @PostMapping("/logout")
  public ResponseEntity<String> logout() {
    loginService.logout();
    return ResponseEntity.ok("success");
  }
}
