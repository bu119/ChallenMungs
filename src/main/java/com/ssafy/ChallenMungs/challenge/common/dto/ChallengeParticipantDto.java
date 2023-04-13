package com.ssafy.ChallenMungs.challenge.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
// 챌린지 참가자 목록 정보를 가져옵니다.
public class ChallengeParticipantDto {

    private Long myChallengeId;;
    private Long challengeId;;
    private String user;
    private String name;
    private String profileUrl;
    private Integer successCount;

}
