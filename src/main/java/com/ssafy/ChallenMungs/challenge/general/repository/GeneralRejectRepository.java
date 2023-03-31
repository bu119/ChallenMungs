package com.ssafy.ChallenMungs.challenge.general.repository;

import com.ssafy.ChallenMungs.challenge.general.entity.GeneralBoard;
import com.ssafy.ChallenMungs.challenge.general.entity.GeneralReject;
import com.ssafy.ChallenMungs.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface GeneralRejectRepository extends JpaRepository<GeneralReject, Long>,QuerydslPredicateExecutor<GeneralReject> {

    boolean existsByBoardAndUser(GeneralBoard board, User user);
    GeneralReject findByBoardAndUser(GeneralBoard board, User user);
}
