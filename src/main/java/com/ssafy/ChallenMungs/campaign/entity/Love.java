package com.ssafy.ChallenMungs.campaign.entity;

import com.ssafy.ChallenMungs.user.entity.User;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Love {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int loveId;
    @ManyToOne
    @JoinColumn(name="login_id")
    private User user;

    @ManyToOne
    @JoinColumn(name="campaign_id")
    private Campaign campaign;
}

