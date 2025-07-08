package com.paytogether.auth;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paytogether.auth.controller.LoginController;
import com.paytogether.auth.controller.request.LoginRequest;
import com.paytogether.auth.controller.response.LoginResponse;
import com.paytogether.auth.service.LoginService;
import com.paytogether.config.TestSecurityConfig;
import com.paytogether.member.service.TestMemberConstant;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@Import(TestSecurityConfig.class)
@WebMvcTest(LoginController.class)
public class LoginControllerTest {

  @Autowired
  private MockMvc mockMvc;


  @Autowired
  private ObjectMapper objectMapper;

  @MockitoBean
  private LoginService loginService;

  @Test
  void login_success() throws Exception {
    String email = "email@spring.com";
    String password = "!!password";
    LoginRequest request = new LoginRequest(email, password);
    given(loginService.login(email, password)).willReturn(LoginResponse.of(email, false));

    mockMvc.perform(post("/auth/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("email").value(email));
  }

  @Test
  void login_fail_invalidEmail() throws Exception {
    LoginRequest request = new LoginRequest(null, "Password1!");

    mockMvc.perform(post("/auth/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andDo(print())
        .andExpect(status().isBadRequest());
  }

  @Test
  void logout_success() throws Exception {
    MockHttpSession session = new MockHttpSession();
    session.setAttribute(TestMemberConstant.LOGIN_MEMBER, 1L);

    mockMvc.perform(post("/auth/logout").session(session))
        .andDo(print())
        .andExpect(status().isOk());
  }
}
