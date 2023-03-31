package com.ssafy.ChallenMungs.challenge.general.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
//일반챌린지에 등록한 유져의 기록 정보들을 담은 dto 입니다.
public class GeneralBoardHistoryDto {
    private Integer boardId;
    private String user;
    private String name;
    private String profileUrl;
    private LocalDate registerDay;
    private String pictureUrl;
    private Boolean myRejectState;
    private Boolean rejectResult;

//    List<GeneralBoardTodayDto> todayList;

}
