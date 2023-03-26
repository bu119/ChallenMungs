package com.ssafy.ChallenMungs.challenge.panel.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.ssafy.ChallenMungs.challenge.common.entity.Challenge;
import com.ssafy.ChallenMungs.challenge.common.entity.MyChallenge;
import com.ssafy.ChallenMungs.challenge.common.service.ChallengeService;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class PanelSocketHandler extends TextWebSocketHandler {
    ArrayList<WebSocketSession> allSession = new ArrayList<>();
    public HashMap<Long, ChallengeVo> challengeManager = new HashMap<>();
    ObjectMapper mapper = new ObjectMapper();
    JsonParser parser = new JsonParser();

    @Autowired
    ChallengeService challengeService;

    @Autowired
    MyChallengeService myChallengeService;

//    PanelSocketHandler() {
//        boolean flag = false;
//        List<Challenge> challenges = challengeService.findAllByStatus(1);
//        for (Challenge c : challenges) {
//            LocalDate today = LocalDate.now();
//            flag = false;
//            if (c.getStatus() == 1) {
//                c.setStatus(1);
//                flag = true;
//                if (c.getChallengeType() == 2) {
//                    List<MyChallenge> myChallenges = myChallengeService.findAllByChallengeId(c.getChallengeId());
//                    int teamIdx = 0;
//                    ArrayList<RankVo> rankInfo = new ArrayList<>();
//                    if (c.getGameType() == 1) {
//                        for (MyChallenge mc : myChallenges) {
//                            teamIdx++;
//                            rankInfo.add(RankVo.builder().teamRank(1).PanelCount(0).teamId(teamIdx).build());
//                        }
//                    } else if (c.getGameType() == 2) {
//                        rankInfo.add(RankVo.builder().teamRank(1).PanelCount(0).teamId(1).build());
//                        rankInfo.add(RankVo.builder().teamRank(1).PanelCount(0).teamId(2).build());
//                    }
//                    Double cell_size = 50.0;// m다
////                    int xd = (int) Math.ceil(getDistance(c.getLeftTopLat(), c.getRightBottomLng(), c.getRightBottomLat(), c.getRightBottomLng()) / (cell_size / 1000));
////                    int yd = (int) Math.ceil(getDistance(c.getLeftTopLat(), c.getLeftTopLng(), c.getLeftTopLat(), c.getRightBottomLng()) / (cell_size / 1000));
//
////                    challengeManager.put(c.getChallengeId(), ChallengeVo.builder().players(new ArrayList<PlayerVo>()).mapInfo(new int[xd][yd]).rankInfo(rankInfo).build());
//                }
//                challengeService.save(c);
//            }
//        }
//    }

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
            log.info("누군가가 게임에 접속했어요 데이터:" + data);
            Long challengeId = data.getAsJsonObject().get("challengeId").getAsLong();
            String loginId = data.getAsJsonObject().get("loginId").getAsString();
            MyChallenge myChallenge = myChallengeService.findByLoginIdAndChallengeId(loginId, challengeId);
            int myTeamId = myChallenge.getTeamId();
            // 챌린지메니저에 등록한다
            challengeManager.get(challengeId).getPlayers().add(PlayerVo.builder().session(session).loginId(loginId).teamId(myTeamId).build());
            // 현재 맵정보와 랭킹정보 준다
            HashMap<String, Object> dto = new HashMap<>();
            dto.put("code", "access");
            dto.put("mapInfo", challengeManager.get(challengeId).mapInfo);
            dto.put("rankInfo", challengeManager.get(challengeId).rankInfo);
            session.sendMessage(new TextMessage(mapper.writeValueAsString(dto)));
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
