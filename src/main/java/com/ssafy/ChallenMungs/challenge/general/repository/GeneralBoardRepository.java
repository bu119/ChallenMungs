package com.ssafy.ChallenMungs.challenge.general.repository;

import com.ssafy.ChallenMungs.challenge.common.entity.Challenge;
import com.ssafy.ChallenMungs.challenge.general.entity.GeneralBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface GeneralBoardRepository extends JpaRepository<GeneralBoard, Long>, QuerydslPredicateExecutor<GeneralBoard> {

    GeneralBoard findByChallengeId(Long challengeId);

    // today 인증 가져오기
    List<GeneralBoard> findByChallengeIdAndRegisterDay(Long challengeId, LocalDate registerDay);

    // 유저의 히스토리 가져오기
    List<GeneralBoard> findByChallengeIdAndLoginId(Long challengeId, String loginId);
}
