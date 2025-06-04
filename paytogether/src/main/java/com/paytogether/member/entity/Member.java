package com.paytogether.member.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
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

  @Column(nullable = false, unique = true, length = 50)
  private String email;

  @Column(nullable = false, length = 255)
  private String password;

  @Column(nullable = true)
  private String name;

  @Column(nullable = true)
  private int age;

  @Enumerated(EnumType.STRING)
  @Column(nullable = true)
  private Gender gender;

  @Column(nullable = false, length = 255)
  private String address;

  @Column(nullable = false, length = 20)
  private String phoneNumber;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private MemberRole role;

  @Column(nullable = false)
  private boolean isActive = true;

  @Column(nullable = false)
  private LocalDateTime createdAt;

  @Column(nullable = true)
  private LocalDateTime deactivatedAt;

  @Builder
  private Member(String email, String password, String name, int age, Gender gender, String address,
      String phoneNumber, MemberRole role, LocalDateTime createdAt) {
    this.email = email;
    this.password = password;
    this.name = name;
    this.age = age;
    this.gender = gender;
    this.address = address;
    this.phoneNumber = phoneNumber;
    this.role = role;
    this.createdAt = createdAt;
  }

  public static Member createMember(String email, String encodedPassword, String name, int age,
      Gender gender, String address, String phoneNumber, LocalDateTime createdAt) {
    return Member.builder()
        .email(email).password(encodedPassword).name(name).age(age).gender(gender)
        .address(address).phoneNumber(phoneNumber).role(MemberRole.MEMBER).createdAt(createdAt)
        .build();
  }

  public void deactivate(LocalDateTime deactivatedAt) {
    this.isActive = false;
    this.deactivatedAt = deactivatedAt;
  }
}
