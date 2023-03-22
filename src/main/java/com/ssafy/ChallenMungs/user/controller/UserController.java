package com.ssafy.ChallenMungs.user.controller;


import com.amazonaws.Response;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.ssafy.ChallenMungs.image.service.FileServiceImpl;
import com.ssafy.ChallenMungs.user.dto.Res1;
import com.ssafy.ChallenMungs.user.dto.Res2;
import com.ssafy.ChallenMungs.user.entity.User;
import com.ssafy.ChallenMungs.user.service.EmailService;
import com.ssafy.ChallenMungs.user.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
@CrossOrigin("*")
@Api(value = "login", description = "유저와 관련된 컨트롤러에요!")
public class UserController {
    private Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserService userService;

    @Autowired
    FileServiceImpl fileService;

    @Autowired
    EmailService emailService;

    //토큰을 만들기 위한 비밀 키를 properties로 부터 가져와요
    @Value("${secret.key}")
    String secretKey;

    //사진을 저장할 경로를 properties로 부터 가져와요
    @Value("${profile.path}")
    String path;

    @PostMapping("/kakaoLogin")
    @ApiOperation(value = "로그인 하는 API에요!")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 417, message = "실패ㅜㅜ")
    })
    // 프론트 단이 없는 지금은 예제로 access토큰을 받아왔고 프론트 단이 완성되면 아래 줄에 패러미터의 주석을 풀고 그아랫줄을 삭제하세요
    ResponseEntity<Map<String, Object>> kakaoLogin(@RequestBody String accessToken) {
        // response로 만들 map을 만들어요
        Map<String, Object> res = new HashMap<>();
        HttpStatus httpStatus = null;
        String email;
        try {
            email = getInfo(accessToken);
            log.info("이메일을 받아왔어요!");

            if (userService.countUserByEmail(email) > 0) {
                log.info("이미 데이터베이스에 아이디(login_id)가 있어요");
                res.put("code", "member");
                String token = makeToken(email);
                System.out.println("token:" + token);
                res.put("result", token);
                httpStatus = HttpStatus.OK;
            } else {
                log.info("데이터 베이스에 아이디(login_id)가 없어요. 닉네임을 등록하세요");
                res.put("code", "no_email");
                res.put("result", email);
                httpStatus = HttpStatus.OK;
            }
        } catch (Exception e) {
            res.put("code", e.getMessage());
            httpStatus = HttpStatus.EXPECTATION_FAILED; //417 이에요
        }
        System.out.println(new ResponseEntity<>(res, httpStatus).toString());
        return new ResponseEntity<>(res, httpStatus);
    }
    String makeToken(String loginId) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + 864000000);
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
    ResponseEntity<Map<String, Object>> registerUser(@RequestParam("name") String name, @RequestParam("accessKey") String accessKey) {
//        userService.saveUser(User.builder().loginId(loginId).name(name).build());
        Map<String, String> v = getProfileFromKakao(accessKey);
        userService.saveUser(User.builder().loginId(v.get("loginId")).name(name).profile(v.get("profile")).type('n').build());
        Map res = new HashMap<>();
        res.put("code", "save_success");
        HttpStatus httpStatus = HttpStatus.OK;
        return new ResponseEntity<>(res, httpStatus);
    }
    private HashMap<String, String> getProfileFromKakao(String token) {
        String reqURL = "https://kapi.kakao.com/v2/user/me";
        URL url;
        HashMap<String, String> v = new HashMap<>();
        v.put("loginId", null);
        v.put("profile", null);
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
            System.out.println("여기까지:::" + element.getAsJsonObject().get("kakao_account").getAsJsonObject());
            v.put("loginId", element.getAsJsonObject().get("kakao_account").getAsJsonObject().get("email").getAsString());
            if (element.getAsJsonObject().get("kakao_account").getAsJsonObject().get("profile").getAsJsonObject().get("is_default_image").toString().equals("false")) {
                System.out.println("개인프로필 이미지가 있어요 : " + element.getAsJsonObject().get("kakao_account").getAsJsonObject().get("profile").getAsJsonObject().get("profile_image_url").toString());
                v.put("profile", element.getAsJsonObject().get("kakao_account").getAsJsonObject().get("profile").getAsJsonObject().get("profile_image_url").toString());
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return v;
    }
    @GetMapping("/tokenConfirm/getNameAndProfile")
    @ApiOperation(value = "프로필수정 페이지에 들어갈 때 닉네임과 프로필이미지을 불러오는 메서드에요!")
    ResponseEntity<Map<String, Object>> getNameAndProfile(HttpServletRequest request) {
        Map res = new HashMap<>();
        res.put("code", "get_name_success");
        User user = userService.findUserByLoginId(request.getAttribute("loginId").toString());
        res.put("name", user.getName());
        res.put("profile", user.getProfile());
        HttpStatus httpStatus = HttpStatus.OK;
        return new ResponseEntity<>(res, httpStatus);
    }

    @DeleteMapping("/tokenConfirm/deleteUser")
    @ApiOperation(value = "회원탈퇴", notes = "loginId를 통해 사용자 정보를 삭제한다.")
    ResponseEntity<Map<String, Object>> deleteUser(HttpServletRequest request){
        String loginId = request.getAttribute("loginId").toString();
        log.info("토큰을 통해 가져온 로그인 아이디:" + loginId);
        boolean isDeleted = userService.delete(loginId);
        log.info("회원탈퇴 =" + isDeleted);
        Map res = new HashMap<>();

        if (isDeleted) {
            res.put("code", "delete_success");
            HttpStatus httpStatus = HttpStatus.OK;
            return new ResponseEntity<>(res, httpStatus);
        } else {
            res.put("code", "delete_failed");
            res.put("message", "회원탈퇴에 실패하였습니다. 로그인 ID를 확인해주세요.");
            HttpStatus httpStatus = HttpStatus.EXPECTATION_FAILED;
            return new ResponseEntity<>(res, httpStatus);
        }
    }

    @PostMapping("/tokenConfirm/updateProfileAndName")
    @ApiOperation(value = "유저의 프로필과 닉네임 정보를 변경해요!")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "변경 성공"),
            @ApiResponse(code = 417, message = "변경 실패ㅜㅜ")
    })
    ResponseEntity updateProfileAndName(HttpServletRequest request, @RequestParam("name") String name, @RequestParam("profile") MultipartFile file) {
        String loginId = request.getAttribute("loginId").toString();
        String url = null;
        try {
            if (file != null) url = fileService.saveFile(file, "user");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
        }
        userService.updateProfileAndName(loginId, name, url);
        return ResponseEntity.status(HttpStatus.OK).build();
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
            System.out.println(element.getAsJsonObject().get("kakao_account").getAsJsonObject());
            return email;
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "no_email";
        }
    }

    // 성공하면 토큰 실패하면 실패 메세지
    @PostMapping("/charityRegister")
    @ApiOperation(value = "자선단체의 회원가입 메서드에요!")
    ResponseEntity<Map<String, Object>> charityLogin(@RequestParam("loginId") String loginId, @RequestParam("password") String password) {
        User user = User.builder().loginId(loginId).password(password).type('s').build();
        return null;
    }
    @PostMapping("/codeEmail")
    void codeEmail(@RequestParam("to") String email, @RequestParam("charityName") String charityName) {
        emailService.sendHtmlEmail("opi6@hanmail.net", charityName);
    }

    @PostMapping("/statusTest")
    ResponseEntity<Map<String, Object>> status(@RequestParam("state") String state) {
        HashMap<String, Object> v = new HashMap<>();
        System.out.println(state);
        if (state.equals("OK")) {
            v.put("code", "good");
            HttpStatus httpStatus = HttpStatus.OK;
            return new ResponseEntity<>(v, httpStatus);
        } else {
            v.put("code", "No");
            return new ResponseEntity<>(v, HttpStatus.EXPECTATION_FAILED);
        }
    }
    @GetMapping("/charity/checkId")
    @ApiOperation(value = "기부처 아이디 중복체크")
//    ResponseEntity<Boolean> checkLoginIdDuplicate(@RequestParam("loginId") String loginId) {
//        log.info("회원가입하려는 아이디:" + loginId);
//        return ResponseEntity.ok(userService.checkLoginIdDuplicate(loginId));
////        아이디가 있으면 true 없으면 false 반환
//    }
    ResponseEntity<Map<String, Object>> checkLoginIdDuplicate(@RequestParam("loginId") String loginId) {
        log.info("회원가입하려는 아이디:" + loginId);
        boolean isExist = userService.checkLoginIdDuplicate(loginId);
        log.info("중복 =" + isExist);
        Map res = new HashMap<>();

        if (isExist) {
            res.put("result", 0);
            res.put("code", "impossible_id");
        } else {
            res.put("result", 1);
            res.put("code", "possible_id");
        }

        HttpStatus httpStatus = HttpStatus.OK;
        return new ResponseEntity<>(res, httpStatus);
    }
}

