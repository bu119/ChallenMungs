package com.ssafy.ChallenMungs.user.controller;


import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.ssafy.ChallenMungs.user.entity.User;
import com.ssafy.ChallenMungs.user.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
@CrossOrigin("*")
@Api(value = "login", description = "테스트 컨트롤러에요!")
public class UserController {
    private Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserService userService;

    //토큰을 만들기 위한 비밀 키를 properties로 부터 가져와요
    @Value("${secret.key}")
    String secretKey;

    //사진을 저장할 경로를 properties로 부터 가져와요
    @Value("${profile.path}")
    String path;

    @PostMapping("/kakaoLogin")
    @ApiOperation(value = "로그인 하는 API에요!")
    // 프론트 단이 없는 지금은 예제로 access토큰을 받아왔고 프론트 단이 완성되면 아래 줄에 패러미터의 주석을 풀고 그아랫줄을 삭제하세요
    ResponseEntity<Map<String, Object>> kakaoLogin(/*@RequestBody String access_Token*/) {
        String access_Token = "rC2ftcJQarIgJ0IsOsPQvepRvj_y5WO7P7i6q5bnCinJXgAAAYcBEa0L";
        // response로 만들 map을 만들어요
        Map<String, Object> res = new HashMap<>();
        HttpStatus httpStatus = null;
        String email;
        try {
            email = getInfo(access_Token);
            log.info("이메일을 받아왔어요!");

            if (userService.countUserByEmail(email) > 0) {
                log.info("이미 데이터베이스에 아이디(login_id)가 있어요");
                res.put("code", "member");
                res.put("result", makeToken(email));
                httpStatus = HttpStatus.OK;
            } else {
                log.info("데이터 베이스에 아이디(login_id)가 없어요. 닉네임을 등록하세요");
                res.put("code", "no_email");
                res.put("result", email);
                httpStatus = HttpStatus.OK;
            }
        } catch (Exception e) {
            res.put("code", e.getMessage());
            httpStatus = HttpStatus.EXPECTATION_FAILED;
        }
        System.out.println(new ResponseEntity<>(res, httpStatus).toString());
        return new ResponseEntity<>(res, httpStatus);
    }

    String makeToken(String loginId) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + 86400000 * 7);
        return Jwts.builder()
                .setSubject(secretKey) // 열쇠? 키?
                .setIssuedAt(new Date()) // 발행일
                .setExpiration(expiryDate) // 만료일
                //만약 claim을 넣고 싶다면
                .claim("loginId", loginId) // 넣을 payload 키
                .signWith(SignatureAlgorithm.HS512, secretKey) // 암호화 방식
                .compact(); // 묶어
    }

    @PostMapping("/registerUser")
    @ApiOperation(value = "이메일, 닉네임로 유저를 등록하는 api에요!")
    ResponseEntity<Map<String, Object>> registerUser(@RequestParam("loginId") String loginId, @RequestParam("name") String name) {

        userService.saveUser(User.builder().loginId(loginId).name(name).build());

        Map res = new HashMap<>();
        res.put("code", "save_success");
        HttpStatus httpStatus = HttpStatus.OK;
        return new ResponseEntity<>(res, httpStatus);
    }

    @GetMapping("/getNameByToken")
    @ApiOperation(value = "프로필수정 페이지에 들어갈 때 닉네임을 불러오는 메서드에요!")
    ResponseEntity<Map<String, Object>> getNameByToken() {
        return null;
    }
    /*
    @PostMapping("/registerUser")
    @ApiOperation(value = "이메일, 닉네임, 프로필 이미지로 유저를 등록하는 api에요")
    ResponseEntity<Map<String, Object>> registerUser(@RequestParam("loginId") String loginId, @RequestParam("name") String name, @RequestParam("file") MultipartFile file) {
        if (file != null) {
            Path savePath = Paths.get(path + "\\" + name + ".png");
            byte[] bytes = new byte[0];
            try {
                bytes = file.getBytes();
                Files.write(savePath, bytes);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            userService.saveUser(User.builder().loginId(loginId).name(name).profile(path + "\\" + name + ".png").build());
        } else {
            userService.saveUser(User.builder().loginId(loginId).name(name).build());
        }
        Map res = new HashMap<>();
        res.put("code", "save_success");
        HttpStatus httpStatus = HttpStatus.OK;
        return new ResponseEntity<>(res, httpStatus);
    }
    */

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

