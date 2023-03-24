package com.ssafy.ChallenMungs.challenge.general.repository;

import com.ssafy.ChallenMungs.challenge.general.entity.GeneralParticipant;
import com.ssafy.ChallenMungs.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface GeneralParticipantRepository  extends JpaRepository<GeneralParticipant, Long>, QuerydslPredicateExecutor<GeneralParticipant> {
    GeneralParticipant findUserByLoginId(String loginId);

}
