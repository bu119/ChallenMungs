package com.ssafy.ChallenMungs.challenge.general.dto;

        import io.swagger.annotations.ApiModel;
        import io.swagger.annotations.ApiModelProperty;
        import lombok.*;

        import java.util.List;

@Getter
@Setter
@AllArgsConstructor
//일반챌린지를 만드는데 필요한 정보들을 담은 dto 입니다.
public class GeneralCreateDto {
    String title;
    String startDate;
    String endDate;
    int maxParticipantCount;
    int currentParticipantCount;
    int entryFee;
    List<ContentDto> contentList;
}
