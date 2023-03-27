package com.ssafy.ChallenMungs.challenge.general.repository;

import com.ssafy.ChallenMungs.challenge.common.entity.MyChallenge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface GeneralParticipantRepository extends JpaRepository<MyChallenge, Long>, QuerydslPredicateExecutor<MyChallenge> {

//    MyChallenge findUserByLoginId(String loginId);
//
//    // 챌린지 id와 로그인 id로 참가자 정보를 조회하는 함수
//    MyChallenge findByLoginIdAndChallengeId(String loginId, Long challengeId);

}
