package com.ssafy.ChallenMungs.challenge.general.service;

import com.ssafy.ChallenMungs.challenge.common.repository.ChallengeRepository;
import com.ssafy.ChallenMungs.challenge.general.entity.GeneralBoard;
import com.ssafy.ChallenMungs.challenge.general.repository.GeneralBoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
public class GeneralBoardService {

    @Autowired
    private GeneralBoardRepository boardRepository;

    @Autowired
    ChallengeRepository challengeRepository;

    // 인증을 사진을 등록하는 함수
    public Integer savePicture(GeneralBoard generalBoard) {
        boardRepository.save(generalBoard);
        return generalBoard.getBoardId();
    }

    // today에 등록된 글 가져오기
    public ResponseEntity<List<GeneralBoard>> getBoardsByChallengeIdToday(Long challengeId) {
        LocalDate today = LocalDate.now();
        List<GeneralBoard> boards = boardRepository.findAllByChallengeIdAndRegisterDay(challengeId, today);
        if (boards.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(boards);
    }

    // 히스토리 가져오기
    public ResponseEntity<List<GeneralBoard>> getBoardsByChallengeIdAndLoginId(Long challengeId, String loginId) {
        List<GeneralBoard> boards = boardRepository.findByChallengeIdAndLoginId(challengeId, loginId);
        if (boards.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(boards);
    }

    public GeneralBoard findByChallengeIdAndLoginIdAndRegisterDay(Long challengeId, String loginId, LocalDate registerDay) {
        return boardRepository.findByChallengeIdAndLoginIdAndRegisterDay(challengeId, loginId, registerDay);
    }

    // 반려하기 (과반수이상이면 게시물 삭제)
    public ResponseEntity<Void> increaseRejectCountAndDelete(Long challengeId, String loginId, String userLoginId) {
        LocalDate registerDay = LocalDate.now();
        GeneralBoard generalBoard = boardRepository.findByChallengeIdAndLoginIdAndRegisterDay(challengeId, loginId, registerDay);
        if (generalBoard != null) {
            // 게시글의 loginId와 userLoginId가 다른 경우
            if (!userLoginId.equals(loginId)) {

                int currentRejectCount = generalBoard.getRejectCount();

                int maxParticipantCount = challengeRepository.findByChallengeId(challengeId).getMaxParticipantCount();

                // rejectCount가 maxParticipantCount의 과반수 미만인 경우
                if (currentRejectCount + 1 < maxParticipantCount / 2) {
                    generalBoard.setRejectCount(currentRejectCount + 1);
                    boardRepository.save(generalBoard);

                } else {
                    // rejectCount가 maxParticipantCount의 과반수 이상인 경우
                    boardRepository.delete(generalBoard);
                }
                return ResponseEntity.ok().build();
            }
        }
        return ResponseEntity.notFound().build();
    }

}



