package com.ssafy.ChallenMungs.challenge.entity;

import lombok.*;

import javax.persistence.*;

@Entity(name = "game_user_mapping")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChallengeUserMapping {
    @Id
    @Column(name = "idx")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int idx;

    @Column(name = "login_id")
    String loginId;

    @Column(name = "game_id")
    int gameId;

}
