package com.ssafy.ChallenMungs.challenge.commonEntity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Challenge {
    @Id @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "challenge_id")
    private Long challengeId;

    @Column(name = "title")
    private String title;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @Column(name = "max_participant_count")
    private int maxParticipantCount;

    @Column(name = "current_participant_count")
    private int currentParticipantCount;

    @Column(name = "entry_fee")
    int entryFee;

}
