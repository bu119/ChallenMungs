package com.ssafy.ChallenMungs.challenge.general.entity;

import com.ssafy.ChallenMungs.challenge.common.entity.Challenge;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity(name = "general")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class General extends Challenge {
    @Column(name = "campaign_id")
    String campaignId;

    @Column(name = "success_condition")
    int successCondition;

    @Column(name = "description")
    int description;
}
