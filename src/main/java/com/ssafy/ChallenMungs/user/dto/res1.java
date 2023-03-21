package com.ssafy.ChallenMungs.user.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class res1 {
    @ApiModelProperty(value = "사용자의 이름", example = "steve", required = true)
    private String result;
}
