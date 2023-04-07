package com.ssafy.ChallenMungs.challenge.general.service;

import com.ssafy.ChallenMungs.challenge.common.entity.Challenge;
import com.ssafy.ChallenMungs.challenge.common.entity.MyChallenge;
import com.ssafy.ChallenMungs.challenge.common.repository.ChallengeRepository;
import com.ssafy.ChallenMungs.challenge.common.repository.MyChallengeRepository;
import com.ssafy.ChallenMungs.challenge.common.service.ChallengeService;
import com.ssafy.ChallenMungs.challenge.general.entity.GeneralBoard;
import com.ssafy.ChallenMungs.challenge.general.repository.GeneralBoardRepository;
import com.ssafy.ChallenMungs.user.entity.User;
import com.ssafy.ChallenMungs.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class GeneralBoardService {

    @Autowired
    private GeneralBoardRepository boardRepository;

    @Autowired
    ChallengeRepository challengeRepository;

    @Autowired
    MyChallengeRepository myChallengeRepository;

    @Autowired
    UserService userService;

    @Autowired
    ChallengeService challengeService;

    // 인증을 사진을 등록하는 함수
    public Integer savePicture(GeneralBoard generalBoard) {
        boardRepository.save(generalBoard);
        return generalBoard.getBoardId();
    }

    // today에 등록된 글 가져오기
    public List<GeneralBoard> getBoardsByChallengeToday(Challenge challenge) {
        LocalDate today = LocalDate.now(ZoneId.of("Asia/Seoul"));
        List<GeneralBoard> boards = boardRepository.findAllByChallengeAndRegisterDay(challenge, today);
        if (boards.isEmpty()) {
            return null;
        }
        return boards;
    }

    // 히스토리 가져오기
    public List<GeneralBoard> getBoardsByChallengeAndUser(Challenge challenge, User boardUser) {
        // 보드 유저 꺼 누르면 보드유저꺼 보이고 반려하기 여부 보이기
        // 챌린지에 해당유저의 기록 가져옴
        List<GeneralBoard> boards = boardRepository.findAllByChallengeAndUser(challenge, boardUser);
        if (boards.isEmpty()) {
            return null;
        }
        return boards;
    }

    // 히스토리 전체 목록 가져오기기
   public List<GeneralBoard> getBoardsByChallenge(Challenge challenge) {
        // 해당 챌린지의 모든 기록 가져옴
        List<GeneralBoard> boards = boardRepository.findAllByChallenge(challenge);
        if (boards.isEmpty()) {
            return null;
        }
        return boards;

   }

   // 과반주 이상의 반려를 받았는지 확인
    public boolean isReject(GeneralBoard board) {

        if (board == null) {
            throw new IllegalArgumentException("Invalid board ID");
        }

        Challenge challenge = board.getChallenge();
        if (challenge == null) {
            throw new IllegalStateException("Board has no associated challenge");
        }

        Integer maxParticipantCount = challenge.getMaxParticipantCount();
        Integer rejectCount = board.getRejectCount();

        if (rejectCount >= (maxParticipantCount / 2)) {
            return true;
        } else {
            return false;
        }
    }


    public GeneralBoard findByChallengeAndUserAndRegisterDay(Challenge challenge, User user, LocalDate registerDay) {
        return boardRepository.findByChallengeAndUserAndRegisterDay(challenge, user, registerDay);
    }

    // 12시가 지나면 반려된 수가 과반수 이상인 게시물에 마이챌린지에 성공 +1
    public List<GeneralBoard> findAllByRegisterDay(LocalDate yesterday) {
        return boardRepository.findAllByRegisterDay(yesterday);
    }

    // SuccessCount를 갱신하는 함수
    public void updateSuccessCount(String loginId, Long challengeId) {
        // 해당 유저
        User user = userService.findUserByLoginId(loginId);
        // 해당 챌린지
        Challenge challenge = challengeService.findByChallengeId(challengeId);

        int maxParticipantCount = challenge.getMaxParticipantCount();
        List<GeneralBoard> boardList = boardRepository.findAllByChallengeAndUser(challenge,user);
        int successCount = 0;
        for (GeneralBoard board : boardList) {
            if (board.getRejectCount() < maxParticipantCount / 2) {
                successCount++;
            }
        }

        MyChallenge myChallenge = myChallengeRepository.findByLoginIdAndChallengeId(loginId, challengeId);
        myChallenge.setSuccessCount(successCount);
        myChallengeRepository.save(myChallenge);
    }

//    public static int calculateSuccessRatio(int successCount, LocalDate startDate, LocalDate endDate) {
//        long daysBetween = ChronoUnit.DAYS.between(startDate, endDate) + 2;
//        double successRatio = (double) successCount / daysBetween * 100;
//        return (int) Math.round(successRatio);
//    }

}



