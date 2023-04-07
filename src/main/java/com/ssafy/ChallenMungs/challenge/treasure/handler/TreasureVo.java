package com.ssafy.ChallenMungs.challenge.treasure.handler;

import lombok.*;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TreasureVo {
    Integer idx;
    Double lat;
    Double lng;
    Integer point;
    Boolean isOpened;
    Boolean inPocket;
    Boolean type; // true: 보물, false: 폭탄
}
