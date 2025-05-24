package com.paytogether.domain.member.entity;

import com.paytogether.domain.member.service.MemberJoinRequest;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Member {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "member_id")
  private Long id;

  private String email;

  private String password;

  private String name;

  private int age;

  @Enumerated(EnumType.STRING)
  private Gender gender;

  private String address;

  private String phoneNumber;

  @Enumerated(EnumType.STRING)
  private MemberRole role;

  private boolean isActive = true;

  @Builder
  private Member(String email, String password, String name, int age, Gender gender, String address,
      String phoneNumber, MemberRole role) {
    this.email = email;
    this.password = password;
    this.name = name;
    this.age = age;
    this.gender = gender;
    this.address = address;
    this.phoneNumber = phoneNumber;
    this.role = role;
  }

  public static Member createMember(MemberJoinRequest request, String encodedPassword) {
    return Member.builder()
        .email(request.getEmail())
        .password(encodedPassword)
        .name(request.getName())
        .age(request.getAge())
        .gender(request.getGender())
        .address(request.getAddress())
        .phoneNumber(request.getPhoneNumber())
        .role(MemberRole.MEMBER)
        .build();
  }
}
