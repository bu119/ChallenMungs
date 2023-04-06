package com.ssafy.ChallenMungs.challenge.common.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

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

    @Column(name = "start_date", columnDefinition = "DATE")
    private LocalDate startDate;

    @Column(name = "end_date", columnDefinition = "DATE")
    private LocalDate endDate;

    @Column(name = "max_participant_count")
    private Integer maxParticipantCount;

    @Column(name = "current_participant_count")
    private Integer currentParticipantCount;

    @Column(name = "entry_fee")
    Integer entryFee;

    @Column(name = "status") // 0: 아직 시작안함, 1: 진행중, 2: 끝남
    Integer status;

    // 일반챌린지 요소
    @Column(name = "campaign_id")
    Integer campaignId;

    @Column(name = "success_condition")
    Integer successCondition;

    @Column(name = "description")
    String description;

    @Column(name = "period")
    Long period;

    // 판넬뒤집기 요소
    @Column(name = "game_type")
    Integer gameType; //1: 개인, 2: 팀

    @Column(name = "center_lat")
    Double centerLat;

    @Column(name = "center_lng")
    Double centerLng;

    @Column(name = "cell_d")
    Integer cellD;

    @Column(name = "max_lat")
    Double maxLat;

    @Column(name = "min_lat")
    Double minLat;

    @Column(name = "max_lng")
    Double maxLng;

    @Column(name = "min_lng")
    Double minLng;

    @Column(name = "cell_size")
    Double cellSize;

    @Column(name = "map_size")
    Double map_size;
}
