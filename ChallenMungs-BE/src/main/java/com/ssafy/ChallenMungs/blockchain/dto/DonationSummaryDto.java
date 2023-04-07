package com.ssafy.ChallenMungs.blockchain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DonationSummaryDto {
    int donateCount;
    int sumYearMoney;
    int sumTotalMoney;

}
