package com.ssafy.ChallenMungs.challenge.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name="challenge_participant")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChallengeParticipant {
    @Id
    @Column(name = "login_id")
    String loginId;

    @Column(name = "challenge_id")
    int challengeId;

    @Column(name = "success_count")
    int successCount;
}
