package com.ssafy.ChallenMungs.blockchain.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

public interface WalletService {
    void insertNomalWallet(String piggyBank,String wallet,String loginId) throws Exception;
    void insertSpecialWallet(String campaign1, String campaign2,String loginId) throws Exception;

    String getBalance(String address);
    JsonNode getHistory(String address) throws JsonProcessingException;
}
