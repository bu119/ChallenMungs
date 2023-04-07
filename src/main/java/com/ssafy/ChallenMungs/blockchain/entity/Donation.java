package com.ssafy.ChallenMungs.blockchain.entity;

import com.ssafy.ChallenMungs.user.entity.User;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Donation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int donationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="login_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @Column(name = "money")
    int money;
    @Column(name = "shelter")
    String shelter;
    @Column(name = "total_money")
    int totalMoney;

    @Column(name = "year")
    int year;

    @Column(name = "donate_date")
    private LocalDateTime donateDate;




}
