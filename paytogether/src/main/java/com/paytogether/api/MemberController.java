package com.paytogether.api;

import com.paytogether.domain.member.service.MemberJoinRequest;
import com.paytogether.domain.member.service.MemberJoinResponse;
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

  @PostMapping("/member/join")
  public MemberJoinResponse join(@RequestBody @Valid MemberJoinRequest request) {
    return memberService.join(request);
  }
}
