package com.ssafy.ChallenMungs.challenge.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name="challenge_board")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChallengeBoard {
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
