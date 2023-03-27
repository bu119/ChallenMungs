package com.ssafy.ChallenMungs.blockchain.service;

import com.ssafy.ChallenMungs.blockchain.entity.Wallet;
import com.ssafy.ChallenMungs.blockchain.repository.WalletRepository;
import com.ssafy.ChallenMungs.user.entity.User;
import com.ssafy.ChallenMungs.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

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
    public void deleteTest(String loginId){
        User user=userRepo.findUserByLoginId(loginId);
        userRepo.delete(user);
    }
}
