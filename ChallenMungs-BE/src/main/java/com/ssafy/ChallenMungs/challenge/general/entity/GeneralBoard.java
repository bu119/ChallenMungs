package com.ssafy.ChallenMungs.challenge.general.entity;

import com.ssafy.ChallenMungs.challenge.common.entity.Challenge;
import com.ssafy.ChallenMungs.user.entity.User;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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

    @ManyToOne(fetch = FetchType.LAZY)  // 1:N
    @JoinColumn(name="challenge_id") //Join 기준
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Challenge challenge;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="login_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @Column(name = "picture_url", length = 2500)
    String pictureUrl;

    @Column(name = "reject_count")
    Integer rejectCount;

    @Column(name = "register_day", columnDefinition = "DATE")
    LocalDate registerDay;
}
