package com.ssafy.ChallenMungs.user.entity;

import lombok.*;

import javax.persistence.*;

@Entity(name="User")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;

    @Column(name = "login_id")
    private String loginId;

    @Column(name = "password")
    private String password;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private String type;

    @Column(name = "profile")
    private String profile;

    @Column(name = "totalDonate")
    private String totalDonate;

}
