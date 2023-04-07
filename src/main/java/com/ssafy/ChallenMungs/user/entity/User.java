package com.ssafy.ChallenMungs.user.entity;

import lombok.*;

import javax.persistence.*;

@Entity(name="user")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @Column(name = "login_id")
    private String loginId;

    //    @Column(name = "user_id")
    //    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //    private Long userId;

    @Column(name = "password")
    private String password;

    @Column(name = "name")
    private String name;

    //일반회원 (n)ormal의 n, 후원처회원 (s)peceil의 s
    @Column(name = "type")
    private char type;

    @Column(name = "profile", length = 2500)
    private String profile;

    @Column(name = "total_donate")
    private Integer totalDonate;
}
