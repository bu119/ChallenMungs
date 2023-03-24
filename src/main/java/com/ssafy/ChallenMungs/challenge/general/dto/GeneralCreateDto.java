package com.ssafy.ChallenMungs.challenge.general.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
//일반챌린지를 만드는데 필요한 정보들을 담은 dto 입니다.
public class GeneralCreateDto {
    // 제목, 시작날짜, 끝날짜, 최대인원, 현재인원, 요금
    String title;
    String startDate;
    String endDate;
    int maxParticipantCount;
    int currentParticipantCount;
    int entryFee;

}
