package com.ssafy.ChallenMungs.blockchain.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.ssafy.ChallenMungs.blockchain.dto.DonationItemDto;
import com.ssafy.ChallenMungs.blockchain.dto.WalletItemDto;
import com.ssafy.ChallenMungs.blockchain.entity.Wallet;

import java.util.List;
import java.util.Map;

public interface WalletService {
    void insertNomalWallet(String piggyBank,String wallet,String loginId) throws Exception;
    void insertSpecialWallet(String campaign1, String campaign2,String loginId) throws Exception;
    // 후원처 출금주소 받기
    void insertSpecialWithdrawalWallet(String walletAddress, String loginId) throws Exception;

    String getBalance(String address);
//    JsonNode getHistory(String address) throws JsonProcessingException;
    Map<String, List<WalletItemDto>> viewMyWallet(String address) throws JsonProcessingException;
    Map<String, List<WalletItemDto>> viewMyPiggyBank(String address) throws JsonProcessingException;
    String getTotalDonate(String address) throws JsonProcessingException;
}
