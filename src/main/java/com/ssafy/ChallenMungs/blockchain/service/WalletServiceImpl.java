package com.ssafy.ChallenMungs.blockchain.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.ChallenMungs.blockchain.controller.WalletController;
import com.ssafy.ChallenMungs.blockchain.dto.WalletItemDto;
import com.ssafy.ChallenMungs.blockchain.entity.Wallet;
import com.ssafy.ChallenMungs.blockchain.repository.WalletRepository;
import com.ssafy.ChallenMungs.user.entity.User;
import com.ssafy.ChallenMungs.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

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
    private Logger log = LoggerFactory.getLogger(WalletController.class);
    String normalChallenge = "0x50Aa5B30442cd67659bF1CA81E7cD4e351898cfd";
    String specialChallenge = "0x6aC40a06633BcF319F0ebd124F189D29d9A390bF";

    // 사용내역의 모든 주소들은 lowercase로 온다.
    String lowerN = normalChallenge.toLowerCase();
    String lowerS = specialChallenge.toLowerCase();
    // for문 돌면서 item 만들기
    @Override
    public Map<String, List<WalletItemDto>> viewMyWallet(String address) throws JsonProcessingException {
        JsonNode items = getHistory(address);

        Map<String, List<WalletItemDto>> result = new HashMap<>();
        String lowerA = address.toLowerCase();

        //사용 내역 바꾸기
        for (JsonNode item : items) {
            String from = item.get("from").asText();
            String to = item.get("to").asText();
            String title;
            if (from.equals(lowerA)) {
                log.info(to);
                if (to.equals(lowerN)) {
                    title = "일반 챌린지 참여";
                } else if (to.equals(lowerS)) {
                    title = "특별 챌린지 참여";
                } else {
                    title = "잘못된 계좌";
                }
            }
            // 충전
            else {
                title = "충전";
            }

            //전송 시간
            long timstamp = item.get("timestamp").asLong();
            Date date = new Date(timstamp * 1000L);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);

            // 전송 Klaytn(hex)
            String hexvalue = item.get("value").asText();
            // 0x slicing
            hexvalue = hexvalue.substring(2);
            // Decimal로 변환
            BigInteger decimal = new BigInteger(hexvalue, 16);
            BigInteger divisor = new BigInteger("1000000000000000000");
            // 최종값으로 변환
            BigDecimal amount = new BigDecimal(decimal).divide(new BigDecimal(divisor));

            // 값 넣기
            WalletItemDto tmp = new WalletItemDto(title, amount, String.valueOf(calendar.get(Calendar.HOUR_OF_DAY)+":"+calendar.get(Calendar.MINUTE)));
            String day = String.valueOf(calendar.get(Calendar.MONTH) + 1) + "." + String.valueOf(calendar.get(Calendar.DATE));
            List<WalletItemDto> dayList = result.getOrDefault(day, new ArrayList<WalletItemDto>());
            dayList.add(tmp);
            result.put(day, dayList);
        }
        return result;
    }

    @Override
    public Map<String, List<WalletItemDto>> viewMyPiggyBank(String address) throws JsonProcessingException {
        JsonNode items = getHistory(address);

        Map<String, List<WalletItemDto>> result = new HashMap<>();
        String lowerA = address.toLowerCase();

        //사용 내역 바꾸기
        for (JsonNode item : items) {
            String from = item.get("from").asText();
            String to = item.get("to").asText();
            String title;
            if (to.equals(lowerA)) {
                log.info(to);
                if (from.equals(lowerN)) {
                    title = "일반 챌린지 보상";
                } else if (to.equals(lowerS)) {
                    title = "특별 챌린지 보상";
                } else {
                    title = "error";
                }
            }
            // 충전
            else {
                Wallet shelter = walletRepo.findByAddress(to);
                title = shelter.getUser().getName() + "에 기부";
            }

            //전송 시간
            long timstamp = item.get("timestamp").asLong();
            Date date = new Date(timstamp * 1000L);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);

            // 전송 Klaytn(hex)
            String hexvalue = item.get("value").asText();
            // 0x slicing
            hexvalue = hexvalue.substring(2);
            // Decimal로 변환
            BigInteger decimal = new BigInteger(hexvalue, 16);
            BigInteger divisor = new BigInteger("1000000000000000000");
            // 최종값으로 변환
            BigDecimal amount = new BigDecimal(decimal).divide(new BigDecimal(divisor));

            // 값 넣기
            WalletItemDto tmp = new WalletItemDto(title, amount, String.valueOf(calendar.get(Calendar.HOUR_OF_DAY)+":"+calendar.get(Calendar.MINUTE)));
            String day = String.valueOf(calendar.get(Calendar.MONTH) + 1) + "." + String.valueOf(calendar.get(Calendar.DATE));
            List<WalletItemDto> dayList = result.getOrDefault(day, new ArrayList<WalletItemDto>());
            dayList.add(tmp);
            result.put(day, dayList);
        }
        return result;


//        return null;
    }

}
