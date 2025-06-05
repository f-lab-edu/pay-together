package com.paytogether.member.service.command;

import com.paytogether.member.entity.Gender;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberLoginCommand {

  private String email;

  private String password;

  private String address;

  private String phoneNumber;

  private String name;

  private int age;

  private Gender gender;

}
