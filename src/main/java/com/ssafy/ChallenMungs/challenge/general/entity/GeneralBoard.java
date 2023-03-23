package com.ssafy.ChallenMungs.challenge.general.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name="general_board")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GeneralBoard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    int boardId;

    @Column(name = "challenge_id")
    int challengeId;

    @Column(name = "picture_uri")
    int pictureUri;

    @Column(name = "login_id")
    String loginId;

    @Column(name = "reject_count")
    int rejectCount;

    @Column(name = "register_day")
    LocalDateTime registerDay;
}
