package com.paytogether.member.api;

import com.paytogether.common.api.response.ApiResponse;
import com.paytogether.member.api.response.LoginResponse;
import com.paytogether.member.service.LoginService;
import com.paytogether.member.api.request.MemberJoinRequest;
import com.paytogether.member.api.response.MemberJoinResponse;
import com.paytogether.member.api.request.MemberLoginRequest;
import com.paytogether.member.service.result.MemberJoinResult;
import com.paytogether.member.service.MemberService;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
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
    LocalDateTime now = LocalDateTime.now();
    MemberJoinResult result = memberService.join(MemberJoinRequest.toMemberLoginCommand(request), now);
    return ApiResponse.createResponse(HttpStatus.OK.value(), MemberJoinResponse.fromMemberLoginResult(result));
  }

  @PostMapping("/member/login")
  public ApiResponse<LoginResponse> login(@RequestBody @Valid MemberLoginRequest request) {
    return ApiResponse.createResponse(HttpStatus.OK.value(), loginService.login(request.getEmail(), request.getPassword()));
  }

  @PostMapping("/member/logout")
  public ApiResponse<?> logout() {
    loginService.logout();
    return ApiResponse.createResponse(HttpStatus.OK.value(), null);
  }
}
