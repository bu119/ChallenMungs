package com.ssafy.ChallenMungs.campaign.entity;



import lombok.*;

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

    @ManyToOne
    @JoinColumn(name="campaign_id")
    private Campaign campaign;

    @Column(name = "writer")
    private String writer;
    @Column(name = "msg")
    private String msg;


}
