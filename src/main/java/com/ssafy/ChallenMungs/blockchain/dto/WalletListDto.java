package com.ssafy.ChallenMungs.blockchain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class WalletListDto {
    String day;
    WalletItemDto item;
    List<WalletItemDto> itemList;
}
