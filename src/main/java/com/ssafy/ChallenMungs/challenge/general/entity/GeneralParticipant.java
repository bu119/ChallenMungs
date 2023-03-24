package com.ssafy.ChallenMungs.challenge.general.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name="general_participant")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GeneralParticipant {
    @Id
    @Column(name = "login_id")
    String loginId;

    @Column(name = "challenge_id")
    Long challengeId;

    @Column(name = "success_count")
    Integer successCount;
}
