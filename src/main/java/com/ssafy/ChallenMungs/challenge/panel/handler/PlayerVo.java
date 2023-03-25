package com.ssafy.ChallenMungs.challenge.panel.handler;

import org.springframework.web.socket.WebSocketSession;

import java.util.ArrayList;

public class PlayerVo extends ArrayList<PlayerVo> {
    WebSocketSession session;
    int ranking;
    int team_id;

}
