package com.paytogether.api;

import com.paytogether.domain.member.service.LoginService;
import com.paytogether.domain.member.service.MemberJoinRequest;
import com.paytogether.domain.member.service.MemberJoinResponse;
import com.paytogether.domain.member.service.MemberLoginRequest;
import com.paytogether.domain.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class MemberController {

  private final MemberService memberService;

  private final LoginService loginService;

  @PostMapping("/member/join")
  public MemberJoinResponse join(@RequestBody @Valid MemberJoinRequest request) {
    return memberService.join(request);
  }

  @PostMapping("/member/login")
  public void login(@RequestBody @Valid MemberLoginRequest request) {
    loginService.login(request.getEmail(), request.getPassword());
  }

  @PostMapping("/member/logout")
  public void logout() {
    loginService.logout();
  }
}
