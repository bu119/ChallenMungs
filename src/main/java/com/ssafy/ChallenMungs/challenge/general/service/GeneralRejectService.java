package com.ssafy.ChallenMungs.challenge.general.service;

import com.ssafy.ChallenMungs.challenge.general.entity.GeneralBoard;
import com.ssafy.ChallenMungs.challenge.general.entity.GeneralReject;
import com.ssafy.ChallenMungs.challenge.general.repository.GeneralBoardRepository;
import com.ssafy.ChallenMungs.challenge.general.repository.GeneralRejectRepository;
import com.ssafy.ChallenMungs.user.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GeneralRejectService {

    private Logger log = LoggerFactory.getLogger(GeneralRejectService.class);

    @Autowired
    private GeneralBoardRepository boardRepository;

    @Autowired
    private GeneralRejectRepository rejectRepository;

    // boardId와 user를 받아서 해당 board의 rejectCount를 1 증가하고, GeneralReject 테이블 추가
    public boolean addReject(Integer boardId, User user) {
        GeneralBoard board = boardRepository.findByBoardId(boardId);

        if (board.getUser().equals(user)) {
            // 작성자와 현재 유저가 같으면 reject 추가 불가
            log.info("본인 사진입니다.");
            return false;
        }

        boolean exists = rejectRepository.existsByBoardAndUser(board, user);
        if (exists) {
            // 이미 reject한 적 있는 경우 reject 추가 불가
            log.info("이미 반려한 사진입니다.");
            return false;
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
