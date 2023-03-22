package com.ssafy.ChallenMungs.user.repository;

import com.ssafy.ChallenMungs.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface UserRepository extends JpaRepository<User, Long>, QuerydslPredicateExecutor<User> {
    int countByLoginId(String str);
    User findUserByLoginId(String loginId);
    void deleteUserByLoginId(String loginId);
}
