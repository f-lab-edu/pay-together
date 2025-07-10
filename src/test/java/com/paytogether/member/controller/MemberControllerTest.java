package com.paytogether.member.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paytogether.auth.config.WebConfig;
import com.paytogether.auth.intercepter.LoginCheckInterceptor;
import com.paytogether.config.SecurityConfig;
import com.paytogether.member.controller.request.MemberJoinRequest;
import com.paytogether.member.entity.Gender;
import com.paytogether.member.entity.Member;
import com.paytogether.member.entity.MemberRole;
import com.paytogether.member.service.MemberService;
import com.paytogether.member.service.command.MemberJoinCommand;
import com.paytogether.member.service.result.MemberJoinResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@Import({WebConfig.class, SecurityConfig.class})
@WebMvcTest(MemberController.class)
class MemberControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private MemberService memberService;

  @Autowired
  private ObjectMapper objectMapper;

  @MockitoBean
  private LoginCheckInterceptor loginCheckInterceptor;

  @BeforeEach
  void setUp() throws Exception {
    given(loginCheckInterceptor.preHandle(any(), any(), any())).willReturn(true);
  }

  @Test
  void join_success_returnMemberJoinResponse() throws Exception {
    String email = "pay@spring.com";
    String password = "12345!4356";
    MemberJoinRequest request = MemberJoinRequest.builder()
        .age(100)
        .name("pay")
        .gender(Gender.MALE)
        .address("seoul")
        .password(password)
        .email(email)
        .phoneNumber("01012345678")
        .build();
    Member member = createMember(request, password);
    given(memberService.join(any(MemberJoinCommand.class), any())).willReturn(
        MemberJoinResult.fromMember(member));

    mockMvc.perform(post("/members/join")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("email").value(email));
  }

  @Test
  void join_fail_invalidPhoneNumber() throws Exception {
    String wrongNumber = "--01";
    MemberJoinRequest request = MemberJoinRequest.builder()
        .email("test@example.com")
        .password("Password1!")
        .address("서울시 강남구")
        .phoneNumber(wrongNumber)
        .name("홍길동")
        .age(25)
        .gender(Gender.MALE)
        .build();

    mockMvc.perform(post("/members/join")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andDo(print())
        .andExpect(status().isBadRequest());
  }

  private Member createMember(MemberJoinRequest request, String encodedPassword) {
    return Member.builder()
        .email(request.email()).password(encodedPassword).name(request.name())
        .age(request.age()).gender(request.gender()).address(request.address())
        .phoneNumber(request.phoneNumber()).role(MemberRole.MEMBER)
        .build();
  }
}