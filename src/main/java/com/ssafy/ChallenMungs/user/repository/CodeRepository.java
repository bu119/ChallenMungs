package com.ssafy.ChallenMungs.user.repository;

import com.ssafy.ChallenMungs.user.entity.Code;
import com.ssafy.ChallenMungs.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import javax.transaction.Transactional;
import java.util.List;

public interface CodeRepository extends JpaRepository<Code, Long>, QuerydslPredicateExecutor<Code> {
    List<Code> findAllByCharityName(String charityName);

    @Transactional
    void deleteByCharityNameAndInviteCode(String charityName, String inviteCode);
}
