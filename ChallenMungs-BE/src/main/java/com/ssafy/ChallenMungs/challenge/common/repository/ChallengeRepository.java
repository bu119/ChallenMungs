package com.ssafy.ChallenMungs.challenge.common.repository;

import com.ssafy.ChallenMungs.challenge.common.entity.Challenge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.ArrayList;
import java.util.List;

public interface ChallengeRepository extends JpaRepository<Challenge, Long>, QuerydslPredicateExecutor<Challenge> {
    List<Challenge> findAllByChallengeType(int i);

    List<Challenge> findAllByTitleLike(String s);

    List<Challenge> findAllByTitleLikeAndChallengeType(String s, int i);

    Challenge findByChallengeId(Long challengeId);

    List<Challenge> findAllByStatusAndChallengeType(int status, int challengeType);

    ArrayList<Challenge> findAllByStatus(int i);
}
