package com.ssafy.ChallenMungs.challenge.general.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.joda.time.LocalDateTime;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
//일반챌린지를 만드는데 필요한 정보들을 담은 dto 입니다.
public class GeneralCreateDto {

    // 제목, 시작날짜, 끝날짜, 최대인원, 현재인원, 참가비, 기부처, 성공조건, 설명

    String title;
    LocalDate startDate;
    LocalDate endDate;
    int maxParticipantCount;
    int currentParticipantCount;
    int entryFee;
    int campaignId;
    int successCondition;
    String description;

}
