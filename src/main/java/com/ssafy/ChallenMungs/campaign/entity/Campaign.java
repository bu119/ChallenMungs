package com.ssafy.ChallenMungs.campaign.entity;
import com.ssafy.ChallenMungs.user.entity.User;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Campaign {

    // 캠페인 아이디
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int campaignId;

    // 캠페인 담당자
    @ManyToOne(fetch = FetchType.LAZY)  // 1:N
    @JoinColumn(name="login_id") //Join 기준
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    // 후원처 이름
    @Column(name = "name")
    private String name;

    // 캠페인 지갑 주소- 캠페인 종료 후에는 "none"으로 변경한다.
    @Column(name = "wallet_address")
    private String walletAddress;

    // 캠페인 사진
    @Column(name = "thumbnail",length = 2500)
    private String thumbnail;

    // 캠페인 제목
    @Column(name = "title")
    private String title;

    // 캠페인 목표 금액
    @Column(name = "target_amount")
    private int targetAmount;

    // 캠페인 누적 금액
    @Column(name = "collect_amount")
    private int collectAmount;

    // 후원처 누적 사용액
    @Column(name = "withdraw_amount")
    private int withdrawAmount;

    // 생성일
    @Column(name = "regist_date")
    private LocalDate registDate;

    // 생성일 unixtime
    @Column(name = "regist_unix")
    private long registUnix;
    // 모금 종료 여부
    @Column(name = "is_end")
    private boolean isEnd;

    // 종료일
    @Column(name = "end_date")
    private LocalDate endDate;

    //종료시간 unixtime
    @Column(name = "end_unix")
    private long endUnix;





}
