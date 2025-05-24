package com.paytogether.domain.member.service;

import static com.paytogether.domain.member.service.SessionLoginService.LOGIN_MEMBER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.paytogether.domain.member.entity.Gender;
import com.paytogether.domain.member.entity.Member;
import com.paytogether.domain.member.repository.MemberRepository;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest
class SessionLoginServiceTest {

  @Autowired
  private HttpSession httpSession;

  @Autowired
  private LoginService loginService;

  @Autowired
  private MemberRepository memberRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Test
  void login_success() {
    String password = "1234!678";
    String email = "pay@spring.com";
    Member beforeLoginMember = createMember(email, password);
    memberRepository.save(beforeLoginMember);

    loginService.login(email, password);

    Long afterLoginMemberId = (Long) httpSession.getAttribute(LOGIN_MEMBER);
    assertThat(beforeLoginMember.getId()).isEqualTo(afterLoginMemberId);
  }

  @Test
  void login_fail_byEmailExistsFalse() {
    String password = "1234!678";
    String email = "pay@spring.com";
    String wrongEmail = "pa@spring.com";
    Member beforeLoginMember = createMember(email, password);
    memberRepository.save(beforeLoginMember);

    assertThatThrownBy(() -> loginService.login(wrongEmail, password))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void login_fail_byPasswordNotMatch() {
    String password = "1234!678";
    String wrongPassword = "!2345678";
    String email = "pay@spring.com";
    Member beforeLoginMember = createMember(email, password);
    memberRepository.save(beforeLoginMember);

    assertThatThrownBy(() -> loginService.login(email, wrongPassword))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void logout_success() {
    String password = "1234!678";
    String email = "pay@spring.com";
    Member beforeLoginMember = createMember(email, password);
    memberRepository.save(beforeLoginMember);

    loginService.login(email, password);
    Object login = httpSession.getAttribute(LOGIN_MEMBER);
    assertThat(login).isNotNull();

    loginService.logout();

    Object logout = httpSession.getAttribute(LOGIN_MEMBER);
    assertThat(logout).isNull();
  }

  private Member createMember(String email, String password) {
    MemberJoinRequest request = MemberJoinRequest.builder()
        .age(100)
        .name("pay")
        .gender(Gender.MALE)
        .address("seoul")
        .password(password)
        .email(email)
        .phoneNumber("01012345678")
        .build();
    String encoded = passwordEncoder.encode(password);
    return Member.createMember(request, encoded);
  }
}