package com.ssafy.ChallenMungs.User;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.List;

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
