package com.ssafy.ChallenMungs.challenge.panel.entity;

import com.ssafy.ChallenMungs.challenge.common.entity.Challenge;
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
    String gameType; //"solo", "team"

    @Column(name = "left_top_lat")
    Double leftTopLat;

    @Column(name = "left_top_lng")
    Double leftTopLng;

    @Column(name = "right_bottom_lat")
    Double rightBottomLat;

    @Column(name = "right_bottom_lng")
    Double rightBottomLng;

    @Column(name = "center_lat")
    Double centerLat;

    @Column(name = "center_lng")
    Double centerLng;
}
