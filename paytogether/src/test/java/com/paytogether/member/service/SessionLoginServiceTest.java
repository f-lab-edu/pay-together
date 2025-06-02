package com.paytogether.member.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.paytogether.exception.PayTogetherException;
import com.paytogether.member.entity.Gender;
import com.paytogether.member.entity.Member;
import com.paytogether.member.entity.MemberRole;
import com.paytogether.member.repository.MemberRepository;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.DisplayName;
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

  @DisplayName("사용자가 로그인 시도를 성공하면 세션에 있는 사용자의 고유 id와 일치해야 한다")
  @Test
  void login_success() {
    String password = "1234!678";
    String email = "pay@spring.com";
    Member beforeLoginMember = createMember(email, password);
    memberRepository.save(beforeLoginMember);
    Long memberId = beforeLoginMember.getId();

    loginService.login(email, password);

    Long memberIdInSession = (Long) httpSession.getAttribute(TestMemberConstant.LOGIN_MEMBER);
    assertThat(memberId).isEqualTo(memberIdInSession);
  }

  @Test
  void login_fail_byEmailExistsFalse() {
    String password = "1234!678";
    String email = "pay@spring.com";
    String wrongEmail = "pa@spring.com";
    Member beforeLoginMember = createMember(email, password);
    memberRepository.save(beforeLoginMember);

    assertThatThrownBy(() -> loginService.login(wrongEmail, password))
        .isInstanceOf(PayTogetherException.class);
  }

  @Test
  void login_fail_byPasswordNotMatch() {
    String password = "1234!678";
    String wrongPassword = "!2345678";
    String email = "pay@spring.com";
    Member beforeLoginMember = createMember(email, password);
    memberRepository.save(beforeLoginMember);

    assertThatThrownBy(() -> loginService.login(email, wrongPassword))
        .isInstanceOf(PayTogetherException.class);
  }

  @Test
  void logout_success() {
    String password = "1234!678";
    String email = "pay@spring.com";
    Member beforeLoginMember = createMember(email, password);
    memberRepository.save(beforeLoginMember);

    loginService.login(email, password);
    Object login = httpSession.getAttribute(SessionLoginService.LOGIN_MEMBER);
    assertThat(login).isNotNull();

    loginService.logout();

    Object logout = httpSession.getAttribute(SessionLoginService.LOGIN_MEMBER);
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
    return createMember(request, encoded);
  }

  private Member createMember(MemberJoinRequest request, String encodedPassword) {
    return Member.builder()
        .email(request.getEmail()).password(encodedPassword).name(request.getName())
        .age(request.getAge()).gender(request.getGender()).address(request.getAddress())
        .phoneNumber(request.getPhoneNumber()).role(MemberRole.MEMBER)
        .build();
  }
}