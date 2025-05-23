package com.paytogether.domain.member.service;

import com.paytogether.domain.member.entity.Gender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class MemberJoinRequest {

  @NotBlank
  @Email(message = "이메일 형식에 맞게 작성해주세요.")
  private String email;

  @NotBlank
  @Pattern(
      regexp = "^(?=.*[!@#$%^&*(),.?\":{}|<>])[A-Za-z\\d!@#$%^&*(),.?\":{}|<>]{8,20}$",
      message = "8자 이상 20자 이하로 ")
  private String password;

  @NotBlank
  @Size(min = 5, message = "최소 5자 이상 입력하세요.")
  private String address;

  @NotBlank
  @Pattern(regexp = "^[0-9]{10,11}$", message = "휴대폰 번호는 숫자로 10 ~ 11자리로 입력하세요.")
  private String phoneNumber;

  private String name;

  private int age;

  private Gender gender;
}
