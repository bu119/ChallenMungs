package com.ssafy.ChallenMungs.blockchain.service;

public interface WalletService {
    void insertNomalWallet(String piggyBank,String wallet,String loginId);
    void insertSpecialWallet(String campaign1, String campaign2,String loginId);
    void deleteTest(String loginId);
}
