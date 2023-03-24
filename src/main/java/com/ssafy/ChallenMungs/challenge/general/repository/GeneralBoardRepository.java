package com.ssafy.ChallenMungs.challenge.general.repository;

import com.ssafy.ChallenMungs.challenge.general.entity.GeneralBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface GeneralBoardRepository extends JpaRepository<GeneralBoard, Long>, QuerydslPredicateExecutor<GeneralBoard> {
}
