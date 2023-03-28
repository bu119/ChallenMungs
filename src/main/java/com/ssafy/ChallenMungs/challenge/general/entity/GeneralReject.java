package com.ssafy.ChallenMungs.challenge.general.entity;

import com.ssafy.ChallenMungs.user.entity.User;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="login_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)  // 1:N
    @JoinColumn(name="board_id") //Join 기준
    @OnDelete(action = OnDeleteAction.CASCADE)
    private GeneralBoard board;

}
