package com.ssafy.ChallenMungs.campaign.entity;
import com.ssafy.ChallenMungs.user.entity.User;
import lombok.*;
import javax.persistence.*;

@Entity(name="Campaign")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Campaign {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int campaignId;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;
}
