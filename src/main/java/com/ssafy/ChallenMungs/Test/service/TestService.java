package com.ssafy.ChallenMungs.Test.service;

import com.ssafy.ChallenMungs.Test.dto.TestDto;
import com.ssafy.ChallenMungs.Test.entity.Test;

import java.util.List;

public interface TestService {
    long getCount();
    List<TestDto> getCustom(String name);
    List <TestDto> getQueryDsl();


    List<TestDto> getJPQL(String name);
}
