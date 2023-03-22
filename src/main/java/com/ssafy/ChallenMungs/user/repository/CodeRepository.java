package com.ssafy.ChallenMungs.user.repository;

import com.ssafy.ChallenMungs.user.entity.Code;
import com.ssafy.ChallenMungs.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface CodeRepository extends JpaRepository<Code, Long>, QuerydslPredicateExecutor<Code> {
}
