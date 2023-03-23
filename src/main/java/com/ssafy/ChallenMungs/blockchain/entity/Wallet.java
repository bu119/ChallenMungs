package com.ssafy.ChallenMungs.blockchain.entity;

import com.ssafy.ChallenMungs.user.entity.User;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int walletId;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name="login_id")
    private User user;

    @Column(name="address")
    private String address;
    @Column(name="type")
    private char type;

}
