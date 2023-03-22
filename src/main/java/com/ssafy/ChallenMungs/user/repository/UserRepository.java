package com.ssafy.ChallenMungs.user.repository;

import com.ssafy.ChallenMungs.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import javax.transaction.Transactional;

public interface UserRepository extends JpaRepository<User, Long>, QuerydslPredicateExecutor<User> {
    int countByLoginId(String str);
    User findUserByLoginId(String loginId);

    @Transactional
    void deleteUserByLoginId(String loginId);
}
