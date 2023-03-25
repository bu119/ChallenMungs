package com.ssafy.ChallenMungs.challenge.panel.handler;

import lombok.*;
import org.springframework.web.socket.WebSocketSession;

import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChallengeVo {
    ArrayList<PlayerVo> players;
    int [] [] mapInfo;
}
