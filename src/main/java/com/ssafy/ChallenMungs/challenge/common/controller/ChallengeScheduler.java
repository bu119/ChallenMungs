package com.ssafy.ChallenMungs.challenge.common.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.ChallenMungs.blockchain.repository.WalletRepository;
import com.ssafy.ChallenMungs.campaign.repository.CampaignListRepository;
import com.ssafy.ChallenMungs.challenge.common.entity.Challenge;
import com.ssafy.ChallenMungs.challenge.common.entity.MyChallenge;
import com.ssafy.ChallenMungs.challenge.common.service.ChallengeService;
import com.ssafy.ChallenMungs.challenge.common.service.MyChallengeService;
import com.ssafy.ChallenMungs.challenge.general.service.GeneralBoardService;
import com.ssafy.ChallenMungs.challenge.panel.handler.*;
import com.ssafy.ChallenMungs.challenge.treasure.handler.TreasureSocketHandler;
import com.ssafy.ChallenMungs.challenge.treasure.handler.TreasureVo;
import com.ssafy.ChallenMungs.common.util.FileManager;
import com.ssafy.ChallenMungs.user.entity.User;
import com.ssafy.ChallenMungs.user.service.UserService;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.ssafy.ChallenMungs.challenge.general.service.GeneralBoardService.*;

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

    @Autowired
    GeneralBoardService generalBoardService;

    @Autowired
    CampaignListRepository campaignListRepo;

    ObjectMapper mapper = new ObjectMapper();

    @Autowired
    WalletRepository walletRepo;

    @Scheduled(cron = "0 0 0 * * ?") // 매일 자정에 동작해요
//    @Scheduled(cron = "0/5 * * * * ?") // 20초마다 실행해요
    public void startChallenge() {
        System.out.println("스케쥴러가 동작해요!");
//        generalBoardService.updateSuccessCount("sa01023@naver.com", 9L);
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
                        for (MyChallenge mc : myChallenges) {
                            System.out.println(mc.getLoginId());
                            if (mc.getTeamId() == 1) rankInfo.add(RankVo.builder().teamRank(1).PanelCount(0).teamId(1).loginId(mc.getLoginId()).build());
                            else if (mc.getTeamId() == 2) rankInfo.add(RankVo.builder().teamRank(1).PanelCount(0).teamId(2).loginId(mc.getLoginId()).build());
                        }
                    }
                    //여기
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

                    panelSocketHandler.challengeManager.put(c.getChallengeId(), ChallengeVo.builder().players(new ArrayList<PlayerVo>()).mapInfo(new int [c.getCellD()] [c.getCellD()]).mapCoordinate(mapCoordinate).rankInfo(rankInfo).belong(new String[c.getCellD()] [c.getCellD()]).build());
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
                    int boomRatio = 5; // 만약 전체 보물의 1/5만큼은 폭탄으로 하고 싶다면 5로  해요 // 소켓이닛도 수정
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
                log.info("일반챌린지가 종료되었어요!");
                int totalKlay = c.getEntryFee() * c.getCurrentParticipantCount();
                c.setStatus(2);
                flag = true;
                String saveValue;
                StringBuilder sb = new StringBuilder();
                // 일반챌린지 끝날때
                if (c.getChallengeType() == 1) {
                    // 같은 챌린지 참여자들 가져와
                    List<MyChallenge> myChallenges = myChallengeService.findAllByChallengeId(c.getChallengeId());
                    List<User> successUsers = new ArrayList<>();
                    int successPeopleCount = 0;
                    // 모든 챌린지 참여자들 돌면서
                    for (MyChallenge mc : myChallenges) {

                        generalBoardService.updateSuccessCount(mc.getLoginId(), c.getChallengeId());
//                        mc.setSuccessRatio(calculateSuccessRatio(mc.getSuccessCount(), c.getStartDate(), c.getEndDate()));
                        long daysBetween = ChronoUnit.DAYS.between(c.getStartDate(), c.getEndDate()) + 1;
                        double successRatio = (double) mc.getSuccessCount() / daysBetween * 100;
                        mc.setSuccessRatio((int) Math.floor(successRatio));

                        if (mc.getSuccessRatio() >= c.getSuccessCondition()) {
                            mc.setSuccessResult(1);
                            successPeopleCount ++;
                            // user entity 내역
                            successUsers.add(userService.findUserByLoginId(mc.getLoginId()));
                        } else {
                            mc.setSuccessResult(0);
                        }

                        myChallengeService.save(mc);
                    }

                    int getCoin = 0;
                    // 전체 금액을 성공한 사람 n빵 금액
                    if (successPeopleCount != 0) {
                        getCoin = c.getMaxParticipantCount() * c.getEntryFee() / successPeopleCount;
                    }

                    log.info(successPeopleCount + "명이 성공했어요. 성공한 사람은" + getCoin + "만큼의 돈을 나눠가져요");

                    if(getCoin != 0) {
                        String shelterAddress = campaignListRepo.findCampaignByCampaignId(c.getCampaignId()).getWalletAddress();
                        for(User successUser:successUsers){
                            sendKlay(successUser, getCoin, true, shelterAddress);
                        }
                    }

                } else if (c.getChallengeType() == 2) {
                    log.info("판넬뒤집기 챌린지가 종료되었어요!");
                    ArrayList<HashMap> newRankInfoList = new ArrayList<>();
                    ArrayList<com.ssafy.ChallenMungs.challenge.panel.handler.RankVo> ri = panelSocketHandler.challengeManager.get(c.getChallengeId()).rankInfo;
                    int[] myklay = new int[panelSocketHandler.challengeManager.get(c.getChallengeId()).rankInfo.size()];
                    if (c.getGameType() == 1) { // 판넬뒤집기 개인전
                        int klaySum = 0;
                        for (int i = 1; i < myklay.length; i++) {
                            myklay[i] = c.getEntryFee() * 2 / c.getCurrentParticipantCount() * (myklay.length - i - 1);
                            klaySum += myklay[i];
                        }
                        myklay[0] = totalKlay - klaySum;
                    } else if (c.getGameType() == 2) {
                        int sum1 = 0; int cnt1 = 0;
                        int sum2 = 0; int cnt2 = 0;
                        for (RankVo r : panelSocketHandler.challengeManager.get(c.getChallengeId()).rankInfo) {
                            if (r.getTeamId() == 1) { sum1 += r.getPanelCount(); cnt1++; }
                            else if (r.getTeamId() == 2) { sum2 += r.getPanelCount(); cnt2++; }
                        }
                        int klayindi = 0;
                        int remain = 0;
                        int winner = 0;
                        if (sum1 > sum2) {
                            klayindi = (int) (c.getEntryFee() * c.getCurrentParticipantCount() / sum1);
                            remain = c.getEntryFee() * c.getCurrentParticipantCount() - klayindi * sum1;
                            winner = 1;
                        } else if (sum2 > sum1) {
                            klayindi = (int) (c.getEntryFee() * c.getCurrentParticipantCount() / sum2);
                            remain = c.getEntryFee() * c.getCurrentParticipantCount() - klayindi * sum2;
                            winner = 2;
                        } else {
                            klayindi = c.getEntryFee();
                        }
                        int idx = 0; boolean f = false;
                        for (RankVo r : panelSocketHandler.challengeManager.get(c.getChallengeId()).rankInfo) {
                            if (r.getTeamId() == winner || winner == 0) {
                                myklay[idx] = klayindi;
                                if (f == false) { myklay[idx] += remain; f = true; }
                            }
                            idx++;
                        }
                    }
                    log.info("팀무승부 중인지 판단해요");
                    boolean teamDraw = false;
                    if (c.getGameType() == 2) {
                        int[] sum = new int[2];
                        for (RankVo rv : panelSocketHandler.challengeManager.get(c.getChallengeId()).rankInfo) {
                            sum[rv.getTeamId() - 1] += rv.getPanelCount();
                        }
                        if (sum[0] == sum[1]) teamDraw = true;
                    }
                    int idx = 1;
                    for (com.ssafy.ChallenMungs.challenge.panel.handler.RankVo rv : ri) {
                        User u = userService.findUserByLoginId((String) rv.getLoginId()); // 팀전일 경우 LoginId가 ArrayList라 고쳐야햄
                        MyChallenge mc = myChallengeService.findByLoginIdAndChallengeId(u.getLoginId(), c.getChallengeId());
                        mc.setSuccessCount(rv.getTeamRank());
                        HashMap<String, Object> newRankInfoMap = new HashMap<>();
                        newRankInfoMap.put("loginId", u.getLoginId());
                        newRankInfoMap.put("name", u.getName());
                        newRankInfoMap.put("profile", u.getProfile());
                        newRankInfoMap.put("indiRank", rv.getIndiRank());
                        newRankInfoMap.put("teamRank", rv.getTeamRank());
                        newRankInfoMap.put("teamId", rv.getTeamId());
                        newRankInfoMap.put("point", rv.getPanelCount());
                        newRankInfoMap.put("obtainKlay", myklay[idx-1]);
                        newRankInfoList.add(newRankInfoMap);
                        sendKlay(u, myklay[idx-1], false, null);
                        idx++;
                        mc.setSuccessResult(rv.getTeamRank());
                        myChallengeService.save(mc);
                    }
                    try {
                        sb.append("{\nmapInfo:");
                        sb.append(mapper.writeValueAsString(panelSocketHandler.challengeManager.get(c.getChallengeId()).getMapInfo()));
                        sb.append(",\ntitle:");
                        sb.append(c.getTitle());
                        sb.append(",\nrankInfo:");
                        sb.append(mapper.writeValueAsString(newRankInfoList));
                        sb.append(",\ncenterLat:");
                        sb.append(c.getCenterLat());
                        sb.append(",\ncenterLng:");
                        sb.append(c.getCenterLng());
                        sb.append(",\ngameType:");
                        sb.append(c.getGameType());
                        sb.append(",\nteamDraw:");
                        sb.append(teamDraw);
                        sb.append("\n}");
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                    fileManager.saveResult(Long.toString(c.getChallengeId()), sb.toString());
                    panelSocketHandler.challengeManager.remove(c.getChallengeId());
                } else if (c.getChallengeType() == 3) {
                    log.info("보물찾기 챌린지가 종료되었어요!");
                    log.info("랭킹정보를 생성해요");
                    System.out.println(treasureSocketHandler.challengeManager.get(c.getChallengeId()));
                    int [] myklay = new int [treasureSocketHandler.challengeManager.get(c.getChallengeId()).rankInfo.size()];
                    int klaySum = 0;
                    for (int i = 1; i < myklay.length; i++) {
                        myklay[i] = c.getEntryFee() * 2 / c.getCurrentParticipantCount() * (myklay.length - i - 1);
                        klaySum += myklay[i];
                    }
                    myklay[0] = totalKlay - klaySum;
                    int idx = 1;
                    ArrayList<HashMap> newRankInfoList = new ArrayList<>();
                    System.out.println(treasureSocketHandler.challengeManager.get(c.getChallengeId()) + " " + treasureSocketHandler.challengeManager.get(c.getChallengeId()).rankInfo);
                    for (com.ssafy.ChallenMungs.challenge.treasure.handler.RankVo rv : treasureSocketHandler.challengeManager.get(c.getChallengeId()).rankInfo) {
                        User u = userService.findUserByLoginId(rv.getLoginId());
                        System.out.println(rv.getLoginId());
                        System.out.println(u.getLoginId() + " " + c.getChallengeId());
                        MyChallenge mc = myChallengeService.findByLoginIdAndChallengeId(u.getLoginId(), c.getChallengeId());
                        mc.setSuccessCount(rv.getTeamRank());
                        HashMap<String, Object> newRankInfoMap = new HashMap<>();
                        newRankInfoMap.put("loginId", u.getLoginId());
                        newRankInfoMap.put("name", u.getName());
                        newRankInfoMap.put("profile", u.getProfile());
                        newRankInfoMap.put("rank", rv.getTeamRank());
                        newRankInfoMap.put("point", rv.getPoint());
                        newRankInfoMap.put("obtainKlay", myklay[idx-1]);
                        newRankInfoMap.put("myTreasureList", rv.getMyTreasureList());
                        newRankInfoList.add(newRankInfoMap);
                        sendKlay(u, myklay[idx-1], false, null);
                        idx++;
                        mc.setSuccessResult(rv.getTeamRank());
                        myChallengeService.save(mc);
                    }
                    try {
                        sb.append("{\nrankInfo:");
                        sb.append(mapper.writeValueAsString(newRankInfoList));
                        sb.append(",\ntitle:");
                        sb.append(c.getTitle());
                        sb.append(",\ntreasureInfo:");
                        sb.append(mapper.writeValueAsString(treasureSocketHandler.challengeManager.get(c.getChallengeId()).treasureInfo));
                        sb.append(",\ncenterLat:");
                        sb.append(c.getCenterLat());
                        sb.append(",\ncenterLng:");
                        sb.append(c.getCenterLng());
                        sb.append("\n}");
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                    log.info("보물찾기 결과 파일을 만들어요!");
                    fileManager.saveResult(Long.toString(c.getChallengeId()), sb.toString());
                    treasureSocketHandler.challengeManager.remove(c.getChallengeId());
                }
            }
            if (flag) {
                challengeService.save(c);
            }
        }
    }

    // 특별챌린지 보상 나누기(특별챌린지 지갑 -> 고객 지갑 클레이튼 전송)
    void sendKlay(User user, Integer intklay, boolean normal, String shelterAddress) {
        BigInteger klayForm = BigInteger.valueOf(intklay).multiply(BigInteger.TEN.pow(18));
        String hexString = "0x" + klayForm.toString(16);
        System.out.println(klayForm);
        System.out.println(hexString);
        String fromAddress;
        if(normal){
            fromAddress = "0x2649eadC4C15bac554940A0A702fa759bddf0dBe";
        }
        else{
            fromAddress = "0xee43BB5476e52B04175d698C56cC4516b96A85A5";
        }
        String userAddress = walletRepo.findByUserAndType(user,'p').getAddress();
        RestTemplate restTemplate = new RestTemplate();

        // 요청 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("x-chain-id", "1001"); // 1001(Baobob 테스트넷)
        headers.set("Authorization", "Basic S0FTS1dDQUdINjkwRkFRV0lPVDE4QkhUOnNTYThjQlI1akhncXRwbnUtWWltMHV5dkVpb1V2REVQRGpMSmJjRkM="); //AccountPool 등록

        // 요청 바디 설정
        JSONObject requestBody = new JSONObject();
        requestBody.put("from", fromAddress);
        requestBody.put("value", hexString);
        requestBody.put("to", userAddress);
        requestBody.put("submit", true);

        // 요청 엔티티 생성
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody.toString(), headers);

        // POST 요청 보내기
        String url = "https://wallet-api.klaytnapi.com/v2/tx/fd/value";
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        String responseBody = responseEntity.getBody();

        if(normal){
            System.out.println(shelterAddress);
            RestTemplate restTemplate2 = new RestTemplate();

            // 요청 헤더 설정
            HttpHeaders headers2 = new HttpHeaders();
            headers2.setContentType(MediaType.APPLICATION_JSON);
            headers2.set("x-chain-id", "1001"); // 1001(Baobob 테스트넷)
            headers2.set("Authorization", "Basic S0FTS1dDQUdINjkwRkFRV0lPVDE4QkhUOnNTYThjQlI1akhncXRwbnUtWWltMHV5dkVpb1V2REVQRGpMSmJjRkM="); //AccountPool 등록

            // 요청 바디 설정
            JSONObject requestBody2 = new JSONObject();
            requestBody2.put("from", userAddress);
            requestBody2.put("value", hexString);
            requestBody2.put("to", shelterAddress);
            requestBody2.put("submit", true);

            // 요청 엔티티 생성
            HttpEntity<String> requestEntity2 = new HttpEntity<>(requestBody2.toString(), headers);

            // POST 요청 보내기
            ResponseEntity<String> responseEntity2 = restTemplate.exchange(url, HttpMethod.POST, requestEntity2, String.class);
            System.out.println("요청 완료");
            String responseBody2 = responseEntity2.getBody();
        }
    }
}
