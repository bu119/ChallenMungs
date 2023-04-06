package com.ssafy.ChallenMungs.user.repository;

import com.ssafy.ChallenMungs.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import javax.transaction.Transactional;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long>, QuerydslPredicateExecutor<User> {
    int countByLoginId(String str);
    User findUserByLoginId(String loginId);
    @Transactional
    void deleteUserByLoginId(String loginId);
    boolean existsByLoginId(String loginId);
    boolean existsUserByLoginIdAndPassword(String loginId, String password);
    User findUserByLoginIdAndPassword(String loginId, String password);
    List<User> findAllByType(char c);
}
