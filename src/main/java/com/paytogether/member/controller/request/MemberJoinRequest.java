package com.paytogether.member.controller.request;

import com.paytogether.member.entity.Gender;
import com.paytogether.member.service.command.MemberJoinCommand;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record MemberJoinRequest(
    @NotBlank
    @Email(message = "이메일 형식에 맞게 작성해주세요.")
    String email,

    @NotBlank
    @Pattern(
        regexp = "^(?=.*[!@#$%^&*(),.?\":{}|<>])[A-Za-z\\d!@#$%^&*(),.?\":{}|<>]{8,20}$",
        message = "8자 이상 20자 이하로 "
    )
    String password,

    @NotBlank
    @Size(min = 5, message = "최소 5자 이상 입력하세요.")
    String address,

    @NotBlank
    @Pattern(regexp = "^[0-9]{10,11}$", message = "휴대폰 번호는 숫자로 10 ~ 11자리로 입력하세요.")
    String phoneNumber,

    String name,
    int age,
    Gender gender
) {

  public static MemberJoinCommand toMemberLoginCommand(MemberJoinRequest request) {
    return MemberJoinCommand.builder()
        .email(request.email())
        .password(request.password())
        .address(request.address())
        .phoneNumber(request.phoneNumber())
        .name(request.name())
        .age(request.age())
        .gender(request.gender())
        .build();
  }
}