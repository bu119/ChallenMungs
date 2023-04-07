package com.ssafy.ChallenMungs.challenge.common.repository;

import com.ssafy.ChallenMungs.challenge.common.entity.Challenge;
import com.ssafy.ChallenMungs.challenge.common.entity.MyChallenge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import javax.transaction.Transactional;
import java.util.List;

public interface MyChallengeRepository extends JpaRepository<MyChallenge, Long>, QuerydslPredicateExecutor<MyChallenge> {
    List<MyChallenge> findAllByLoginId(String loginId);
    @Transactional
    void deleteByLoginIdAndChallengeId(String loginId, Long challengeId);

    List<MyChallenge> findAllByChallengeId(Long challengeId);

    MyChallenge findByLoginIdAndChallengeId(String loginId, Long challengeId);

    // 챌린지 id가 같고 같은 successResult 결과를 반환
    List<MyChallenge> findByChallengeIdAndSuccessResult(Long challengeId, Boolean successResult);


}
