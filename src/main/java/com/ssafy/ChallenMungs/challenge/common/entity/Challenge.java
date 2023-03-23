package com.ssafy.ChallenMungs.challenge.common.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity (name = "challenge")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Challenge {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "challenge_id")
    private Long challengeId;

    @Column(name = "challenge_type")
    private Integer challengeType;

    @Column(name = "title")
    private String title;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @Column(name = "max_participant_count")
    private Integer maxParticipantCount;

    @Column(name = "current_participant_count")
    private Integer currentParticipantCount;

    @Column(name = "entry_fee")
    Integer entryFee;

    // 일반챌린지 요소
    @Column(name = "campaign_id")
    Integer campaignId;

    @Column(name = "success_condition")
    Integer successCondition;

    @Column(name = "description")
    String description;

    // 판넬뒤집기 요소
    @Column(name = "game_type")
    String gameType; //"solo", "team"

    // 판넬, 보물 요소
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
