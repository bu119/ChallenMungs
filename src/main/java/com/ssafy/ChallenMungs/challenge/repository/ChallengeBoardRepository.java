package com.ssafy.ChallenMungs.challenge.repository;

import com.ssafy.ChallenMungs.challenge.entity.Challenge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface ChallengeBoardRepository extends JpaRepository<Challenge, Long>, QuerydslPredicateExecutor<Challenge> {
}
