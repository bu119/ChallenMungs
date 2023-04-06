package com.ssafy.ChallenMungs.blockchain.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ssafy.ChallenMungs.blockchain.dto.CampaignItemDto;

import java.util.List;
import java.util.Map;

public interface WalletService {
    void insertNomalWallet(String piggyBank,String wallet,String loginId) throws Exception;
    void insertSpecialWallet(String campaign1, String campaign2,String loginId) throws Exception;
    // 후원처 출금주소 받기
//    void insertSpecialWithdrawalWallet(String walletAddress, String loginId) throws Exception;
    void saveOrUpdateWallet(String loginId, String walletAddress) throws Exception;
    void sendKlay(String from, String to, int money);

    String getBalance(String address, char type);
//    JsonNode getHistory(String address) throws JsonProcessingException;
    List<Map<String, Object>> viewMyWallet(String address) throws JsonProcessingException;
    List<Map<String, Object>> viewMyPiggyBank(String loginId) throws JsonProcessingException;
    Map<String, List<CampaignItemDto>> viewCampaignWallet(int campaignId, boolean fromOnly, boolean toOnly) throws JsonProcessingException;
    String getTotalDonate(String loginId) throws JsonProcessingException;
    String getWalletAddress(String loginId) throws JsonProcessingException;
}
