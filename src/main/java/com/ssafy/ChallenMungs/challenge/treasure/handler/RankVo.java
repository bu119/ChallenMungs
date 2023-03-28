package com.ssafy.ChallenMungs.challenge.treasure.handler;

import lombok.*;

@Getter@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RankVo {
    Integer teamId;
    Integer point;
    Integer teamRank;
    Object loginId;
}
