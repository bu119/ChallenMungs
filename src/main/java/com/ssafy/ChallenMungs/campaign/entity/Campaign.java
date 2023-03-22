package com.ssafy.ChallenMungs.campaign.entity;
import com.ssafy.ChallenMungs.user.entity.User;
import lombok.*;
import javax.persistence.*;
import java.util.Date;

@Entity(name="Campaign")
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
    @ManyToOne  // 1:N
    @JoinColumn(name="user_id") //Join 기준
    private User user;

    // 후원처 이름
    @Column(name = "name")
    private String name;

    // 캠페인 지갑 주소
    @Column(name = "wallet_address")
    private String walletAddress;

    // 캠페인 사진
    @Column(name = "thumbnail")
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
    private Date registDate;

    // 모금 종료 여부
    @Column(name = "is_end")
    private boolean isEnd;


}