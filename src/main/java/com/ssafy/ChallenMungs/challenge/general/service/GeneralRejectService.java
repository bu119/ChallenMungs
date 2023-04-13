package com.ssafy.ChallenMungs.challenge.general.service;

import com.ssafy.ChallenMungs.challenge.common.entity.Challenge;
import com.ssafy.ChallenMungs.challenge.general.entity.GeneralBoard;
import com.ssafy.ChallenMungs.challenge.general.entity.GeneralReject;
import com.ssafy.ChallenMungs.challenge.general.repository.GeneralBoardRepository;
import com.ssafy.ChallenMungs.challenge.general.repository.GeneralRejectRepository;
import com.ssafy.ChallenMungs.user.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class GeneralRejectService {

    private Logger log = LoggerFactory.getLogger(GeneralRejectService.class);

    @Autowired
    private GeneralBoardRepository boardRepository;

    @Autowired
    private GeneralRejectRepository rejectRepository;

    public boolean existsByBoardAndUser(GeneralBoard board, User user) {
        return rejectRepository.existsByBoardAndUser(board, user);
    }




    // boardId와 user를 받아서 해당 board의 rejectCount를 1 증가하고, GeneralReject 테이블 추가
    @Transactional
    public boolean addOrCancelReject(Integer boardId, User user) {
        GeneralBoard board = boardRepository.findByBoardId(boardId);

        if (board.getUser().equals(user)) {
            // 작성자와 현재 유저가 같으면 reject 추가 불가
            log.info("본인 사진입니다.");
            return false;
        }

        boolean exists = rejectRepository.existsByBoardAndUser(board, user);

        if (exists) {
            log.info("이미 반려한 사진입니다. 반려 취소를 실행합니다.");
            // 이미 reject한 적 있는 경우 반려하기를 취소하여 reject 테이블 삭제하고 rejectCount -1
            // GeneralReject 테이블에서 해당 기록 삭제
            GeneralReject reject = rejectRepository.findByBoardAndUser(board, user);
            rejectRepository.delete(reject);

            // 해당 board의 rejectCount 감소
            board.setRejectCount(board.getRejectCount() - 1);
            boardRepository.save(board);

            // 0이상인 조건을 달았어욤...
//            if (board.getRejectCount() > 0) {
//                // rejectCount가 0보다 작아지는 것을 방지하기 위해 확인
//                board.setRejectCount(board.getRejectCount() - 1);
//                boardRepository.save(board);
//            }

            log.info("반려 취소 성공");
            return true;

        }

        board.setRejectCount(board.getRejectCount() + 1);
        boardRepository.save(board);

        GeneralReject reject = new GeneralReject();
        reject.setBoard(board);
        reject.setUser(user);
        rejectRepository.save(reject);

//        GeneralReject reject = new GeneralReject.Builder()
//                .user(user)
//                .board(board)
//                .build();
//        rejectRepository.save(reject);
        log.info("반려하기 성공");
        return true;
    }
}
