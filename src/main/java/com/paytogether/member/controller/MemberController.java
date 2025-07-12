package com.paytogether.member.controller;

import com.paytogether.member.controller.request.MemberJoinRequest;
import com.paytogether.member.controller.response.MemberJoinResponse;
import com.paytogether.member.service.MemberService;
import com.paytogether.member.service.result.MemberJoinResult;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/members")
public class MemberController {

  private final MemberService memberService;

  @PostMapping("/join")
  public MemberJoinResponse join(@RequestBody @Valid MemberJoinRequest request) {
    LocalDateTime now = LocalDateTime.now();
    MemberJoinResult result = memberService.join(MemberJoinRequest.toMemberLoginCommand(request),
        now);
    return MemberJoinResponse.fromMemberLoginResult(result);
  }
}
