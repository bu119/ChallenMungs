package com.ssafy.ChallenMungs.user.service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.ChallenMungs.user.entity.Code;
import com.ssafy.ChallenMungs.user.entity.User;
import com.ssafy.ChallenMungs.user.repository.CodeRepository;
import com.ssafy.ChallenMungs.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;

@Service
public class CodeService {
    private final CodeRepository codeRepository;
    private final JPAQueryFactory queryFactory;

    public CodeService(CodeRepository codeRepository, EntityManager entityManager) {
        this.codeRepository = codeRepository;
        this.queryFactory = new JPAQueryFactory(entityManager);
    }
    public void registerCode(String charityName, String code) {
        Code inviteCode = Code.builder().charityName(charityName).invite_code(code).build();
        codeRepository.save(inviteCode);
    }
}
