package com.ssafy.ChallenMungs.challenge.panel.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.ssafy.ChallenMungs.challenge.common.entity.Challenge;
import com.ssafy.ChallenMungs.challenge.common.entity.MyChallenge;
import com.ssafy.ChallenMungs.challenge.common.service.ChallengeService;
import com.ssafy.ChallenMungs.challenge.common.service.MyChallengeService;
import com.ssafy.ChallenMungs.user.controller.UserController;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import springfox.documentation.spring.web.json.Json;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class PanelSocketHandler extends TextWebSocketHandler {
    public HashMap<Long, ChallengeVo> challengeManager = new HashMap<>();
    ObjectMapper mapper = new ObjectMapper();
    JsonParser parser = new JsonParser();

    @Autowired
    ChallengeService challengeService;

    @Autowired
    MyChallengeService myChallengeService;

    // 서버를 재시작할 때 현재 진행중인 판넬게임을 판넬메니저에 등록해요 물론초기화!!
    @PostConstruct
    public void init() {
        List<Challenge> challenges = challengeService.findAllByStatusAndChallengeType(1, 2);
        for (Challenge c : challenges) {
            List<MyChallenge> myChallenges = myChallengeService.findAllByChallengeId(c.getChallengeId());
            ArrayList<RankVo> rankInfo = new ArrayList<>();
            if (c.getGameType() == 1) {
                for (MyChallenge mc : myChallenges) {
                    rankInfo.add(RankVo.builder().teamRank(1).PanelCount(0).teamId(mc.getTeamId()).loginId(mc.getLoginId()).build());
                }
            } else if (c.getGameType() == 2) {
                ArrayList<String> ids1 = new ArrayList<>();
                ArrayList<String> ids2 = new ArrayList<>();
                for (MyChallenge mc : myChallenges) {
                    if (mc.getTeamId() == 1) ids1.add(mc.getLoginId());
                    else if (mc.getTeamId() == 2) ids2.add(mc.getLoginId());
                }
                rankInfo.add(RankVo.builder().teamRank(1).PanelCount(0).teamId(1).loginId(ids1).build());
                rankInfo.add(RankVo.builder().teamRank(1).PanelCount(0).teamId(2).loginId(ids2).build());
            }
            challengeManager.put(c.getChallengeId(), ChallengeVo.builder().players(new ArrayList<PlayerVo>()).mapInfo(new int[c.getCellD()][c.getCellD()]).rankInfo(rankInfo).build());
        }
        System.out.println("관리하고 있는 아이디:" + challengeManager.keySet());
    }

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
        log.info("누군가가 판넬소켓에 연결되었어요!");
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        super.handleTextMessage(session, message);

        JsonElement element = parser.parse(message.getPayload());
        String event = element.getAsJsonObject().get("event").getAsString();
        JsonElement data = element.getAsJsonObject().get("data").getAsJsonObject();
        if (event.equals("access")) {
            log.info("누군가가 산책을 시작했어요 데이터:" + data);
            Long challengeId = data.getAsJsonObject().get("challengeId").getAsLong();
            String loginId = data.getAsJsonObject().get("loginId").getAsString();
            MyChallenge myChallenge = myChallengeService.findByLoginIdAndChallengeId(loginId, challengeId);
            int myTeamId = myChallenge.getTeamId();
            // 챌린지메니저에 등록한다
            challengeManager.get(challengeId).getPlayers().add(PlayerVo.builder().session(session).loginId(loginId).teamId(myTeamId).build());
            // 현재 맵정보와 랭킹정보 준다
            HashMap<String, Object> dto = new HashMap<>();
            dto.put("code", "access");
            HashMap<String, Object> value = new HashMap<>();
            dto.put("value", value);
            value.put("mapInfo", challengeManager.get(challengeId).mapInfo);
            value.put("rankInfo", challengeManager.get(challengeId).rankInfo);
            session.sendMessage(new TextMessage(mapper.writeValueAsString(dto)));
        }
        else if (event.equals("signaling")) {
            log.info("같은 방 사람들에게 신호를 보내요!");
            Double myLat = data.getAsJsonObject().get("lat").getAsDouble();
            Double myLng = data.getAsJsonObject().get("lng").getAsDouble();
            Long challengeId = data.getAsJsonObject().get("challengeId").getAsLong();
            String loginId = data.getAsJsonObject().get("loginId").getAsString();

            Challenge challenge = challengeService.findByChallengeId(challengeId);
            MyChallenge myChallenge = myChallengeService.findByLoginIdAndChallengeId(loginId, challengeId);
            Double latCellLength = (challenge.getMaxLat() - challenge.getMinLat()) / challenge.getCellD();
            Double lngCellLength = (challenge.getMaxLng() - challenge.getMinLng()) / challenge.getCellD();

            Integer index_c = (int) ((myLng - challenge.getMinLng()) / lngCellLength);
            Integer index_r = (int) ((challenge.getMaxLat() - myLat) / latCellLength);

            int moto = challengeManager.get(challengeId).mapInfo[index_r][index_c];
            challengeManager.get(challengeId).mapInfo[index_r][index_c] = myChallenge.getTeamId();

            if (moto != 0) challengeManager.get(challengeId).rankInfo.get(moto - 1).PanelCount--;
            challengeManager.get(challengeId).rankInfo.get(myChallenge.getTeamId() - 1).PanelCount++;

            challengeManager.get(challengeId).rankInfo.sort((o1, o2) -> {
                return o2.PanelCount - o1.PanelCount;
            });

            int rank = 0;
            int count = -1;
            for (RankVo r : challengeManager.get(challengeId).rankInfo) {
                if (count < r.PanelCount) {
                    count = r.PanelCount;
                    rank++;
                }
                r.teamRank = rank;
            }

            HashMap<String, Object> mapDto = new HashMap<String, Object>();
            mapDto.put("code", "signaling");
            HashMap<String, Object> subMap = new HashMap<String, Object>();
            mapDto.put("value", subMap);
            subMap.put("indexR", index_r);
            subMap.put("indexC", index_c);
            subMap.put("teamId", myChallenge.getTeamId());
            subMap.put("rankInfo", challengeManager.get(challengeId).rankInfo);
            TextMessage dto = new TextMessage(mapper.writeValueAsString(mapDto));

            for (PlayerVo pv : challengeManager.get(challengeId).players) {
                pv.session.sendMessage(dto);
            }
        } /*else if (event.equals("getInfo")) {
            log.info("게임 중 정보를 불러와요");
            Long challengeId = data.getAsJsonObject().get("challengeId").getAsLong();
            Challenge challenge = challengeService.findByChallengeId(challengeId);
            HashMap<String, Object> mapDto = new HashMap<String, Object>();
            mapDto.put("code", "getInfo");
            HashMap<String, Object> subMap = new HashMap<String, Object>();
            mapDto.put("value", subMap);
            subMap.put("startDate", challenge.getStartDate().toString());
            subMap.put("endDate", challenge.getEndDate().toString());
            subMap.put("entryFee", challenge.getEntryFee());
            subMap.put("gameType", challenge.getGameType());
            subMap.put("rankInfo", challengeManager.get(challengeId).rankInfo);
            TextMessage dto = new TextMessage(mapper.writeValueAsString(mapDto));
            session.sendMessage(dto);
        }*/
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        log.info("누군가가 소켓에서 연결이 끊겼어요ㅜㅜ");
        Loop1:
        for (Long i : challengeManager.keySet()) {
            for (PlayerVo pv : challengeManager.get(i).players) {
                if (pv.session.equals(session)) {
                    challengeManager.get(i).players.remove(pv);
                    break Loop1;
                }
            }
        }
    }
}
