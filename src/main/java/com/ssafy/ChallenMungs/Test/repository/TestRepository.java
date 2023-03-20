package com.ssafy.ChallenMungs.Test.repository;

import com.ssafy.ChallenMungs.Test.entity.Test;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TestRepository  extends JpaRepository<Test, Long> {
    //커스텀 쿼리 메서드
    List<Test> findByNameContaining(String name);
}
