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
import com.ssafy.ChallenMungs.campaign.entity.Receipt;
import com.ssafy.ChallenMungs.campaign.repository.CampaignListRepository;
import com.ssafy.ChallenMungs.campaign.repository.ReceiptRepository;
import com.ssafy.ChallenMungs.user.entity.User;
import com.ssafy.ChallenMungs.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.http.HttpService;
import org.web3j.utils.Convert;

import javax.transaction.Transactional;
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
    private final ReceiptRepository receiptRepo;

    @Override
    public void insertNomalWallet(String piggyBank, String wallet,String loginId) throws Exception{
        User user=userRepo.findUserByLoginId(loginId);
        if(user==null) throw new Exception("로그인아이디 확인");
        walletRepo.save(initWallet(user,'p',piggyBank));
        walletRepo.save(initWallet(user,'w',wallet));
    }

    @Override
    public void insertSpecialWallet(String campaign1, String campaign2,String loginId) throws Exception {
//        System.out.println(loginId);
        User user=userRepo.findUserByLoginId(loginId);
        if(user==null) throw new Exception("로그인아이디 확인");
        walletRepo.save(initWallet(user,'1',campaign1));
        walletRepo.save(initWallet(user,'2',campaign2));

    }


    @Override
    public void saveOrUpdateWallet(String loginId, String walletAddress) throws Exception {
        User user = userRepo.findUserByLoginId(loginId);
        if(user==null) throw new Exception("로그인아이디 확인");
        Wallet wallet = walletRepo.findByUserAndType(user, '3');

        if (wallet == null) {
            wallet = new Wallet();
            wallet.setUser(user);
            wallet.setType('3');
        }

        wallet.setAddress(walletAddress);
        walletRepo.save(wallet);
    }

    @Override
    public void sendKlay(String from, String to, int money) {
        BigInteger klayForm = BigInteger.valueOf(money).multiply(BigInteger.TEN.pow(18));
        String hexString = "0x" + klayForm.toString(16);

        RestTemplate restTemplate = new RestTemplate();

        // 요청 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("x-chain-id", "1001"); // 1001(Baobob 테스트넷)
        headers.set("Authorization", "Basic S0FTS1dDQUdINjkwRkFRV0lPVDE4QkhUOnNTYThjQlI1akhncXRwbnUtWWltMHV5dkVpb1V2REVQRGpMSmJjRkM="); //AccountPool 등록

        // 요청 바디 설정
        JSONObject requestBody = new JSONObject();
        requestBody.put("from", from);
        requestBody.put("value", hexString);
        requestBody.put("to", to);
        requestBody.put("submit", true);

        // 요청 엔티티 생성
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody.toString(), headers);

        // POST 요청 보내기
        String url = "https://wallet-api.klaytnapi.com/v2/tx/fd/value";
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        String responseBody = responseEntity.getBody();
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
            String balanceKlay = String.valueOf(Convert.fromWei(balanceWei.toString(), Convert.Unit.ETHER).toBigInteger());
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


    String normalChallenge = "0xcC1E6094bAfD1eb9EdA01a2a70E01f3bE7708C50";
    String specialChallenge = "0x023ddCfc3F563D3D797A5F3934cb67c7e1AdB6Ca";
    // 사용내역의 모든 주소들은 lowercase로 온다.
    String lowerN = normalChallenge.toLowerCase();
    String lowerS = specialChallenge.toLowerCase();

    @Override
    public List<Map<String, Object>> viewMyWallet(String loginId) throws JsonProcessingException {
        User user = userRepo.findUserByLoginId(loginId);
        String address = walletRepo.findByUserAndType(user, 'w').getAddress();
        int totalMoney = Integer.parseInt(getBalance(loginId, 'w'));
        int before_input = 0;

        JsonNode items = getHistory(address);

        List<Map<String, Object>> result = new ArrayList<>();
        Map<String, List<WalletItemDto>> dayMap = new LinkedHashMap<>();

        //사용 내역 바꾸기
        for (JsonNode item : items) {
            String from = item.get("from").asText();
            String to = item.get("to").asText();
            String title;

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

            if (from.equals(address)) {
                totalMoney += before_input;
                before_input = amount.intValue();

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
                totalMoney += before_input;
                before_input = amount.intValue() * (-1);
                if(from.equals(lowerN)){
                    title = "일반 챌린지 환불";
                } else if (from.equals(lowerS)) {
                    title = "특별 챌린지 환불";
                }
                title = "충전";
            }
//            System.out.println(calendar);
            // 값 넣기
            WalletItemDto tmp = new WalletItemDto(title, amount, String.valueOf(calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE)), totalMoney);
            String day = String.valueOf(calendar.get(Calendar.MONTH) + 1) + "." + String.valueOf(calendar.get(Calendar.DATE));
            List<WalletItemDto> dayList = dayMap.getOrDefault(day, new ArrayList<>());
            dayList.add(tmp);
            dayMap.put(day, dayList);
        }

        // 결과 맵으로 변환
        for (Map.Entry<String, List<WalletItemDto>> entry : dayMap.entrySet()) {
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("date", entry.getKey());
            resultMap.put("items", entry.getValue());
            result.add(resultMap);
        }
       return result;
    }

    @Override
    public List<Map<String, Object>> viewMyPiggyBank(String loginId) throws JsonProcessingException {
        User user = userRepo.findUserByLoginId(loginId);
        String address = walletRepo.findByUserAndType(user, 'p').getAddress();
        JsonNode items = getHistory(address);

        int totalMoney = Integer.parseInt(getBalance(loginId, 'p'));
        // item별 당시 계좌 잔액 표기를 위해
        int before_input = 0;

//        Map<String, List<WalletItemDto>> result = new HashMap<>();
        List<Map<String, Object>> result = new ArrayList<>();
        Map<String, List<WalletItemDto>> dayMap = new LinkedHashMap<>();


        //사용 내역 바꾸기
        for (JsonNode item : items) {
            String from = item.get("from").asText();
            String to = item.get("to").asText();
            String title;

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

            // 챌린지 보상
            if (to.equals(address)) {
                totalMoney += before_input;
                before_input = amount.intValue() * (-1);

                if (from.equals(lowerN)) {
                    title = "일반 챌린지 보상";
                } else if (from.equals(lowerS)) {
                    title = "특별 챌린지 보상";
                } else {
                    title = "error";
                }
            }
            // 기부
            else {
                totalMoney += before_input;
                before_input = amount.intValue();
                Wallet shelter;
                //아름이이 계좌 오류로 인한 코드 나중에 바꾸기 밑에껄로
                //shelter = walletRepo.findByAddress(to);

                if (to.equals("0xe85c069b5a941cf7739333c5c4dfaddacbf77c4d")){
                    shelter = walletRepo.findByAddress("0xba1663bc97c13bb6f9fff51144d14b7c542f25e6");
                }
                else{
                    shelter = walletRepo.findByAddress(to);
                }
                ////////////////////////////////
                title = shelter.getUser().getName() + "에 기부";
            }

            // 값 넣기
            WalletItemDto tmp = new WalletItemDto(title, amount, String.valueOf(calendar.get(Calendar.HOUR_OF_DAY)+":"+calendar.get(Calendar.MINUTE)), totalMoney);
            String day = String.valueOf(calendar.get(Calendar.MONTH) + 1) + "." + String.valueOf(calendar.get(Calendar.DATE));
            List<WalletItemDto> dayList = dayMap.getOrDefault(day, new ArrayList<>());
            dayList.add(tmp);
            dayMap.put(day, dayList);
        }

        for (Map.Entry<String, List<WalletItemDto>> entry : dayMap.entrySet()) {
            Map<String, Object> dayResult = new HashMap<>();
            dayResult.put("date", entry.getKey());
            dayResult.put("items", entry.getValue());
            result.add(dayResult);
        }

        return result;
    }


    public JsonNode getCampaignHistory(int campaignId, boolean fromOnly, boolean toOnly) throws JsonProcessingException {
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

        if(fromOnly){
            builder = builder.queryParam("from-only", true);
        }
        if(toOnly){
            builder = builder.queryParam("to-only", true);
        }
        // 요청 엔티티 생성
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);


        ResponseEntity<String> response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, requestEntity, String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(response.getBody());
        JsonNode itemsNode = rootNode.get("items");

        return itemsNode;
    }


    @Override
    public Map<String, List<CampaignItemDto>> viewCampaignWallet(int campaignId, boolean fromOnly, boolean toOnly) throws JsonProcessingException {
        JsonNode items = getCampaignHistory(campaignId, fromOnly, toOnly);
        Campaign campaign = campaignRepo.findCampaignByCampaignId(campaignId);
        String address = campaign.getWalletAddress();
        List<Receipt> receipt_list = receiptRepo.findAllByCampaignOrderByReceiptIdDesc(campaign);
        int i = 0;


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
                receipt = receipt_list.get(i).getReceipt();
                i++;
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

    @Override
    public String getWalletAddress(String loginId) throws JsonProcessingException {
        User user = userRepo.findUserByLoginId(loginId);
        return walletRepo.findByUserAndType(user, 'w').getAddress();
    }

}
