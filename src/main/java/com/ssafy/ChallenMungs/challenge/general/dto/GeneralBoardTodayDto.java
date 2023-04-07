package com.ssafy.ChallenMungs.challenge.general.dto;

import com.ssafy.ChallenMungs.campaign.dto.ContentDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.joda.time.LocalDateTime;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
// 오늘 생성된 일반챌린지 목록을 정보들을 담은 dto 입니다.
public class GeneralBoardTodayDto {
    private Integer boardId;
    private String user;
    private String name;
    private String profileUrl;
    private String pictureUrl;
    private Boolean myRejectState;


//    List<GeneralBoardTodayDto> todayList;
}
