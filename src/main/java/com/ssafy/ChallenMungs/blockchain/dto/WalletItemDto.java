package com.ssafy.ChallenMungs.blockchain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class WalletItemDto {
    String title;
    String amount;
    String time;
    double balance;

}
