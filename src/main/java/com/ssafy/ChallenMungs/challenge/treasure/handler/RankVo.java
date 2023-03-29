package com.ssafy.ChallenMungs.challenge.treasure.handler;

import lombok.*;

import java.util.ArrayList;

@Getter@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RankVo {
    Integer teamId;
    Integer point;
    Integer teamRank;
    String loginId;
    ArrayList<Integer> myTreasureList;
}
