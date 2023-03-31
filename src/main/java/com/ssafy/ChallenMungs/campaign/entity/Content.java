package com.ssafy.ChallenMungs.campaign.entity;


import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="campaign_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Campaign campaign;
    @Column(name = "body")
    String body;

    @Column(name = "type")
    String type;

    @Column(name = "seq")
    int seq;


}
