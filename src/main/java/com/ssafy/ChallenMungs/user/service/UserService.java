package com.ssafy.ChallenMungs.user.service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.ChallenMungs.user.entity.User;
import com.ssafy.ChallenMungs.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final JPAQueryFactory queryFactory;

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
}

