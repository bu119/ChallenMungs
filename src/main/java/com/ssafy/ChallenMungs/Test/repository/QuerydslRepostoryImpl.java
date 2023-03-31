package com.ssafy.ChallenMungs.Test.repository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.ChallenMungs.Test.entity.QTest;
import com.ssafy.ChallenMungs.Test.entity.Test;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
@RequiredArgsConstructor
@Repository
public class QuerydslRepostoryImpl  implements QuerydslRepository{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Test> getList() {
        QTest qtest = QTest.test;
        return jpaQueryFactory
                .selectFrom(qtest)
                .fetch();

    }
}
