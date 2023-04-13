package com.ssafy.ChallenMungs.blockchain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DonationDetailDto {
    String userName;
    int money;
    String shelter;
    String donateDay;
}

