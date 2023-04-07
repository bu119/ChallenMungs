package com.ssafy.ChallenMungs.Test.repository;

import com.ssafy.ChallenMungs.Test.entity.Test;

import java.util.List;

public interface QuerydslRepository {
    List<Test> getList();
}
