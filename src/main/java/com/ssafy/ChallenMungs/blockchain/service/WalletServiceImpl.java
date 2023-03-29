package com.ssafy.ChallenMungs.blockchain.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.ChallenMungs.blockchain.entity.Wallet;
import com.ssafy.ChallenMungs.blockchain.repository.WalletRepository;
import com.ssafy.ChallenMungs.user.entity.User;
import com.ssafy.ChallenMungs.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
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

    // Klaytn에서 사용자 지갑 사용내역 Law 받기
    @Override
    public JsonNode getHistory(String address) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        // 사용할 API 주소
        String url = "https://th-api.klaytnapi.com/v2/transfer/account/";
        // Header 설정
        HttpHeaders headers = new HttpHeaders();
        headers.set("x-chain-id", "1001"); // 1001(Baobob 테스트넷)
        headers.set("Authorization", "Basic S0FTS1dDQUdINjkwRkFRV0lPVDE4QkhUOnNTYThjQlI1akhncXRwbnUtWWltMHV5dkVpb1V2REVQRGpMSmJjRkM="); //AccountPool 등록
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

        // Klaytn에서 사용 내역 받기
        ResponseEntity<String> response = restTemplate.exchange(url+address, HttpMethod.GET, entity, String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(response.getBody());
        JsonNode itemsNode = rootNode.get("items");

        return itemsNode;
    }

}
