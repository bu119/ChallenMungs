package com.ssafy.ChallenMungs.blockchain.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.ChallenMungs.blockchain.controller.WalletController;
import com.ssafy.ChallenMungs.blockchain.dto.CampaignItemDto;
import com.ssafy.ChallenMungs.blockchain.dto.WalletItemDto;
import com.ssafy.ChallenMungs.blockchain.entity.Wallet;
import com.ssafy.ChallenMungs.blockchain.repository.WalletRepository;
import com.ssafy.ChallenMungs.campaign.entity.Campaign;
import com.ssafy.ChallenMungs.campaign.repository.CampaignContentRepository;
import com.ssafy.ChallenMungs.campaign.repository.CampaignListRepository;
import com.ssafy.ChallenMungs.user.entity.User;
import com.ssafy.ChallenMungs.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.http.HttpService;
import org.web3j.utils.Convert;
import software.amazon.ion.Decimal;

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
    private final CampaignListRepository campaignRepo;

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

    // 후원처 출금계좌 생성
    @Override
    public void insertSpecialWithdrawalWallet(String walletAddress, String loginId) throws Exception {
        User user = userRepo.findUserByLoginId(loginId);
        if(user==null) throw new Exception("로그인아이디 확인");
        walletRepo.save(initWallet(user,'3',walletAddress));
    }



    
    public Wallet initWallet(User user,char type,String address){
        Wallet wallet=new Wallet();
        wallet.setUser(user);
        wallet.setType(type);
        wallet.setAddress(address);
        return wallet;
    }

    @Override
    public String getBalance(String loginId, char type) {
        User user = userRepo.findUserByLoginId(loginId);
        String address = walletRepo.findByUserAndType(user, type).getAddress();
        String nodeUrl = "https://api.baobab.klaytn.net:8651";
        // Web3j 인스턴스 생성
        Web3j web3j = Web3j.build(new HttpService(nodeUrl));
        // 최신 블록 번호
        DefaultBlockParameterName blockParameter = DefaultBlockParameterName.LATEST;
        String hexString = Integer.toHexString(150).toUpperCase();
//        log.info(hexString);
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
    String normalChallenge = "0x2649eadC4C15bac554940A0A702fa759bddf0dBe";
    String specialChallenge = "0xee43BB5476e52B04175d698C56cC4516b96A85A5";

    // 사용내역의 모든 주소들은 lowercase로 온다.
    String lowerN = normalChallenge.toLowerCase();
    String lowerS = specialChallenge.toLowerCase();
    // for문 돌면서 item 만들기
    @Override
    public Map<String, List<WalletItemDto>> viewMyWallet(String loginId) throws JsonProcessingException {
        User user = userRepo.findUserByLoginId(loginId);
        String address = walletRepo.findByUserAndType(user, 'w').getAddress();

        JsonNode items = getHistory(address);

        Map<String, List<WalletItemDto>> result = new HashMap<>();

        //사용 내역 바꾸기
        for (JsonNode item : items) {
            String from = item.get("from").asText();
            String to = item.get("to").asText();
            String title;
            if (from.equals(address)) {
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
//            log.info(hexvalue);
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
    public Map<String, List<WalletItemDto>> viewMyPiggyBank(String loginId) throws JsonProcessingException {
        User user = userRepo.findUserByLoginId(loginId);
        String address = walletRepo.findByUserAndType(user, 'p').getAddress();
        JsonNode items = getHistory(address);

        Map<String, List<WalletItemDto>> result = new HashMap<>();

        //사용 내역 바꾸기
        for (JsonNode item : items) {
            String from = item.get("from").asText();
            String to = item.get("to").asText();
            String title;
            if (to.equals(address)) {
                if (from.equals(lowerN)) {
                    title = "일반 챌린지 보상";
                } else if (from.equals(lowerS)) {
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
//            log.info(hexvalue);
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


    public JsonNode getCampaignHistory(int campaignId) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();

        // Header 설정
        HttpHeaders headers = new HttpHeaders();
        headers.set("x-chain-id", "1001"); // 1001(Baobob 테스트넷)
        headers.set("Authorization", "Basic S0FTS1dDQUdINjkwRkFRV0lPVDE4QkhUOnNTYThjQlI1akhncXRwbnUtWWltMHV5dkVpb1V2REVQRGpMSmJjRkM="); //AccountPool 등록


        // 요청 바디 설정
        JSONObject requestBody = new JSONObject();

        Campaign campaign = campaignRepo.findCampaignByCampaignId(campaignId);
        String registUnix = String.valueOf(campaign.getRegistUnix());
        String endUnix = String.valueOf(campaign.getEndUnix());
        String address = campaign.getWalletAddress();
        String url = "https://th-api.klaytnapi.com/v2/transfer/account/"+ address;
        UriComponentsBuilder builder;


        if(endUnix.equals("0")){
            builder = UriComponentsBuilder.fromHttpUrl(url)
                    .queryParam("range", registUnix);
            System.out.println("진행중");
        }
        else{
            builder = UriComponentsBuilder.fromHttpUrl(url)
                    .queryParam("range", registUnix + "," + endUnix);
            System.out.println("종료");
        }
        // 요청 엔티티 생성
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);


        ResponseEntity<String> response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, requestEntity, String.class);
        System.out.println(requestBody);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(response.getBody());
        JsonNode itemsNode = rootNode.get("items");

        return itemsNode;
    }


    @Override
    public Map<String, List<CampaignItemDto>> viewCampaignWallet(int campaignId) throws JsonProcessingException {
        JsonNode items = getCampaignHistory(campaignId);
        String address = campaignRepo.findCampaignByCampaignId(campaignId).getWalletAddress();

        Map<String, List<CampaignItemDto>> result = new HashMap<>();

        //사용 내역 바꾸기
        for (JsonNode item : items) {
            String from = item.get("from").asText();
            String to = item.get("to").asText();
            String title;
            String receipt = null;

            // 모금
            if (to.equals(address)) {
                title = walletRepo.findByAddress(from).getUser().getName() + "님의 후원";
            }
            // 출금
            else {
                title = "출금";
                receipt = "url";
            }

            //전송 시간
            long timstamp = item.get("timestamp").asLong();
            Date date = new Date(timstamp * 1000L);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);

            // 전송 Klaytn(hex)
            String hexvalue = item.get("value").asText();
//            log.info(hexvalue);
            // 0x slicing
            hexvalue = hexvalue.substring(2);
            // Decimal로 변환
            BigInteger decimal = new BigInteger(hexvalue, 16);
            BigInteger divisor = new BigInteger("1000000000000000000");
            // 최종값으로 변환
            BigDecimal amount = new BigDecimal(decimal).divide(new BigDecimal(divisor));

            // 값 넣기
            CampaignItemDto tmp = new CampaignItemDto(title, amount, String.valueOf(calendar.get(Calendar.HOUR_OF_DAY)+":"+calendar.get(Calendar.MINUTE)), receipt);
            String day = String.valueOf(calendar.get(Calendar.MONTH) + 1) + "." + String.valueOf(calendar.get(Calendar.DATE));
            List<CampaignItemDto> dayList = result.getOrDefault(day, new ArrayList<CampaignItemDto>());
            dayList.add(tmp);
            result.put(day, dayList);
        }

        return result;
    }

    @Override
    public String getTotalDonate(String loginId) throws JsonProcessingException {
        User user = userRepo.findUserByLoginId(loginId);
        String address = walletRepo.findByUserAndType(user, 'p').getAddress();
        JsonNode items = getHistory(address);
        String lowerA = address.toLowerCase();
        BigDecimal totalAmount = new BigDecimal("0");

        for (JsonNode item : items) {
            String from = item.get("from").asText();
            if (from.equals(lowerA)) {
                String hexvalue = item.get("value").asText();
                // 0x slicing
                hexvalue = hexvalue.substring(2);
                // Decimal로 변환
                BigInteger decimal = new BigInteger(hexvalue, 16);
                BigInteger divisor = new BigInteger("1000000000000000000");
                // 최종값으로 변환
                BigDecimal amount = new BigDecimal(decimal).divide(new BigDecimal(divisor));
                totalAmount = totalAmount.add(amount);
            }

        }
        String result = totalAmount.toString();

        return result;
    }

}
