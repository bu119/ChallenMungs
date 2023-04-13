package com.ssafy.ChallenMungs.challenge.panel.handler;

import lombok.*;

import java.util.ArrayList;

@Getter@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RankVo {
    Integer teamId;
    Integer PanelCount;
    Integer teamRank;
    Integer indiRank;
    String loginId;
}
