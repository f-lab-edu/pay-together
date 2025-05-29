package com.paytogether.api;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paytogether.config.TestSecurityConfig;
import com.paytogether.domain.member.entity.Gender;
import com.paytogether.domain.member.entity.Member;
import com.paytogether.domain.member.entity.MemberRole;
import com.paytogether.domain.member.service.LoginService;
import com.paytogether.domain.member.service.MemberJoinRequest;
import com.paytogether.domain.member.service.MemberJoinResponse;
import com.paytogether.domain.member.service.MemberLoginRequest;
import com.paytogether.domain.member.service.MemberService;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@Import(TestSecurityConfig.class)
@WebMvcTest(MemberController.class)
class MemberControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private MemberService memberService;

  @Autowired
  private ObjectMapper objectMapper;

  @MockitoBean
  private LoginService loginService;

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
    given(memberService.join(any(MemberJoinRequest.class))).willReturn(
        MemberJoinResponse.from(member));

    mockMvc.perform(post("/member/join")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.email").value(email));
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

    mockMvc.perform(post("/member/join")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andDo(print())
        .andExpect(status().isBadRequest());
  }


  @Test
  void login_success() throws Exception {
    MemberLoginRequest request = new MemberLoginRequest("email@spring.com", "!!password");

    mockMvc.perform(post("/member/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andDo(print())
        .andExpect(status().isOk());
  }

  @Test
  void login_fail_invalidEmail() throws Exception {
    MemberLoginRequest request = new MemberLoginRequest(null, "Password1!");

    mockMvc.perform(post("/member/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andDo(print())
        .andExpect(status().isBadRequest());
  }

  @Test
  void logout_success() throws Exception {
    given(loginService.sessionExists(any(HttpSession.class))).willReturn(true);

    mockMvc.perform(post("/member/logout"))
        .andDo(print())
        .andExpect(status().isOk());
  }

  private Member createMember(MemberJoinRequest request, String encodedPassword) {
    return Member.builder()
        .email(request.getEmail()).password(encodedPassword).name(request.getName())
        .age(request.getAge()).gender(request.getGender()).address(request.getAddress())
        .phoneNumber(request.getPhoneNumber()).role(MemberRole.MEMBER)
        .build();
  }
}