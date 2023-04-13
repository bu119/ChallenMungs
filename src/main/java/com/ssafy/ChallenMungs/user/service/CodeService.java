package com.ssafy.ChallenMungs.user.service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.ChallenMungs.user.controller.UserController;
import com.ssafy.ChallenMungs.user.entity.Code;
import com.ssafy.ChallenMungs.user.entity.User;
import com.ssafy.ChallenMungs.user.repository.CodeRepository;
import com.ssafy.ChallenMungs.user.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;

@Service
public class CodeService {
    private Logger log = LoggerFactory.getLogger(UserController.class);
    private final CodeRepository codeRepository;
    private final JPAQueryFactory queryFactory;

    public CodeService(CodeRepository codeRepository, EntityManager entityManager) {
        this.codeRepository = codeRepository;
        this.queryFactory = new JPAQueryFactory(entityManager);
    }
    public void registerCode(String charityName, String code) {
        Code inviteCode = Code.builder().charityName(charityName).inviteCode(code).build();
        codeRepository.save(inviteCode);
    }

    public boolean confirmInviteCode(String charityName, String inviteCode) {
        log.info("해당 자선단체와 이름이 같은 애들의 코드를 모두 불러올게요!:" + charityName);
        List<Code> codes = codeRepository.findAllByCharityName(charityName);
        log.info("불러온 코드에서 입력한 코드와 같은 코드가 있는지 찾아볼게요!" + codes);
        for (Code c : codes) {
            if (c.getInviteCode().equals(inviteCode)) {
                codeRepository.delete(c);
                return true;
            }
        }
        return false;
    }
}
