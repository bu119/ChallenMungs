package com.ssafy.ChallenMungs.Test.repository;

import com.ssafy.ChallenMungs.Test.entity.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TestRepository  extends JpaRepository<Test, Long> {
    //커스텀 쿼리 메서드
    List<Test> findByNameContaining(String name);

    //직접 쿼리문 짜는 방식
    @Query("select m from Test m where m.name = :name")
    List<Test> testNative(@Param("name") String name);
}
