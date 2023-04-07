package com.ssafy.ChallenMungs.campaign.entity;

import com.ssafy.ChallenMungs.user.entity.User;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "love")
public class Love {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int loveId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="login_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="campaign_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Campaign campaign;
}

