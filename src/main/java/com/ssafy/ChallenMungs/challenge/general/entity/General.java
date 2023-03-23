package com.ssafy.ChallenMungs.challenge.general.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity(name = "challenge")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class General {
    @Id
    @Column(name = "challengeId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int challengeID;

    @Column(name = "type")
    int type;

    @Column(name = "start_date")
    LocalDateTime startDate;

    @Column(name = "end_date")
    LocalDateTime endDate;

    @Column(name = "max_num")
    int maxNum;

    @Column(name = "participant_num")
    int participantNum;

    @Column(name = "title")
    String title;

    @Column(name = "entry_fee")
    int entryFee;

    @Column(name = "charity_login_id")
    String charityLoginId;

    @Column(name = "success_condition")
    int successCondition;

    @Column(name = "description")
    int description;
}
