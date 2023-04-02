package com.ssafy.ChallenMungs.blockchain.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.ssafy.ChallenMungs.blockchain.dto.CampaignItemDto;
import com.ssafy.ChallenMungs.blockchain.dto.DonationItemDto;
import com.ssafy.ChallenMungs.blockchain.dto.WalletItemDto;
import com.ssafy.ChallenMungs.blockchain.entity.Wallet;

import java.util.List;
import java.util.Map;

public interface WalletService {
    void insertNomalWallet(String piggyBank,String wallet,String loginId) throws Exception;
    void insertSpecialWallet(String campaign1, String campaign2,String loginId) throws Exception;
    // 후원처 출금주소 받기
//    void insertSpecialWithdrawalWallet(String walletAddress, String loginId) throws Exception;
    void saveOrUpdateWallet(String loginId, String walletAddress) throws Exception;

    String getBalance(String address, char type);
//    JsonNode getHistory(String address) throws JsonProcessingException;
    Map<String, List<WalletItemDto>> viewMyWallet(String address) throws JsonProcessingException;
    Map<String, List<WalletItemDto>> viewMyPiggyBank(String loginId) throws JsonProcessingException;
    Map<String, List<CampaignItemDto>> viewCampaignWallet(int campaignId, boolean fromOnly) throws JsonProcessingException;
    String getTotalDonate(String loginId) throws JsonProcessingException;
}
