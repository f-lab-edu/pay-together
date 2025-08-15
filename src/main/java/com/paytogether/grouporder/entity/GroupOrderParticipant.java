package com.paytogether.grouporder.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class GroupOrderParticipant {

  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  @Column(name = "group_order_participant_id")
  private Long id;

  @Column(nullable = false)
  private Long memberId;

  @Column(nullable = false)
  private Long groupOrderId;

  @Column(nullable = false)
  private int quantity;

  @Column(nullable = false)
  private int totalPrice;

  @Enumerated(value = EnumType.STRING)
  @Column(nullable = false)
  private ParticipantStatus status;
}
