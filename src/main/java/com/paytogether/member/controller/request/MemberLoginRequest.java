package com.paytogether.member.controller.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record MemberLoginRequest(
    @NotBlank
    @Email(message = "이메일 형식에 맞게 작성해주세요.")
    String email,

    @NotBlank
    @Pattern(
        regexp = "^(?=.*[!@#$%^&*(),.?\":{}|<>])[A-Za-z\\d!@#$%^&*(),.?\":{}|<>]{8,20}$",
        message = "8자 이상 20자 이하로 "
    )
    String password
) {

}
