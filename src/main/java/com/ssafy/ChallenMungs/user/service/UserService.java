package com.ssafy.ChallenMungs.user.service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.ChallenMungs.user.controller.UserController;
import com.ssafy.ChallenMungs.user.entity.User;
import com.ssafy.ChallenMungs.user.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final JPAQueryFactory queryFactory;
    private Logger log = LoggerFactory.getLogger(UserController.class);

    public UserService(UserRepository userRepository, EntityManager entityManager) {
        this.userRepository = userRepository;
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    public int countUserByEmail(String email) {
        return userRepository.countByLoginId(email);
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public User findUserByLoginId(String loginId) {
        return userRepository.findUserByLoginId(loginId);
    }

    public boolean delete(String loginId) {
        try {
            userRepository.deleteUserByLoginId(loginId);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void updateProfileAndName(String loginId, String name, String url) {
        User user = userRepository.findUserByLoginId(loginId);
        user.setName(name);
        user.setProfile(url);
        userRepository.save(user);
    }

    public boolean checkLoginIdDuplicate(String loginId) {
        return userRepository.existsByLoginId(loginId);
    }

    public void updatePassword(String loginId, String password) {
        log.info("아이디:" + loginId);
        User user = userRepository.findUserByLoginId(loginId);
        user.setPassword(password);
        userRepository.save(user);
    }
}

