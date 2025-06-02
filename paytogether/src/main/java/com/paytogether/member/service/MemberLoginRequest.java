package com.paytogether.member.service;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberLoginRequest {

  @NotBlank
  @Email(message = "이메일 형식에 맞게 작성해주세요.")
  private String email;

  @NotBlank
  @Pattern(
      regexp = "^(?=.*[!@#$%^&*(),.?\":{}|<>])[A-Za-z\\d!@#$%^&*(),.?\":{}|<>]{8,20}$",
      message = "8자 이상 20자 이하로 ")
  private String password;
}
