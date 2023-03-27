package com.ssafy.ChallenMungs.challenge.general.service;

import com.ssafy.ChallenMungs.challenge.general.entity.GeneralBoard;
import com.ssafy.ChallenMungs.challenge.general.repository.GeneralBoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class GeneralBoardService {

    @Autowired
    private GeneralBoardRepository boardRepository;

    // 인증을 사진을 등록하는 함수
    public Integer savePicture(GeneralBoard generalBoard) {
        return boardRepository.save(generalBoard).getBoardId();
    }

    // today에 등록된 글 가져오기
    public ResponseEntity<List<GeneralBoard>> getBoardsByChallengeIdToday(Long challengeId) {
        LocalDate today = LocalDate.now();
        List<GeneralBoard> boards = boardRepository.findByChallengeIdAndRegisterDay(challengeId, today);
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



}



