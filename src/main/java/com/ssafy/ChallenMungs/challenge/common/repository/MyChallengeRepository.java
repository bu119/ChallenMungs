package com.ssafy.ChallenMungs.challenge.common.repository;

import com.ssafy.ChallenMungs.challenge.common.entity.Challenge;
import com.ssafy.ChallenMungs.challenge.common.entity.MyChallenge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

public interface MyChallengeRepository extends JpaRepository<MyChallenge, Long>, QuerydslPredicateExecutor<MyChallenge> {
    List<MyChallenge> findAllByLoginId();
}
