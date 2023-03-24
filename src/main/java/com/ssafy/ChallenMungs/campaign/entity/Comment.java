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
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int commentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="campaign_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Campaign campaign;

    @Column(name = "writer")
    private String writer;
    @Column(name = "msg")
    private String msg;


}