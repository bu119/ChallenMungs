package com.ssafy.ChallenMungs.campaign.entity;


import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Content {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int contentId;

    @Column(name = "body")
    String body;

    @Column(name = "type")
    String type;

    @Column(name = "seq")
    int seq;



}
