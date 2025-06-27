package com.paytogether.member.service.command;

import com.paytogether.member.entity.Gender;
import lombok.Builder;

@Builder
public record MemberJoinCommand(String email, String password, String address, String phoneNumber, String name, int age, Gender gender) {

}
