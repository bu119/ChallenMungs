package com.ssafy.ChallenMungs.challenge.panel.repository;

import com.ssafy.ChallenMungs.challenge.common.entity.Challenge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface PanelRepositoryForChallenge extends JpaRepository<Challenge, Long>, QuerydslPredicateExecutor<Challenge> {
}
