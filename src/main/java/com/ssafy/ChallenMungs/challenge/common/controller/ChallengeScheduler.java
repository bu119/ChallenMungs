package com.ssafy.ChallenMungs.challenge.common.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.ChallenMungs.challenge.common.entity.Challenge;
import com.ssafy.ChallenMungs.challenge.common.entity.MyChallenge;
import com.ssafy.ChallenMungs.challenge.common.service.ChallengeService;
import com.ssafy.ChallenMungs.challenge.common.service.MyChallengeService;
import com.ssafy.ChallenMungs.challenge.panel.handler.ChallengeVo;
import com.ssafy.ChallenMungs.challenge.panel.handler.PanelSocketHandler;
import com.ssafy.ChallenMungs.challenge.panel.handler.PlayerVo;
import com.ssafy.ChallenMungs.challenge.panel.handler.RankVo;
import com.ssafy.ChallenMungs.challenge.treasure.handler.TreasureSocketHandler;
import com.ssafy.ChallenMungs.challenge.treasure.handler.TreasureVo;
import com.ssafy.ChallenMungs.common.util.FileManager;
import com.ssafy.ChallenMungs.user.entity.User;
import com.ssafy.ChallenMungs.user.service.UserService;
import jnr.ffi.Struct;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class ChallengeScheduler {
    private Logger log = LoggerFactory.getLogger(ChallengeScheduler.class);
    @Autowired
    ChallengeService challengeService;

    @Autowired
    MyChallengeService myChallengeService;

    @Autowired
    PanelSocketHandler panelSocketHandler;

    @Autowired
    TreasureSocketHandler treasureSocketHandler;

    @Autowired
    FileManager fileManager;

    @Autowired
    UserService userService;

    ObjectMapper mapper = new ObjectMapper();

    @Scheduled(cron = "0 0 0 * * ?") // 매일 자정에 동작해요
//    @Scheduled(cron = "0/20 * * * * ?") // 20초마다 실행해요
    public void startChallenge() {
        System.out.println("스케쥴러가 동작해요!");
        boolean flag;
        // 시작하면 teamId를 다시 정의해줘요//
        List<Challenge> challenges = challengeService.findAll();
        Loop1:
        for (Challenge c : challenges) {
            LocalDate today = LocalDate.now();
            flag = false;
            if (c.getStatus() == 0 && c.getStartDate().equals(today)) {
                if (c.getCurrentParticipantCount() < c.getMaxParticipantCount()) {
                    log.info("정원을 채우지 못해서 방이 지워져요ㅜㅜ");
                    challengeService.delete(c);
                    continue Loop1;
                }
                c.setStatus(1);
                flag = true;
                if (c.getChallengeType() == 2) {
                    List<MyChallenge> myChallenges = myChallengeService.findAllByChallengeId(c.getChallengeId());
                    int teamIdx = 0;
                    ArrayList<RankVo> rankInfo = new ArrayList<>();
                    if (c.getGameType() == 1) {
                        for (MyChallenge mc : myChallenges) {
                            teamIdx++;
                            mc.setTeamId(teamIdx);
                            myChallengeService.save(mc);
                            rankInfo.add(RankVo.builder().teamRank(1).PanelCount(0).teamId(teamIdx).loginId(mc.getLoginId()).build());
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
                    panelSocketHandler.challengeManager.put(c.getChallengeId(), ChallengeVo.builder().players(new ArrayList<PlayerVo>()).mapInfo(new int [c.getCellD()] [c.getCellD()]).rankInfo(rankInfo).build());
                // 시작되는 방이 보물 찾기라면
                } else if (c.getChallengeType() == 3) {
                    List<MyChallenge> myChallenges = myChallengeService.findAllByChallengeId(c.getChallengeId());
                    int teamIdx = 0;
                    ArrayList<com.ssafy.ChallenMungs.challenge.treasure.handler.RankVo> rankInfo = new ArrayList<>();
                    for (MyChallenge mc : myChallenges) {
                        teamIdx++;
                        mc.setTeamId(teamIdx);
                        myChallengeService.save(mc);
                        rankInfo.add(com.ssafy.ChallenMungs.challenge.treasure.handler.RankVo.builder().point(0).teamRank(1).teamId(teamIdx).loginId(mc.getLoginId()).build());
                    }
                    ArrayList<TreasureVo> treasures = new ArrayList<>();
                    int cc = (int) (c.getMap_size() / c.getCellSize());
                    cc *= cc;
                    int boomRatio = 5; // 만약 전체 보물의 1/5만큼은 폭탄으로 하고 싶다면 5로 해요 // 소켓이닛도 수정
                    int idx = 0;
                    for (int i = 0; i < (int) (cc / boomRatio * (boomRatio - 1)); i++) {
                        double randomLat = Math.random() * (c.getMaxLat() - c.getMinLat()) + c.getMinLat();
                        double randomLng = Math.random() * (c.getMaxLng() - c.getMinLng()) + c.getMinLng();
                        int randomPoint = (int) (Math.random() * 30 - 10);
                        treasures.add(TreasureVo.builder().idx(idx).lat(randomLat).lng(randomLng).point(randomPoint).isOpened(false).inPocket(false).type(true).build());
                        idx++;
                    }
                    for (int i = 0; i < (int) (cc / boomRatio); i++) {
                        double randomLat = Math.random() * (c.getMaxLat() - c.getMinLat()) + c.getMinLat();
                        double randomLng = Math.random() * (c.getMaxLng() - c.getMinLng()) + c.getMinLng();
                        treasures.add(TreasureVo.builder().idx(idx).lat(randomLat).lng(randomLng).point(null).isOpened(false).inPocket(false).type(false).build());
                        idx++;
                    }
                    treasureSocketHandler.challengeManager.put(c.getChallengeId(), com.ssafy.ChallenMungs.challenge.treasure.handler.ChallengeVo.builder().sessions(new ArrayList<>()).treasureInfo(treasures).rankInfo(rankInfo).build());
                }
                challengeService.save(c);
            }
            // 예를 들어 2일에 끝나는 겜이면 3일 자정에 끝나야됨
            if (c.getStatus() == 1 && c.getEndDate().plusDays(1).equals(today)) {
                int totalKlay = c.getEntryFee() * c.getCurrentParticipantCount();
                c.setStatus(2);
                flag = true;
                String saveValue;
                StringBuilder sb = new StringBuilder();
                if (c.getChallengeType() == 2) {
                    log.info("판넬뒤집기 챌린지가 종료되었어요!");
                    ArrayList<HashMap> newRankInfoList = new ArrayList<>();
                    if (c.getGameType() == 1) { // 판넬뒤집기 개인전
                        int [] myklay = new int [panelSocketHandler.challengeManager.get(c.getChallengeId()).rankInfo.size()];
                        int klaySum = 0;
                        for (int i = 1; i < myklay.length; i++) {
                            myklay[i] = c.getEntryFee() * 2 / c.getCurrentParticipantCount() * (myklay.length - i - 1);
                            klaySum += myklay[i];
                        }
                        myklay[0] = totalKlay - klaySum;
                        int idx = 1;
                        for (com.ssafy.ChallenMungs.challenge.panel.handler.RankVo rv : panelSocketHandler.challengeManager.get(c.getChallengeId()).rankInfo) {
                            User u = userService.findUserByLoginId((String) rv.getLoginId()); // 팀전일 경우 LoginId가 ArrayList라 고쳐야햄
                            MyChallenge mc = myChallengeService.findByLoginIdAndChallengeId(u.getLoginId(), c.getChallengeId());
                            mc.setSuccessCount(rv.getTeamRank());
                            HashMap<String, Object> newRankInfoMap = new HashMap<>();
                            newRankInfoMap.put("name", u.getName());
                            newRankInfoMap.put("profile", u.getProfile());
                            newRankInfoMap.put("rank", rv.getTeamRank());
                            newRankInfoMap.put("teamId", rv.getTeamId());
                            newRankInfoMap.put("point", rv.getPanelCount());
                            newRankInfoMap.put("obtainKlay", myklay[idx-1]);
                            newRankInfoList.add(newRankInfoMap);
                            sendKlay(u, myklay[idx-1]);
                            idx++;
                        }
                    } else if (c.getGameType() == 2) { // 판넬뒤집기 팀전
                        int [] myklay = new int [((ArrayList) panelSocketHandler.challengeManager.get(c.getChallengeId()).rankInfo.get(0).getLoginId()).size()];
                        int klaySum = 0;
                        for (int i = 1; i < myklay.length; i++) {
                            myklay[i] = c.getEntryFee() / myklay.length;
                            klaySum += myklay[i];
                        }
                        myklay[0] = totalKlay - klaySum;
                        for (int i = 0; i < 2; i++) {
                            int idx = 0;
                            for (String loginId : (ArrayList<String>) panelSocketHandler.challengeManager.get(c.getChallengeId()).rankInfo.get(i).getLoginId()) {
                                User u = userService.findUserByLoginId(loginId); // 팀전일 경우 LoginId가 ArrayList라 고쳐야햄
                                MyChallenge mc = myChallengeService.findByLoginIdAndChallengeId(u.getLoginId(), c.getChallengeId());
                                mc.setSuccessCount(panelSocketHandler.challengeManager.get(c.getChallengeId()).rankInfo.get(i).getTeamRank());
                                HashMap<String, Object> newRankInfoMap = new HashMap<>();
                                newRankInfoMap.put("name", u.getName());
                                newRankInfoMap.put("profile", u.getProfile());
                                newRankInfoMap.put("rank", panelSocketHandler.challengeManager.get(c.getChallengeId()).rankInfo.get(i).getTeamRank());
                                newRankInfoMap.put("teamId", panelSocketHandler.challengeManager.get(c.getChallengeId()).rankInfo.get(i).getTeamId());
                                newRankInfoMap.put("point", panelSocketHandler.challengeManager.get(c.getChallengeId()).rankInfo.get(i).getPanelCount());
                                if (i == 0) {
                                    newRankInfoMap.put("obtainKlay", myklay[idx]);
                                    sendKlay(u, myklay[idx]);
                                    idx++;
                                } else {
                                    newRankInfoMap.put("obtainKlay", 0);
                                }
                                newRankInfoList.add(newRankInfoMap);
                            }
                        }
                    }
                    try {
                        sb.append("{\nmapInfo:");
                        sb.append(mapper.writeValueAsString(panelSocketHandler.challengeManager.get(c.getChallengeId()).getMapInfo()));
                        sb.append(",\nrankInfo:");
                        sb.append(mapper.writeValueAsString(newRankInfoList));
                        sb.append(",\ncenterLat:");
                        sb.append(c.getCenterLat());
                        sb.append(",\ncenterLng:");
                        sb.append(c.getCenterLng());
                        sb.append(",\ngameType:");
                        sb.append(c.getGameType());
                        sb.append("\n}");
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                    fileManager.saveResult(Long.toString(c.getChallengeId()), sb.toString());
                    panelSocketHandler.challengeManager.remove(c.getChallengeId());
                } else if (c.getChallengeType() == 3) {
                    log.info("보물찾기 챌린지가 종료되었어요!");
                    log.info("랭킹정보를 생성해요");
                    int [] myklay = new int [treasureSocketHandler.challengeManager.get(c.getChallengeId()).rankInfo.size()];
                    int klaySum = 0;
                    for (int i = 1; i < myklay.length; i++) {
                        myklay[i] = c.getEntryFee() * 2 / c.getCurrentParticipantCount() * (myklay.length - i - 1);
                        klaySum += myklay[i];
                    }
                    myklay[0] = totalKlay - klaySum;
                    int idx = 1;
                    ArrayList<HashMap> newRankInfoList = new ArrayList<>();
                    for (com.ssafy.ChallenMungs.challenge.treasure.handler.RankVo rv : treasureSocketHandler.challengeManager.get(c.getChallengeId()).rankInfo) {
                        User u = userService.findUserByLoginId(rv.getLoginId());
                        MyChallenge mc = myChallengeService.findByLoginIdAndChallengeId(u.getLoginId(), c.getChallengeId());
                        mc.setSuccessCount(rv.getTeamRank());
                        HashMap<String, Object> newRankInfoMap = new HashMap<>();
                        newRankInfoMap.put("name", u.getName());
                        newRankInfoMap.put("profile", u.getProfile());
                        newRankInfoMap.put("rank", rv.getTeamRank());
                        newRankInfoMap.put("point", rv.getPoint());
                        newRankInfoMap.put("obtainKlay", myklay[idx-1]);
                        newRankInfoMap.put("myTreasureList", rv.getMyTreasureList());
                        newRankInfoList.add(newRankInfoMap);
                        sendKlay(u, myklay[idx-1]);
                        idx++;
                    }
                    try {
                        sb.append("{\nmapInfo:");
                        sb.append(mapper.writeValueAsString(panelSocketHandler.challengeManager.get(c.getChallengeId()).getMapInfo()));
                        sb.append(",\nrankInfo:");
                        sb.append(mapper.writeValueAsString(newRankInfoList));
                        sb.append(",\ncenterLat:");
                        sb.append(c.getCenterLat());
                        sb.append(",\ncenterLng:");
                        sb.append(c.getCenterLng());
                        sb.append("\n}");
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            if (flag) {
                challengeService.save(c);
            }
        }
    }
    void sendKlay(User user, Integer klay) {

    }
}
