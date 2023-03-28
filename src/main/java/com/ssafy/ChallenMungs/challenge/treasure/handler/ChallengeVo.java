package com.ssafy.ChallenMungs.challenge.treasure.handler;

import lombok.*;
import org.springframework.web.socket.WebSocketSession;

import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChallengeVo {
    ArrayList<WebSocketSession> sessions;
    public ArrayList<TreasureVo> treasureInfo;
    public ArrayList<RankVo> rankInfo;

}
