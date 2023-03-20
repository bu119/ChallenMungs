package com.ssafy.ChallenMungs.Login;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import nonapi.io.github.classgraph.json.JSONUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/login")
@CrossOrigin("*")
@Api(value = "login", description = "테스트 컨트롤러에요!")
public class LoginController {
    private Logger log = LoggerFactory.getLogger(LoginController.class);

    @Value("${secret.key}")
    String secretKey;

    @PostMapping("/login")
    @ApiOperation(value = "로그인 하는 API에요")
    ResponseEntity<Map<String, Object>> getAccessTokenFromMobile(/*@RequestBody String access_Token*/) {
        String access_Token = "Vl3ExGYMkn2l9yhbJ2uwrRpF-s7Y-CSBq4lTw1DvCisNHgAAAYb9zdX2";
        Map<String, Object> res = new HashMap<>();
        HttpStatus httpStatus = null;
        String email;
        try {
            email = getInfo(access_Token);

            if (email.equals("no_email")) {
                res.put("code", "no_email");
                httpStatus = HttpStatus.OK;
            }
            System.out.println("여기까지");

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
