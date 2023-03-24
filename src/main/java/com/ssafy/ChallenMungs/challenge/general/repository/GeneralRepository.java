package com.ssafy.ChallenMungs.challenge.general.repository;

import com.ssafy.ChallenMungs.challenge.common.entity.Challenge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface GeneralRepository extends JpaRepository<Challenge, Long>, QuerydslPredicateExecutor<Challenge> {


}

