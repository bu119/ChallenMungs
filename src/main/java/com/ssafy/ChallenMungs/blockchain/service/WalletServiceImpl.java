package com.ssafy.ChallenMungs.blockchain.service;

import com.ssafy.ChallenMungs.blockchain.entity.Wallet;
import com.ssafy.ChallenMungs.blockchain.repository.WalletRepository;
import com.ssafy.ChallenMungs.user.entity.User;
import com.ssafy.ChallenMungs.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.http.HttpService;
import org.web3j.utils.Convert;

import javax.transaction.Transactional;
import java.io.IOException;
import java.math.BigInteger;

@Service
@Transactional
@RequiredArgsConstructor
public class WalletServiceImpl implements  WalletService{
    private final WalletRepository walletRepo;
    private final UserRepository userRepo;

    @Override
    public void insertNomalWallet(String piggyBank, String wallet,String loginId) throws Exception{
        User user=userRepo.findUserByLoginId(loginId);
        if(user==null) throw new Exception("로그인아이디 확인");
        walletRepo.save(initWallet(user,'p',piggyBank));
        walletRepo.save(initWallet(user,'w',wallet));
    }

    @Override
    public void insertSpecialWallet(String campaign1, String campaign2,String loginId) throws Exception {
        User user=userRepo.findUserByLoginId(loginId);
        if(user==null) throw new Exception("로그인아이디 확인");
        walletRepo.save(initWallet(user,'1',campaign1));
        walletRepo.save(initWallet(user,'2',campaign2));

    }

    
    public Wallet initWallet(User user,char type,String address){
        Wallet wallet=new Wallet();
        wallet.setUser(user);
        wallet.setType(type);
        wallet.setAddress(address);
        return wallet;
    }

    @Override
    public String getBalance(String address) {
        String nodeUrl = "https://api.baobab.klaytn.net:8651";
        // Web3j 인스턴스 생성
        Web3j web3j = Web3j.build(new HttpService(nodeUrl));
        // 최신 블록 번호
        DefaultBlockParameterName blockParameter = DefaultBlockParameterName.LATEST;

        try {
            // 계좌 잔액 가져오기
            EthGetBalance balanceResponse = web3j.ethGetBalance(address, blockParameter).send();
            BigInteger balanceWei = balanceResponse.getBalance();

            // 단위 변환: KLAY -> KLAY
            String balanceKlay = Convert.fromWei(balanceWei.toString(), Convert.Unit.ETHER).toPlainString();
            return balanceKlay;

        } catch (Exception e) {
            return "error";
        }

    }
}
