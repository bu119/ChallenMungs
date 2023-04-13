package com.ssafy.ChallenMungs.campaign.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
//캠페인을 집어넣는데에 필요한 정보들을 담은 dto입니다.
public class CampaignInsertDto {
    String title;
    String loginId;
    String thumbnail;
    int targetAmount;

    List<ContentDto> contentList;
}
