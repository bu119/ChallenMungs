package com.ssafy.ChallenMungs.challenge.common.entity;

import lombok.*;

import javax.persistence.*;

@Entity(name = "challenge_result")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChallengeResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idx")
    Long idx;

    @Column(name = "login_id")
    String loginId;

    @Column(name = "challenge_id")
    Long challengeId;

    @Column(name = "team_id")
    Integer teamId;

    @Column(name = "panel_count")
    Integer panelCount;

    @Column(name = "rank")
    Integer rank;
}
