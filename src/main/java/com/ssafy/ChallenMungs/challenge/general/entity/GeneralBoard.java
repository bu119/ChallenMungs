package com.ssafy.ChallenMungs.challenge.general.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
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
    Integer boardId;

    @Column(name = "challenge_id")
    Long challengeId;

    @Column(name = "login_id")
    String loginId;

    @Column(name = "picture_uri", length = 2500)
    String pictureUri;

    @Column(name = "reject_count")
    Integer rejectCount;

    @Column(name = "register_day", columnDefinition = "DATE")
    LocalDate registerDay;
}
