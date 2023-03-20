package com.ssafy.ChallenMungs.Test.service;

import com.ssafy.ChallenMungs.Test.entity.Test;

import java.util.List;

public interface TestService {
    long getCount();
    List<Test> getCustom(String name);
    List <Test> getQueryDsl();


}
