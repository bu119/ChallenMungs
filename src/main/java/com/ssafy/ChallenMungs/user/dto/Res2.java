package com.ssafy.ChallenMungs.user.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Res2 {
    @ApiModelProperty(value = "어떤값이 들어오는지 설명", example = "테스트 값", required = true)
    private String result;
}
