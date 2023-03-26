package com.ssafy.ChallenMungs.challenge.common.controller;

import com.ssafy.ChallenMungs.challenge.common.entity.Challenge;
import com.ssafy.ChallenMungs.challenge.common.entity.MyChallenge;
import com.ssafy.ChallenMungs.challenge.common.service.ChallengeService;
import com.ssafy.ChallenMungs.challenge.common.service.MyChallengeService;
import com.ssafy.ChallenMungs.challenge.panel.handler.ChallengeVo;
import com.ssafy.ChallenMungs.challenge.panel.handler.PanelSocketHandler;
import com.ssafy.ChallenMungs.challenge.panel.handler.PlayerVo;
import com.ssafy.ChallenMungs.challenge.panel.handler.RankVo;
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

//    @Scheduled(cron = "0 0 0 * * ?") // 매일 자정에 동작해요
    @Scheduled(cron = "0/5 * * * * ?") // 5초마다 실행해요
    public void startChallenge() {
        System.out.println("스케쥴러가 동작해요!");
        boolean flag;
        // 시작하면 teamId를 다시 정의해줘요
        List<Challenge> challenges = challengeService.findAll();
        for (Challenge c : challenges) {
            LocalDate today = LocalDate.now();
            flag = false;
            if (c.getStatus() == 0 && c.getStartDate().equals(today)) {
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
                    // 일단 가로 세로 길이를 받아온다
                    // 네모가 생기면 아래와 왼쪽 변을 기준으로 (대충)100m로 몇번 나눌 수 있는지 센다
                    // 만약 아래변이 2.56km라고 해보자 그럼 26개의 구역으로 나눌수 있다 그러니 딱 100m라고 하지말자 균일한 크기를 위해서도
                    // 그럼 이차원 배열의 각 왼쪽위를 두 좌표로 맵핑 할 수 있다. 예를 들어 지도의 가장 서쪽 경도가 a, 가장 동쪽 경도가 b라고 해보자
                    // 그럼 idx = 0 이 이차원 배열의 첫번째 번호라고 할 때 n(n <= 25)번째 요소의 경도는 a + (b - a) / 26 * n이다
                    // 마찬가지로 왼쪽변이 1.94km라고 해보자 그럼 20개의 구역으로 나눌 수 있고 가장 북쪽 위도가 a, 가장 남쪽 위도가 b라 할때
                    // n번째 요소의 위도는 a + (b - a) / 20 * n 이다
                    // 그럼 위도 c, 경도 d가 주어 졌을때 이차원배열의 [n][m]에 대응한다고 할 때 n, m은 얼마일까?
                    // 다시 예를 들어보자 경기장의 가장 왼쪽위는 (x1, y1)이다 오른쪽아래는 (x2, y2)다 한칸을 대충 k(m)로 잡자 현재위치 (a,b)가 주어쪗을때
                    // 이에 대응하는 이차원배열의 index [n][m]는 얼마일까?
                    // 먼저 26과 20에 대응하는 수를 구해보자
                    // 위도칸수 xd = (x2 - x1) // (k / 1000)
                    // 경도칸수 yd = (y2 - y1) // (k / 1000)
                    // a <= x1 + ((x2 - x1) / xd) * n
                    // b <= y1 + ((y2 - y1) / yd) * m
                    // (a - x1) / ((x2 - x1) / xd) = n
                    // (b - y1) / ((y2 - y1) / yd) = m
                    Double cell_size = 50.0;// m다
//                    log.info("세로 길이:" + getDistance(c.getLeftTopLat(), c.getRightBottomLng(), c.getRightBottomLat(), c.getRightBottomLng()) + " 한칸길이:" + (cell_size / 1000) + "세로 몇칸:" + (int) Math.ceil(getDistance(c.getLeftTopLat(), c.getRightBottomLng(), c.getRightBottomLat(), c.getRightBottomLng()) / (cell_size / 1000)));
                    int xd = (int) Math.ceil(getDistance(c.getLeftTopLat(), c.getRightBottomLng(), c.getRightBottomLat(), c.getRightBottomLng()) / (cell_size / 1000));
                    int yd = (int) Math.ceil(getDistance(c.getLeftTopLat(), c.getLeftTopLng(), c.getLeftTopLat(), c.getRightBottomLng()) / (cell_size / 1000));

                    panelSocketHandler.challengeManager.put(c.getChallengeId(), ChallengeVo.builder().players(new ArrayList<PlayerVo>()).mapInfo(new int [xd] [yd]).rankInfo(rankInfo).build());
                }
                challengeService.save(c);
            }
            // 예를 들어 2일에 끝나는 겜이면 3일 자정에 끝나야됨
            if (c.getEndDate().minusDays(1).equals(today)) {
                c.setStatus(2);
                flag = true;
            }
            if (flag) {
                challengeService.save(c);
            }
        }
    }
    double getDistance(double lat1, double lon1, double lat2, double lon2) {
        final double R = 6371; // 지구 반경 (km)
        double lat1Rad = Math.toRadians(lat1);
        double lat2Rad = Math.toRadians(lat2);
        double lon1Rad = Math.toRadians(lon1);
        double lon2Rad = Math.toRadians(lon2);

        double x1 = R * Math.cos(lat1Rad) * Math.cos(lon1Rad);
        double y1 = R * Math.cos(lat1Rad) * Math.sin(lon1Rad);
        double z1 = R * Math.sin(lat1Rad);

        double x2 = R * Math.cos(lat2Rad) * Math.cos(lon2Rad);
        double y2 = R * Math.cos(lat2Rad) * Math.sin(lon2Rad);
        double z2 = R * Math.sin(lat2Rad);

        Vector3D p1 = new Vector3D(x1, y1, z1);
        Vector3D p2 = new Vector3D(x2, y2, z2);

        return R * Vector3D.angle(p1, p2);
    }
}
