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
import com.ssafy.ChallenMungs.common.util.FileManager;
import jnr.ffi.Struct;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
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
    FileManager fileManager;

    ObjectMapper mapper = new ObjectMapper();

//    @Scheduled(cron = "0 0 0 * * ?") // 매일 자정에 동작해요
    @Scheduled(cron = "0/20 * * * * ?") // 5초마다 실행해요
    public void startChallenge() {
        System.out.println("스케쥴러가 동작해요!");
        boolean flag;
        // 시작하면 teamId를 다시 정의해줘요
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
                            rankInfo.add(RankVo.builder().teamRank(1).PanelCount(0).teamId(teamIdx).build());
                        }
                    } else if (c.getGameType() == 2) {
                        for (MyChallenge mc : myChallenges) {
                            teamIdx++;
                            mc.setTeamId(teamIdx % 2 + 1);
                            myChallengeService.save(mc);
                        }
                        rankInfo.add(RankVo.builder().teamRank(1).PanelCount(0).teamId(1).build());
                        rankInfo.add(RankVo.builder().teamRank(1).PanelCount(0).teamId(2).build());
                    }
                    panelSocketHandler.challengeManager.put(c.getChallengeId(), ChallengeVo.builder().players(new ArrayList<PlayerVo>()).mapInfo(new int [c.getCellD()] [c.getCellD()]).rankInfo(rankInfo).build());
                }
                challengeService.save(c);
            }
            // 예를 들어 2일에 끝나는 겜이면 3일 자정에 끝나야됨
            if (c.getStatus() == 1 && c.getEndDate().plusDays(1).equals(today)) {
                c.setStatus(2);
                flag = true;
                String saveValue;
                StringBuilder sb = new StringBuilder();
                if (c.getChallengeType() == 2) {
                    log.info("판넬뒤집기 챌린지가 종료되었어요!");
                    try {
                        sb.append("{\nmapInfo:");
                        sb.append(mapper.writeValueAsString(panelSocketHandler.challengeManager.get(c.getChallengeId()).getMapInfo()));
                        sb.append(",\nrankInfo:");
                        sb.append(mapper.writeValueAsString(panelSocketHandler.challengeManager.get(c.getChallengeId()).getRankInfo()));
                        sb.append(",\ncenterLat:");
                        sb.append(c.getCenterLat());
                        sb.append(",\ncenterLng:");
                        sb.append(c.getCenterLng());
                        sb.append("\n}");
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                    fileManager.saveResult(Long.toString(c.getChallengeId()), sb.toString());
                    panelSocketHandler.challengeManager.remove(c.getChallengeId());
                }
            }
            if (flag) {
                challengeService.save(c);
            }
        }
    }
}
