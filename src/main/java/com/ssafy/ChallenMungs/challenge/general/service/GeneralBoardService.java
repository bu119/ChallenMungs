package com.ssafy.ChallenMungs.challenge.general.service;

import com.ssafy.ChallenMungs.challenge.common.entity.Challenge;
import com.ssafy.ChallenMungs.challenge.common.repository.ChallengeRepository;
import com.ssafy.ChallenMungs.challenge.general.entity.GeneralBoard;
import com.ssafy.ChallenMungs.challenge.general.repository.GeneralBoardRepository;
import com.ssafy.ChallenMungs.user.entity.User;
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
    public List<GeneralBoard> getBoardsByChallengeToday(Challenge challenge) {
        LocalDate today = LocalDate.now();
        List<GeneralBoard> boards = boardRepository.findAllByChallengeAndRegisterDay(challenge, today);
        if (boards.isEmpty()) {
            return null;
        }
        return boards;
    }

    // 히스토리 가져오기
    public ResponseEntity<List<GeneralBoard>> getBoardsByChallengeAndUser(Challenge challenge, User user) {
        List<GeneralBoard> boards = boardRepository.findAllByChallengeAndUser(challenge, user);
        if (boards.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(boards);
    }


    public GeneralBoard findByChallengeAndUserAndRegisterDay(Challenge challenge, User user, LocalDate registerDay) {
        return boardRepository.findByChallengeAndUserAndRegisterDay(challenge, user, registerDay);
    }

//    12시가 지나면 반려된 수가 과반수 이상인 게시물에 마이챌린지에 성공 +1
    public List<GeneralBoard> findAllByRegisterDay(LocalDate yesterday) {
        return boardRepository.findAllByRegisterDay(yesterday);
    }

}



