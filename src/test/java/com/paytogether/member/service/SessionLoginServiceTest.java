package com.paytogether.member.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.paytogether.exception.PayTogetherException;
import com.paytogether.member.entity.Gender;
import com.paytogether.member.entity.Member;
import com.paytogether.member.repository.MemberRepository;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

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

  @BeforeEach
  void beforeEach() {
    memberRepository.deleteAll();
  }

  @DisplayName("사용자가 로그인 시도를 성공하면 세션에 있는 사용자의 고유 id와 일치해야 한다")
  @Test
  void login_success() {
    //given
    String password = "1234!678";
    String email = "pay@spring.com";
    Member member = Member.createMember(email, passwordEncoder.encode(password), "name", 100,
        Gender.MALE,
        "address", "01012345678", LocalDateTime.now());
    memberRepository.save(member);

    //when
    loginService.login(email, password);

    //then
    Long memberId = member.getId();
    Long memberIdInSession = (Long) httpSession.getAttribute(TestMemberConstant.LOGIN_MEMBER);
    assertThat(memberId).isEqualTo(memberIdInSession);
  }

  @Test
  void login_fail_byEmailExistsFalse() {
    //given
    String password = "1234!678";
    String email = "pay@spring.com";
    String wrongEmail = "pa@spring.com";
    Member member = Member.createMember(email, password, "name", 100, Gender.MALE,
        "address", "01012345678", LocalDateTime.now());
    memberRepository.save(member);

    //when, then
    assertThatThrownBy(() -> loginService.login(wrongEmail, password))
        .isInstanceOf(PayTogetherException.class);
  }

  @Test
  void login_fail_byPasswordNotMatch() {
    //given
    String password = "1234!678";
    String wrongPassword = "!2345678";
    String email = "pay3@spring.com";
    Member member = Member.createMember(email, passwordEncoder.encode(password), "name", 100,
        Gender.MALE,
        "address", "01012345678", LocalDateTime.now());
    memberRepository.save(member);

    //then
    assertThatThrownBy(() -> loginService.login(email, wrongPassword))
        .isInstanceOf(PayTogetherException.class);
  }

  @Test
  void logout_success() {
    //given
    String password = "1234!678";
    String email = "pay4@spring.com";
    Member member = Member.createMember(email, passwordEncoder.encode(password), "name", 100,
        Gender.MALE,
        "address", "01012345678", LocalDateTime.now());
    memberRepository.save(member);

    //when
    loginService.login(email, password);
    Object login = httpSession.getAttribute(SessionLoginService.LOGIN_MEMBER);
    assertThat(login).isNotNull();

    loginService.logout();

    //then
    Object logout = httpSession.getAttribute(SessionLoginService.LOGIN_MEMBER);
    assertThat(logout).isNull();
  }
}