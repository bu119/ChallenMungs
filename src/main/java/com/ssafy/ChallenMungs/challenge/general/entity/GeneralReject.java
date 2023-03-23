package com.ssafy.ChallenMungs.challenge.general.entity;

import lombok.*;

import javax.persistence.*;

@Entity(name="general_reject")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GeneralReject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idx")
    int idx;

    @Column(name = "login_id")
    String loginId;

    @Column(name = "board_id")
    int boardId;
}
