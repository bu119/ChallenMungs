package com.ssafy.ChallenMungs.Test.service;

import com.ssafy.ChallenMungs.Test.entity.Test;
import com.ssafy.ChallenMungs.Test.repository.TestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class TestServiceImpl implements TestService{
    private final TestRepository jpaRepo;
    @Override
    public long getCount() {
        return jpaRepo.count();
    }

    @Override
    public List<Test> getCustom(String name) {
        return jpaRepo.findByNameContaining(name);
    }
}
