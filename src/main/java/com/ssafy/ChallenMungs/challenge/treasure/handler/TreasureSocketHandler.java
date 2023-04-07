package com.ssafy.ChallenMungs.challenge.treasure.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.ssafy.ChallenMungs.challenge.common.entity.Challenge;
import com.ssafy.ChallenMungs.challenge.common.entity.MyChallenge;
import com.ssafy.ChallenMungs.challenge.common.service.ChallengeService;
import com.ssafy.ChallenMungs.challenge.common.service.MyChallengeService;
import com.ssafy.ChallenMungs.challenge.panel.handler.PanelSocketHandler;
import com.ssafy.ChallenMungs.challenge.panel.handler.PlayerVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class TreasureSocketHandler extends TextWebSocketHandler {

    public HashMap<Long, ChallengeVo> challengeManager = new HashMap<>();
    ObjectMapper mapper = new ObjectMapper();
    JsonParser parser = new JsonParser();

    @Autowired
    ChallengeService challengeService;

    @Autowired
    MyChallengeService myChallengeService;

    private Logger log = LoggerFactory.getLogger(TreasureSocketHandler.class);

    // 서버를 재시작할 때 현재 진행중인 보물게임을 보물메니저에 등록해요 물론초기화!!
    @PostConstruct
    public void init() {
        List<Challenge> challenges = challengeService.findAllByStatusAndChallengeType(1, 3);
        for (Challenge c : challenges) {
            List<MyChallenge> myChallenges = myChallengeService.findAllByChallengeId(c.getChallengeId());
            ArrayList<RankVo> rankInfo = new ArrayList<>();
            for (MyChallenge mc : myChallenges) {
                rankInfo.add(RankVo.builder().point(0).teamRank(1).teamId(mc.getTeamId()).loginId(mc.getLoginId()).myTreasureList(new ArrayList<>()).build());
            }
            ArrayList<TreasureVo> treasures = new ArrayList<>();
            int cc = (int) (c.getMap_size() / c.getCellSize());
            cc *= cc;
            int boomRatio = 5; // 만약 전체 보물의 1/5만큼은 폭탄으로 하고 싶다면 5로 해요 //스케쥴러도 수정
            int idx = 0;
            for (int i = 0; i < (int) (cc / boomRatio * (boomRatio - 1)); i++) {
                double randomLat = Math.random() * (c.getMaxLat() - c.getMinLat()) + c.getMinLat();
                double randomLng = Math.random() * (c.getMaxLng() - c.getMinLng()) + c.getMinLng();
                int randomPoint = (int) (Math.random() * 20 - 10);
                treasures.add(TreasureVo.builder().idx(idx).lat(randomLat).lng(randomLng).point(randomPoint).isOpened(false).inPocket(false).type(true).build());
                idx++;
            }
            for (int i = 0; i < (int) (cc / boomRatio); i++) {
                double randomLat = Math.random() * (c.getMaxLat() - c.getMinLat()) + c.getMinLat();
                double randomLng = Math.random() * (c.getMaxLng() - c.getMinLng()) + c.getMinLng();
                treasures.add(TreasureVo.builder().idx(idx).lat(randomLat).lng(randomLng).point(null).isOpened(false).inPocket(false).type(false).build());
                idx++;
            }
            challengeManager.put(c.getChallengeId(), ChallengeVo.builder().sessions(new ArrayList<>()).treasureInfo(treasures).rankInfo(rankInfo).build());
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        log.info("누군가가 보물소켓과 연결되었어요");
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        super.handleTextMessage(session, message);

        JsonElement element = parser.parse(message.getPayload());
        String event = element.getAsJsonObject().get("event").getAsString();
        JsonElement data = element.getAsJsonObject().get("data").getAsJsonObject();

        if (event.equals("access")) {
            log.info("누군가가 보물찾기를 시작했어요 데이터:" + data);
            Long challengeId = data.getAsJsonObject().get("challengeId").getAsLong();
            System.out.println(challengeId + " " + challengeManager.get(challengeId));
            challengeManager.get(challengeId).sessions.add(session);
            HashMap<String, Object> dto = new HashMap<>();
            dto.put("code", "access");
            HashMap<String, Object> value = new HashMap<>();
            dto.put("value", value);
            value.put("trasureInfo", challengeManager.get(challengeId).treasureInfo);
            value.put("rankInfo", challengeManager.get(challengeId).rankInfo);
            session.sendMessage(new TextMessage(mapper.writeValueAsString(dto)));
        } else if (event.equals("signaling")) {
            log.info("누군가가 보물을 포켓에 담았어요");
            Long challengeId = data.getAsJsonObject().get("challengeId").getAsLong();
            Integer treasureIdx = data.getAsJsonObject().get("treasureIdx").getAsInt();
            challengeManager.get(challengeId).treasureInfo.get(treasureIdx).setInPocket(true); //포켓 내용물은 프론트에서 관리하세요

            HashMap<String, Object> dto = new HashMap<>();
            dto.put("code", "signaling");
            HashMap<String, Object> value = new HashMap<>();
            dto.put("value", value);
            value.put("trasureInfo", challengeManager.get(challengeId).treasureInfo);
            TextMessage tm = new TextMessage(mapper.writeValueAsString(dto));
            for (WebSocketSession s : challengeManager.get(challengeId).sessions) {
                s.sendMessage(tm);
            }
        } else if (event.equals("selecting")) {
            log.info("누군가가 보물 선택을 완료 했어요");
            String loginId = data.getAsJsonObject().get("loginId").getAsString();
            Long challengeId = data.getAsJsonObject().get("challengeId").getAsLong();
            JsonArray selectList = data.getAsJsonObject().get("selectList").getAsJsonArray();
            for (int i = 0; i < selectList.size(); i++) {
                int idx = selectList.get(i).getAsInt();
                challengeManager.get(challengeId).treasureInfo.get(idx).setIsOpened(true);

                if (challengeManager.get(challengeId).treasureInfo.get(idx).getType() == true) { // 폭탄이 아니라면
                    int point = challengeManager.get(challengeId).treasureInfo.get(idx).getPoint();
                    for (RankVo rv : challengeManager.get(challengeId).rankInfo) {
                        if (rv.getLoginId().equals(loginId)) {
                            rv.setPoint(rv.getPoint() + point);
                            rv.myTreasureList.add(idx);
                            break;
                        }
                    }
                } else { // 폭탄이라면
                    for (RankVo rv : challengeManager.get(challengeId).rankInfo) {
                        if (rv.getLoginId().equals(loginId)) {
                            rv.setPoint(0);
                            rv.myTreasureList.add(idx);
                            break;
                        }
                    }
                }
            }
            challengeManager.get(challengeId).rankInfo.sort((o1, o2) -> {
                return o2.getPoint() - o1.getPoint();
            });
            int rank = 0;
            int count = Integer.MAX_VALUE;
            for (RankVo r : challengeManager.get(challengeId).rankInfo) {
                if (count > r.getPoint()) {
                    count = r.getPoint();
                    rank++;
                }
                r.setTeamRank(rank);
            }

            HashMap<String, Object> dto = new HashMap<>();
            dto.put("code", "selecting");
            HashMap<String, Object> value = new HashMap<>();
            dto.put("value", value);
            value.put("rankInfo", challengeManager.get(challengeId).rankInfo);
            value.put("treasureInfo", challengeManager.get(challengeId).treasureInfo);
            TextMessage tm = new TextMessage(mapper.writeValueAsString(dto));
            for (WebSocketSession s : challengeManager.get(challengeId).sessions) {
                s.sendMessage(tm);
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        log.info("누군가가 보물소켓과 연결이 끊어졌어요ㅜㅜ");
    }
}
