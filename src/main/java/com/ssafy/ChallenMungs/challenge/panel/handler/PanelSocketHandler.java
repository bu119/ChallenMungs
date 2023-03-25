package com.ssafy.ChallenMungs.challenge.panel.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.ssafy.ChallenMungs.challenge.common.entity.MyChallenge;
import com.ssafy.ChallenMungs.challenge.common.service.MyChallengeService;
import com.ssafy.ChallenMungs.user.controller.UserController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import springfox.documentation.spring.web.json.Json;

import java.util.ArrayList;
import java.util.HashMap;

@Component
public class PanelSocketHandler extends TextWebSocketHandler {
    ArrayList<WebSocketSession> allSession = new ArrayList<>();
    public HashMap<Long, ChallengeVo> challengeManager = new HashMap<>();
    ObjectMapper mapper = new ObjectMapper();
    JsonParser parser = new JsonParser();

    @Autowired
    MyChallengeService myChallengeService;

    //있어야 되는거
    //0. 방이 만들어졌을 때 룸메니저에게 맵크기를 알려주기 -> 맵을 구획하고 만든사람을 방안에 넣어줌
    //1. 처음 들어왔을 때 맵 전체 불러오기 -> 할 수 있으면 누군가 산책을 시작했다고 알려주기 기능?
    //2. 누군가 판넬을 바꿨을 때 알려주기
    //3. 누군가 원할 때 순위 정보
    //4. 누군가에게 메세지보내기

    private Logger log = LoggerFactory.getLogger(PanelSocketHandler.class);
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        log.info("누군가가 소켓에 연결되었어요!");
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        super.handleTextMessage(session, message);

        JsonElement element = parser.parse(message.getPayload());
        String event = element.getAsJsonObject().get("event").getAsString();
        JsonElement data = element.getAsJsonObject().get("data").getAsJsonObject();
        if (event.equals("access")) {
            log.info("누군가가 게임에 접속했어요");
            Long challengeId = data.getAsJsonObject().get("challengeId").getAsLong();
            String loginId = data.getAsJsonObject().get("LoginId").getAsString();
            MyChallenge myChallenge = myChallengeService.findByLoginIdAndChallengeId(loginId, challengeId);
            int myTeamId = myChallenge.getTeamId();
            
            // 챌린지메니저에 등록한다
            challengeManager.get(challengeId).getPlayers().add(PlayerVo.builder().session(session).team_id(myTeamId).ranking(0).build());
            // 현재 맵정보 준다
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        log.info("누군가가 소켓에서 연결이 끊겼어요ㅜㅜ");
        for (WebSocketSession w : allSession) {
            if (w.equals(session)) {
                allSession.remove(w);
                break;
            }
        }
    }
}
