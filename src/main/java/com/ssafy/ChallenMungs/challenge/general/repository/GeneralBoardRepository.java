package com.ssafy.ChallenMungs.challenge.general.repository;

import com.ssafy.ChallenMungs.challenge.common.entity.Challenge;
import com.ssafy.ChallenMungs.challenge.general.entity.GeneralBoard;
import com.ssafy.ChallenMungs.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface GeneralBoardRepository extends JpaRepository<GeneralBoard, Long>, QuerydslPredicateExecutor<GeneralBoard> {

    // today 등록된 모든 게시글 가져오기 (하루가 끝날 때 성공 카운트 올릴 때 필요)
    List<GeneralBoard> findAllByRegisterDay(LocalDate today);

    // today 인증 가져오기 (challenge와 registerDay에 해당하는 게시글을 모두 조회)
    List<GeneralBoard> findAllByChallengeAndRegisterDay(Challenge challenge, LocalDate registerDay);

    // 유저의 인증 히스토리 가져오기 (challenge와 user에 해당하는 게시글을 모두 조회)
    List<GeneralBoard> findAllByChallengeAndUser(Challenge challenge, User user);

    // 해당 challenge에 해당하는 모든 Board를 찾아서 리스트 형태로 반환
    List<GeneralBoard> findAllByChallenge(Challenge challenge);

    // 반려하기 누르면 challenge, user, registerDay에 해당하는 게시글 조회
    GeneralBoard findByChallengeAndUserAndRegisterDay(Challenge challenge, User user, LocalDate registerDay);

    // 반려하기 누르면 boardId에 해당하는 게시글 조회
    GeneralBoard findByBoardId(int boardId);

}
