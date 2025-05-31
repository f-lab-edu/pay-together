package com.paytogether.domain.member.api;

import com.paytogether.common.response.ApiResponse;
import com.paytogether.domain.member.service.LoginResponse;
import com.paytogether.domain.member.service.LoginService;
import com.paytogether.domain.member.service.MemberJoinRequest;
import com.paytogether.domain.member.service.MemberJoinResponse;
import com.paytogether.domain.member.service.MemberLoginRequest;
import com.paytogether.domain.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class MemberController {

  private final MemberService memberService;

  private final LoginService loginService;

  @PostMapping("/member/join")
  public ApiResponse<MemberJoinResponse> join(@RequestBody @Valid MemberJoinRequest request) {
    return ApiResponse.createResponse(HttpStatus.OK.value(), memberService.join(request));
  }

  @PostMapping("/member/login")
  public ApiResponse<LoginResponse> login(@RequestBody @Valid MemberLoginRequest request) {
    return ApiResponse.createResponse(
        HttpStatus.OK.value(),
        loginService.login(request.getEmail(), request.getPassword()));
  }

  @PostMapping("/member/logout")
  public ApiResponse<?> logout() {
    loginService.logout();
    return ApiResponse.createResponse(HttpStatus.OK.value(), null);
  }
}
