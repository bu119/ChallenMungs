package com.ssafy.ChallenMungs.challenge.panel.entity;

import com.ssafy.ChallenMungs.challenge.commonEntity.Challenge;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity(name = "panel")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Panel extends Challenge {
    @Column(name = "game_type")
    String gameType;

}
