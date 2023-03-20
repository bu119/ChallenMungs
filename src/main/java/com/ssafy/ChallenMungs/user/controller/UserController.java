package com.ssafy.ChallenMungs.user.controller;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.ssafy.ChallenMungs.user.entity.User;
import com.ssafy.ChallenMungs.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/login")
@CrossOrigin("*")
@Api(value = "login", description = "테스트 컨트롤러에요!")
public class UserController {
    private Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserService userService;

    @Value("${secret.key}")
    String secretKey;

    @PostMapping("/login")
    @ApiOperation(value = "로그인 하는 API에요")
    ResponseEntity<Map<String, Object>> login(/*@RequestBody String access_Token*/) {
        String access_Token = "TtLDDKPnnJdQwA9NFa0MF_Er-uYrbr-kaMx3XU5GCj1zTQAAAYb-MvTq";
        Map<String, Object> res = new HashMap<>();
        HttpStatus httpStatus = null;
        String email;
        try {
            email = getInfo(access_Token);

            if (email.equals("no_email")) {
                res.put("code", "no_email");
                httpStatus = HttpStatus.OK;
            }

            if (userService.countUserByEmail(email) > 0) {
                System.out.println("아이디가 이미 존재해요");
            } else {
                System.out.print("사용할 닉네임 입력:");
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                String tempName = br.readLine();
                userService.saveUser(User.builder().loginId(email).name(tempName).build());
            }
//            if (userService.countUserByEmail(email) == 0) {
//                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//
//                String tempName = br.readLine();
//
//                userService.saveUser(User.builder().loginId(email).name(tempName).build());
//                System.out.println("왓음");
//            }

            //이메일을 데이터베이스에서 뒤져요
//            UserEntity user = userService.findUserByEmail(email);
            //이메일이 있으면 이미 가입한 유저에요 JWT토큰을 만들어 홈으로 userDto와 함께 가요
            //이메일이 없으면 가입한 적이 없는 유저에요
//            if (user == null) {
//                res.put("code", "no_email");
//                res.put("result", email);
//                httpStatus = HttpStatus.OK;
//            } else {
//                res.put("code", "member");
//                res.put("result", makeToken(user.getId()));
//                httpStatus = HttpStatus.OK;
//            }
        } catch (Exception e) {
            res.put("code", e.getMessage());
            httpStatus = HttpStatus.EXPECTATION_FAILED;
        }
        return null;
    }

    //이메일을 받아 오는 메서드에요
    String getInfo(String token) throws IOException {
        String reqURL = "https://kapi.kakao.com/v2/user/me";
        URL url;
        try {
            url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Authorization", "Bearer " + token);
            int responseCode = conn.getResponseCode();
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";
            while ((line = br.readLine()) != null) {
                result += line;
            }
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);
            boolean hasEmail = element.getAsJsonObject().get("kakao_account").getAsJsonObject().get("has_email").getAsBoolean();
            String email = "";
            if(hasEmail){
                email = element.getAsJsonObject().get("kakao_account").getAsJsonObject().get("email").getAsString();
            }
            return email;
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "no_email";
        }
    }
}

