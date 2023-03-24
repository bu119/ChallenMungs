package com.ssafy.ChallenMungs.challenge.common.repository;

import com.ssafy.ChallenMungs.challenge.common.entity.Challenge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

public interface ChallengeRepository extends JpaRepository<Challenge, Long>, QuerydslPredicateExecutor<Challenge> {
    List<Challenge> findByChallengeType(int i);
}
