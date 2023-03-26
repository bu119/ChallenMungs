package com.ssafy.ChallenMungs.challenge.general.entity;

import lombok.*;

import javax.persistence.*;

@Entity(name="general_participant")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GeneralParticipant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long participantId;

    @Column(name = "login_id")
    String loginId;

    @Column(name = "challenge_id")
    Long challengeId;

    @Column(name = "success_count")
    Integer successCount;
}
