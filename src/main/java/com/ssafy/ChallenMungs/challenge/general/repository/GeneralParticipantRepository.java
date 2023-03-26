package com.ssafy.ChallenMungs.challenge.general.repository;

import com.ssafy.ChallenMungs.challenge.general.entity.GeneralParticipant;
import com.ssafy.ChallenMungs.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface GeneralParticipantRepository  extends JpaRepository<GeneralParticipant, Long>, QuerydslPredicateExecutor<GeneralParticipant> {
    GeneralParticipant findUserByLoginId(String loginId);

    // 챌린지 id와 로그인 id로 참가자 정보를 조회하는 함수
    GeneralParticipant findByLoginIdAndChallengeId(String loginId, Long challengeId);

    // 챌린지 id와 로그인 id로 참가자 정보를 삭제하는 함수
    void deleteByChallengeIdAndLoginId(Long challengeId, String loginId);

}
