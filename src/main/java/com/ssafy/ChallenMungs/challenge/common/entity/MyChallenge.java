package com.ssafy.ChallenMungs.challenge.common.entity;

import lombok.*;

import javax.persistence.*;

@Entity(name = "my_challenge")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MyChallenge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idx;

    @Column(name = "login_id")
    String loginId;

    @Column(name = "challenge_id")
    Long challengeId;

    @Column(name = "team_id")
    Integer teamId;

    @Column(name = "success_count")
    Integer successCount;

    @Column(name = "success_ratio")
    Integer successRatio;

    @Column(name = "success_result")
    Integer successResult;

}
