package com.ssafy.ChallenMungs.Test.service;

import com.ssafy.ChallenMungs.Test.dto.TestDto;
import com.ssafy.ChallenMungs.Test.entity.Test;
import com.ssafy.ChallenMungs.Test.repository.QuerydslRepository;
import com.ssafy.ChallenMungs.Test.repository.TestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class TestServiceImpl implements TestService{
    private final TestRepository jpaRepo;
    private final QuerydslRepository queryRepo;
    @Override
    public long getCount() {
        return jpaRepo.count();

    }
    @Override
    public List<TestDto> getCustom(String name) {
        List<Test> list = jpaRepo.findByNameContaining(name);
        return list.stream()
                .map(b -> new TestDto(b.getName()))
                .collect(Collectors.toList());

    }
    @Override
    public List<TestDto> getQueryDsl() {
        List<Test> list = queryRepo.getList();
        return list.stream()
                .map(b -> new TestDto(b.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public List<TestDto> getJPQL(String name) {
        List<Test> list =jpaRepo.testNative(name);
        return list.stream()
                .map(b -> new TestDto(b.getName()))
                .collect(Collectors.toList());
    }


}
