package com.paytogether.member.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.paytogether.member.service.MemberJoinRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.Set;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class MemberJoinRequestValidationTest {

  private Validator validator;

  @BeforeEach
  void setUp() {
    ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    validator = validatorFactory.getValidator();
  }

  @Test
  void validEmailNull_Exception() {
    MemberJoinRequest request = MemberJoinRequest.builder()
        .email(null)
        .phoneNumber("0101234567")
        .address("012345")
        .password("012345!67")
        .build();

    Set<ConstraintViolation<MemberJoinRequest>> violations = validator.validate(request);

    assertThat(violations).hasSize(1);
    assertThat(violations)
        .extracting("message")
        .contains("공백일 수 없습니다");
  }

  @Test
  void validEmailBlank_Exception() {
    //given
    MemberJoinRequest request = MemberJoinRequest.builder()
        .email("")
        .phoneNumber("0101234567")
        .address("012345")
        .password("012345!67")
        .build();

    //when
    Set<ConstraintViolation<MemberJoinRequest>> violations = validator.validate(request);

    //then
    SoftAssertions softly = new SoftAssertions();
    softly.assertThat(violations).hasSize(1);
    softly.assertThat(violations)
        .extracting("message")
        .contains("공백일 수 없습니다");
    softly.assertAll();
  }

  @ValueSource(strings = {"pay", "pay@", "@pay.com"})
  @ParameterizedTest
  void validEmailForm_Exception(String invalidEmail) {
    //given
    MemberJoinRequest request = MemberJoinRequest.builder()
        .email(invalidEmail)
        .phoneNumber("0101234567")
        .address("012345")
        .password("012345!67")
        .build();

    //when
    Set<ConstraintViolation<MemberJoinRequest>> violations = validator.validate(request);

    //then
    SoftAssertions softly = new SoftAssertions();
    softly.assertThat(violations).hasSize(1);
    softly.assertThat(violations)
        .extracting("message")
        .contains("이메일 형식에 맞게 작성해주세요.");
    softly.assertAll();
  }


  @ValueSource(strings = {
      "short1!", // 8자 미만
      "NoSpecial1", // 특수문자 없음
      "NoNumber!", // 숫자 없음
      "verylongpassword1234567890!" // 20자 초과
  })
  @ParameterizedTest
  void validPasswordPattern_Exception(String invalidPassword) {
    //given
    MemberJoinRequest request = MemberJoinRequest.builder()
        .email(invalidPassword)
        .phoneNumber("0101234567")
        .address("012345")
        .password("012345!67")
        .build();

    //when
    Set<ConstraintViolation<MemberJoinRequest>> violations = validator.validate(request);

    //then
    assertThat(violations).hasSize(1);
  }
}