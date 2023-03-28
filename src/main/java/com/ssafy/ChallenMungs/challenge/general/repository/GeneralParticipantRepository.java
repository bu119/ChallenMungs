package com.ssafy.ChallenMungs.challenge.general.repository;

import com.ssafy.ChallenMungs.challenge.common.entity.MyChallenge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface GeneralParticipantRepository extends JpaRepository<MyChallenge, Long>, QuerydslPredicateExecutor<MyChallenge> {

}
