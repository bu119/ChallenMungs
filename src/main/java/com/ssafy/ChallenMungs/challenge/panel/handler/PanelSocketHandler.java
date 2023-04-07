package com.ssafy.ChallenMungs.challenge.panel.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.ssafy.ChallenMungs.challenge.common.entity.Challenge;
import com.ssafy.ChallenMungs.challenge.common.entity.MyChallenge;
import com.ssafy.ChallenMungs.challenge.common.service.ChallengeService;
import com.ssafy.ChallenMungs.challenge.common.service.MyChallengeService;
import com.ssafy.ChallenMungs.user.controller.UserController;
import com.ssafy.ChallenMungs.user.entity.User;
import com.ssafy.ChallenMungs.user.service.UserService;
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
import java.util.Arrays;
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

    @Autowired
    UserService userService;

    // 서버를 재시작할 때 현재 진행중인 판넬게임을 판넬메니저에 등록해요 물론초기화!!
    @PostConstruct
    public void init() {
        List<Challenge> challenges = challengeService.findAllByStatusAndChallengeType(1, 2);
        for (Challenge c : challenges) {
            List<MyChallenge> myChallenges = myChallengeService.findAllByChallengeId(c.getChallengeId());
            ArrayList<RankVo> rankInfo = new ArrayList<>();
            for (MyChallenge mc : myChallenges) {
                rankInfo.add(RankVo.builder().teamRank(1).indiRank(1).PanelCount(0).teamId(mc.getTeamId()).loginId(mc.getLoginId()).build());
            }
            // mapCoordinate 정의
            Double latCellLength = (c.getMaxLat() - c.getMinLat()) / c.getCellD();
            Double lngCellLength = (c.getMaxLng() - c.getMinLng()) / c.getCellD();
            CoordinateVo [] [] [] mapCoordinate = new CoordinateVo [c.getCellD()] [c.getCellD()] [4];
            for (int i = 0; i < c.getCellD() - 1; i++) {
                for (int j = 0; j < c.getCellD() - 1; j++) {
                    mapCoordinate[i][j][0] = CoordinateVo.builder().lat(c.getMaxLat() - latCellLength * i).lng(c.getMinLng() + lngCellLength * j).build();
                    mapCoordinate[i][j][1] = CoordinateVo.builder().lat(c.getMaxLat() - latCellLength * i).lng(c.getMinLng() + lngCellLength * (j + 1)).build();
                    mapCoordinate[i][j][2] = CoordinateVo.builder().lat(c.getMaxLat() - latCellLength * (i + 1)).lng(c.getMinLng() + lngCellLength * j).build();
                    mapCoordinate[i][j][3] = CoordinateVo.builder().lat(c.getMaxLat() - latCellLength * (i + 1)).lng(c.getMinLng() + lngCellLength * (j + 1)).build();
                }
            }
            for (int i = 0; i < c.getCellD() - 1; i++) {
                mapCoordinate[i][c.getCellD() - 1][0] = CoordinateVo.builder().lat(c.getMaxLat() - latCellLength * i).lng(c.getMinLng() + lngCellLength * (c.getCellD()-1)).build();
                mapCoordinate[i][c.getCellD() - 1][1] = CoordinateVo.builder().lat(c.getMaxLat() - latCellLength * i).lng(c.getMaxLng()).build();
                mapCoordinate[i][c.getCellD() - 1][2] = CoordinateVo.builder().lat(c.getMaxLat() - latCellLength * (i+1)).lng(c.getMinLng() + lngCellLength * (c.getCellD()-1)).build();
                mapCoordinate[i][c.getCellD() - 1][3] = CoordinateVo.builder().lat(c.getMaxLat() - latCellLength * (i+1)).lng(c.getMaxLng()).build();
            }
            for (int i = 0; i < c.getCellD() - 1; i++) {
                mapCoordinate[c.getCellD() - 1][i][0] = CoordinateVo.builder().lat(c.getMaxLat() - latCellLength * (c.getCellD() - 1)).lng(c.getMinLng() + lngCellLength * i).build();
                mapCoordinate[c.getCellD() - 1][i][1] = CoordinateVo.builder().lat(c.getMaxLat() - latCellLength * (c.getCellD() - 1)).lng(c.getMinLng() + lngCellLength * (i + 1)).build();
                mapCoordinate[c.getCellD() - 1][i][2] = CoordinateVo.builder().lat(c.getMinLat()).lng(c.getMinLng() + lngCellLength * i).build();
                mapCoordinate[c.getCellD() - 1][i][3] = CoordinateVo.builder().lat(c.getMinLat()).lng(c.getMinLng() + lngCellLength * (i + 1)).build();
            }
            mapCoordinate[c.getCellD() - 1][c.getCellD() - 1][0] = CoordinateVo.builder().lat(c.getMaxLat() - latCellLength * (c.getCellD()-1)).lng(c.getMinLng() + lngCellLength * (c.getCellD()-1)).build();
            mapCoordinate[c.getCellD() - 1][c.getCellD() - 1][1] = CoordinateVo.builder().lat(c.getMaxLat() - latCellLength * (c.getCellD()-1)).lng(c.getMaxLng()).build();
            mapCoordinate[c.getCellD() - 1][c.getCellD() - 1][2] = CoordinateVo.builder().lat(c.getMinLat()).lng(c.getMinLng() + lngCellLength * (c.getCellD()-1)).build();
            mapCoordinate[c.getCellD() - 1][c.getCellD() - 1][3] = CoordinateVo.builder().lat(c.getMinLat()).lng(c.getMaxLng()).build();
            // 여기서 mapInfo 정의
            challengeManager.put(c.getChallengeId(), ChallengeVo.builder().players(new ArrayList<PlayerVo>()).mapInfo(new int[c.getCellD()][c.getCellD()]).mapCoordinate(mapCoordinate).rankInfo(rankInfo).belong(new String [c.getCellD()] [c.getCellD()]).build());
        }
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
//            for (Long i : challengeManager.keySet()) {
//                System.out.println(i);
//                for (int j = 0; j < challengeManager.get(i).players.size(); j++) {
//                    System.out.print(challengeManager.get(i).players.get(j).session.getId());
//                }
//                System.out.println();
//            }
//            for (int i = 0; i < challengeManager.get(challengeId).mapInfo.length; i++) {
//                for (int j = 0; j < challengeManager.get(challengeId).mapInfo[0].length; j++) {
//                    System.out.print(challengeManager.get(challengeId).mapInfo[i][j] + " ");
//                }
//                System.out.println();
//            }
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

//            System.out.println(challengeManager.get(challengeId).mapCoordinate[0]);
//            System.out.println(challengeManager.get(challengeId).mapCoordinate[0][0]);
//            System.out.println(challengeManager.get(challengeId).mapCoordinate[0][0][0]);
//            System.out.println(challengeManager.get(challengeId).mapCoordinate[0][0][0].lat);
//            for (int i = 0; i < challengeManager.get(challengeId).mapCoordinate.length; i++) {
//                for (int j = 0; j < challengeManager.get(challengeId).mapCoordinate.length; j++) {
//                    System.out.printf("(%d, %d)[%7.5f, %7.5f]", i, j, challengeManager.get(challengeId).mapCoordinate[i][j][3].lat, challengeManager.get(challengeId).mapCoordinate[i][j][0].lng);
//                }
//                System.out.println();
//            }

            int index_r = -1;
            int index_c = -1;
            Loop1:
            for (int i = 0; i < challenge.getCellD(); i++) {
                for (int j = 0; j < challenge.getCellD(); j++) {
                    if (
                            (challengeManager.get(challengeId).mapCoordinate[i][j][3].lat <= myLat && myLat <= challengeManager.get(challengeId).mapCoordinate[i][j][0].lat) &&
                            (challengeManager.get(challengeId).mapCoordinate[i][j][0].lng <= myLng && myLng <= challengeManager.get(challengeId).mapCoordinate[i][j][3].lng)
                    ) {
                        index_r = i;
                        index_c = j;
                        break Loop1;
                    }
                }
            }

            if(index_r != -1 && index_c != -1) {
                if (challengeManager.get(challengeId).belong[index_r][index_c] != null) {
                    for (RankVo rv : challengeManager.get(challengeId).rankInfo) {
                        if (rv.getLoginId().equals(challengeManager.get(challengeId).belong[index_r][index_c])) {
                            rv.setPanelCount(rv.getPanelCount() - 1);
                            break;
                        }
                    }
                }
                challengeManager.get(challengeId).mapInfo[index_r][index_c] = myChallenge.getTeamId();
                challengeManager.get(challengeId).belong[index_r][index_c] = loginId;
                for (RankVo rv : challengeManager.get(challengeId).rankInfo) {
                    if (rv.getLoginId().equals(loginId)) {
                        rv.setPanelCount(rv.getPanelCount() + 1);
                        break;
                    }
                }
            }

            challengeManager.get(challengeId).rankInfo.sort((rv1, rv2) -> {
                return rv2.getPanelCount() - rv1.getPanelCount();
            });

            boolean teamdraw = false;
            if (challenge.getGameType() == 1) {
                int rank = 0;
                int count = 99999;

                for (RankVo r : challengeManager.get(challengeId).rankInfo) {
                    if (count > r.PanelCount) {
                        count = r.PanelCount;
                        rank++;
                    }
                    r.teamRank = rank;
                    r.indiRank = rank;
                }
            } else if (challenge.getGameType() == 2) {
                int sum1 = 0;
                int sum2 = 0;
                int rank = 0;
                int count = 99999;
                for (RankVo r : challengeManager.get(challengeId).rankInfo) {
                    System.out.println(rank);
                    if (r.getTeamId() == 1) sum1 += r.getPanelCount();
                    else if (r.getTeamId() == 2) sum2 += r.getPanelCount();
                    if (count > r.PanelCount) {
                        count = r.PanelCount;
                        rank++;
                    }
                    r.indiRank = rank;

                    System.out.println(r.loginId + " " + r.PanelCount + " " + r.indiRank);
                }
                if (sum1 > sum2) {
                    challengeManager.get(challengeId).rankInfo.sort((o1, o2) -> {
                        return o1.getTeamId() - o2.getTeamId();
                    });
                    for (RankVo r : challengeManager.get(challengeId).rankInfo) {
                        if (r.getTeamId() == 1) r.setTeamRank(1);
                        else if (r.getTeamId() == 2) r.setTeamRank(2);
                    }
                } else if (sum1 < sum2) {
                    challengeManager.get(challengeId).rankInfo.sort((o1, o2) -> {
                        return o2.getTeamId() - o1.getTeamId();
                    });
                    for (RankVo r : challengeManager.get(challengeId).rankInfo) {
                        if (r.getTeamId() == 1) r.setTeamRank(2);
                        else if (r.getTeamId() == 2) r.setTeamRank(1);
                    }
                } else if (sum1 == sum2) {
                    challengeManager.get(challengeId).rankInfo.sort((o1, o2) -> {
                        return o1.getTeamId() - o2.getTeamId();
                    });
                    for (RankVo r : challengeManager.get(challengeId).rankInfo) {
                        r.setTeamRank(1);
                        teamdraw = true;
                    }
                }
            }

            HashMap<String, Object> mapDto = new HashMap<String, Object>();
            mapDto.put("code", "signaling");
            HashMap<String, Object> subMap = new HashMap<String, Object>();
            mapDto.put("value", subMap);
            subMap.put("indexR", index_r);
            subMap.put("indexC", index_c);
            subMap.put("teamId", myChallenge.getTeamId());
            ArrayList<HashMap<String, Object>> newRankInfoList = new ArrayList<>();
            for (RankVo rv : challengeManager.get(challenge.getChallengeId()).rankInfo) {
                User u = userService.findUserByLoginId((String) rv.getLoginId()); // 팀전일 경우 LoginId가 ArrayList라 고쳐야햄
                HashMap<String, Object> newRankInfoMap = new HashMap<>();
                newRankInfoMap.put("loginId", u.getLoginId());
                newRankInfoMap.put("name", u.getName());
                newRankInfoMap.put("profile", u.getProfile());
                newRankInfoMap.put("teamRank", rv.getTeamRank());
                newRankInfoMap.put("indiRank", rv.getIndiRank());
                newRankInfoMap.put("teamId", rv.getTeamId());
                newRankInfoMap.put("point", rv.getPanelCount());
                newRankInfoList.add(newRankInfoMap);
            }
            subMap.put("rankInfo", newRankInfoList);
            subMap.put("teamDraw", teamdraw);
            TextMessage dto = new TextMessage(mapper.writeValueAsString(mapDto));

//            for (int i = 0; i < challengeManager.get(challengeId).mapInfo.length; i++) {
//                for (int j = 0; j < challengeManager.get(challengeId).mapInfo[0].length; j++) {
//                    System.out.print(challengeManager.get(challengeId).mapInfo[i][j] + " ");
//                }
//                System.out.println();
//            }

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
//                System.out.println(pv.session);
                if (pv.session.equals(session)) {
                    log.info("소켓에서 세션을 제거했어요!!");
                    challengeManager.get(i).players.remove(pv);
                    break Loop1;
                }
            }
        }
//        for (Long i : challengeManager.keySet()) {
//            System.out.println(i);
//            for (int j = 0; j < challengeManager.get(i).players.size(); j++) {
//                System.out.print(challengeManager.get(i).players.get(j).session.getId());
//            }
//            System.out.println();
//        }
//        for (int i = 0; i < challengeManager.get(1).mapInfo.length; i++) {
//            for (int j = 0; j < challengeManager.get(1).mapInfo[0].length; j++) {
//                System.out.print(challengeManager.get(1).mapInfo[i][j] + " ");
//            }
//            System.out.println();
//        }
    }
}
