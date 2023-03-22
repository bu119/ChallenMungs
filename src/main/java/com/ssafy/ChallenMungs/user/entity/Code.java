package com.ssafy.ChallenMungs.user.entity;

import lombok.*;

import javax.persistence.*;

@Entity(name="code")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Code {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "charity_name")
    private String charityName;

    @Column(name = "invite_code")
    private String invite_code;

}
