package com.ssafy.ChallenMungs.campaign.dto;

import lombok.*;

import javax.persistence.Column;

@Getter
@Setter
@AllArgsConstructor
//안드에서 캠페인 내용은 블럭으로 관리합니다. 블럭 하나를 나타내는 dto입니다.
public class ContentDto {
    String type;
    String body;

}
