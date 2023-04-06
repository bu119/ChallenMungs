package com.ssafy.ChallenMungs.challenge.general.controller;

import com.amazonaws.services.kms.model.NotFoundException;
import com.ssafy.ChallenMungs.challenge.common.entity.Challenge;
import com.ssafy.ChallenMungs.challenge.common.entity.MyChallenge;
import com.ssafy.ChallenMungs.challenge.common.service.ChallengeService;
import com.ssafy.ChallenMungs.challenge.common.service.MyChallengeService;
import com.ssafy.ChallenMungs.challenge.general.dto.GeneralBoardHistoryDto;
import com.ssafy.ChallenMungs.challenge.general.dto.GeneralBoardTodayDto;
import com.ssafy.ChallenMungs.challenge.general.entity.GeneralBoard;
import com.ssafy.ChallenMungs.challenge.general.service.GeneralBoardService;
import com.ssafy.ChallenMungs.challenge.general.service.GeneralRejectService;
import com.ssafy.ChallenMungs.image.service.FileServiceImpl;
import com.ssafy.ChallenMungs.user.entity.User;
import com.ssafy.ChallenMungs.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kotlin.collections.ArrayDeque;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/generalBoard")
@CrossOrigin("*")
@Api(value = "generalChallengeBoard", description = "일반챌린지 게시판를 관리하는 컨트롤러에요!")
public class GeneralBoardController {

    private Logger log = LoggerFactory.getLogger(GeneralBoardController.class);

    @Autowired
    private GeneralBoardService boardService;

    @Autowired
    private GeneralRejectService rejectService;

    @Autowired
    FileServiceImpl fileService;

    @Autowired
    ChallengeService challengeService;

    @Autowired
    MyChallengeService myChallengeService;

    @Autowired
    UserService userService;


    // 인증사진을 등록하는 API
    @PostMapping("/tokenConfirm/registerPicture")
    @ApiOperation(value = "인증사진을 등록하는 api입니다.", notes = "challengeId와 pictureUrl 활용하여 결과 값으로 boardId를 반환합니다.")
    public ResponseEntity<Integer> savePicture(
            HttpServletRequest request,
            @RequestParam("challengeId") Long challengeId,
            @RequestParam("pictureUrl") MultipartFile file
    ) {

        Challenge challenge = challengeService.findByChallengeId(challengeId);

        // 챌린지가 진행중인지 확인
        if (challenge.getStatus() != 1) {

            if (challenge.getStatus() == 0) {
                log.info("챌린지가 진행중이지 않습니다.");
            }
            if (challenge.getStatus() == 2) {
                log.info("끝난 챌린지입니다.");
            }
            return ResponseEntity.notFound().build();
        }

        String loginId = request.getAttribute("loginId").toString();
        User user = userService.findUserByLoginId(loginId);

        LocalDate today = LocalDate.now(ZoneId.of("Asia/Seoul"));

        // 해당 챌린지에 로그인한 유저가 참여하고 있는지 확인
        MyChallenge myChallenge = myChallengeService.findByLoginIdAndChallengeId(loginId,challengeId);
        if (myChallenge == null) {
            log.info("참여하지않은 챌린지입니다.");
            return ResponseEntity.notFound().build();
        }

        // 해당 챌린지에 이미 인증을 완료한 사진이 있는지 확인
        GeneralBoard board = boardService.findByChallengeAndUserAndRegisterDay(challenge, user, today);
        if (board != null) {
            log.info("이미 사진 인증을 완료하였습니다.");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        log.info(loginId + ": " + today +" 의 인증 게시글을 업로드 합니다.");

        // 사진 업로드
        String url = null;
        try {
            url = fileService.saveFile(file, "challenge");
        } catch (IOException e) {
            log.info("사진 업로드 실패");
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
        }


        int boardId = boardService.savePicture(
                GeneralBoard.builder()
                        .challenge(challenge)
                        .user(user)
                        .pictureUrl(url)
                        .rejectCount(0)
                        .registerDay(today)
                        .build()
        );
        return ResponseEntity.ok(boardId);
    }

    @GetMapping("tokenConfirm/getToday")
    @ApiOperation(value = "투데이 게시판을 조회하는 api입니다.", notes = "challengeId를 활용하여 결과 값으로 오늘 등록된 주어진 challengeId와 일치하는 모든 GeneralBoard 객체들이 반환합니다.")
    public ResponseEntity<List<GeneralBoardTodayDto>> getBoardsByChallengeIdToday(
            HttpServletRequest request, @PathParam("challengeId") Long challengeId) {

        String loginId = request.getAttribute("loginId").toString();
        User user = userService.findUserByLoginId(loginId);

        Challenge challenge = challengeService.findByChallengeId(challengeId);
        List<GeneralBoard> boards = boardService.getBoardsByChallengeToday(challenge);

        if (boards == null || boards.isEmpty()) {
            log.info("인증 게시글이 존재하지 않습니다.");
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
        }

        List<GeneralBoardTodayDto> dtoList = new ArrayList<>();
        for (GeneralBoard gb : boards) {
            String boardUserId = gb.getUser().getLoginId();
            String name = userService.findUserByLoginId(boardUserId).getName();
            String profileUrl = userService.findUserByLoginId(boardUserId).getProfile();
            boolean myRejectState = rejectService.existsByBoardAndUser(gb, user);

            GeneralBoardTodayDto dto = new GeneralBoardTodayDto(
                    gb.getBoardId(),
                    boardUserId,
                    name,
                    profileUrl,
                    gb.getPictureUrl(),
                    myRejectState
            );
            dtoList.add(dto);
        }
        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }
    
    @GetMapping("tokenConfirm/history")
    @ApiOperation(value = "히스토리 게시판을 조회하는 api입니다.", notes = "challengeId, boardUserId를 활용하여 결과 값으로 challengeId와 boardUserId에 해당하는 유저의 데이터들을 반환합니다.")
    public ResponseEntity<List<GeneralBoardHistoryDto>> getBoardsByChallengeIdAndLoginId(
            HttpServletRequest request,
            @PathParam("challengeId") Long challengeId,
            @PathParam("boardUserId") String boardUserId
    ) {

        String loginId = request.getAttribute("loginId").toString();
        // 로그인된 유저
        User loginUser = userService.findUserByLoginId(loginId);
        // 게시글의 유저
        User boardUser = userService.findUserByLoginId(boardUserId);
        // 해당 챌린지
        Challenge challenge = challengeService.findByChallengeId(challengeId);
        // 해당 챌린지의 선택한 게시글 유저의 기록을 가져옴
        List<GeneralBoard> boards = boardService.getBoardsByChallengeAndUser(challenge, boardUser);

        // 예외 처리 추가
        if (boards == null || boards.isEmpty()) {
            log.info("인증 게시글이 존재하지 않습니다.");
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
        }

        List<GeneralBoardHistoryDto> dtoList = new ArrayList<>();
        for (GeneralBoard gb : boards) {

            boolean myRejectState = rejectService.existsByBoardAndUser(gb, loginUser);
            boolean rejectResult = boardService.isReject(gb);

            GeneralBoardHistoryDto dto = new GeneralBoardHistoryDto(
                    gb.getBoardId(),
                    boardUserId,
                    gb.getUser().getName(),
                    gb.getUser().getProfile(),
                    gb.getRegisterDay(),
                    gb.getPictureUrl(),
                    myRejectState,
                    rejectResult
            );
            dtoList.add(dto);
        }
        return new ResponseEntity<>(dtoList, HttpStatus.OK);

    }

    @GetMapping("tokenConfirm/allHistory")
    @ApiOperation(value = "히스토리 게시판을 전체 조회하는 api입니다.", notes = "challengeId 활용하여 결과 값으로 challengeId에 해당하는 모든 데이터들을 반환합니다.")
    public ResponseEntity<List<GeneralBoardHistoryDto>> getBoardsByChallengeId(
            HttpServletRequest request,
            @PathParam("challengeId") Long challengeId
    ) {

        String loginId = request.getAttribute("loginId").toString();
        // 로그인된 유저
        User loginUser = userService.findUserByLoginId(loginId);
        // 해당 챌린지
        Challenge challenge = challengeService.findByChallengeId(challengeId);
        // 해당 챌린지의 선택한 게시글 유저의 기록을 가져옴
        List<GeneralBoard> boards = boardService.getBoardsByChallenge(challenge);

        if (boards == null || boards.isEmpty()) {
            log.info("인증 게시글이 존재하지 않습니다.");
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
        }

        List<GeneralBoardHistoryDto> dtoList = new ArrayList<>();
        for (GeneralBoard gb : boards) {

            boolean myRejectState = rejectService.existsByBoardAndUser(gb, loginUser);
            boolean rejectResult = boardService.isReject(gb);

            GeneralBoardHistoryDto dto = new GeneralBoardHistoryDto(
                    gb.getBoardId(),
                    gb.getUser().getLoginId(),
                    gb.getUser().getName(),
                    gb.getUser().getProfile(),
                    gb.getRegisterDay(),
                    gb.getPictureUrl(),
                    myRejectState,
                    rejectResult
            );
            dtoList.add(dto);
        }
        return new ResponseEntity<>(dtoList, HttpStatus.OK);

    }

    // 반려하기
    // boardId를 받아서 해당 board의 rejectCount를 1 증가하고, GeneralReject 테이블 추가
    @PostMapping("tokenConfirm/reject")
    @ApiOperation(value = "일반챌린지 인증을 반려하는 api입니다.", notes = "boradId를 활용하여 거절 횟수를 증가합니다.")
    public ResponseEntity<String> addOrCancelReject(HttpServletRequest request, @RequestParam("boardId") Integer boardId) {
        String loginId = request.getAttribute("loginId").toString();
        User user = userService.findUserByLoginId(loginId);
        // 이 board를 이미 반려했는지, 반려안했는지, 내 게시글인지 확인
        // 이미 반려했는지, 반려안했는지 == true를 반환하고 데이터 수정
        // 내 게시글이면 false
        boolean result = rejectService.addOrCancelReject(boardId, user);
        if (result) {
            HashMap<String, String> dto = new HashMap<>();
            dto.put("result", "success");
            return new ResponseEntity(dto, HttpStatus.OK); // 200
//            return ResponseEntity.ok("result : Success Reject Count updated!");
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    // successCount 갱신
    @PutMapping("/tokenConfirm/successCount")
    @ApiOperation(value = "일반챌린지 성공횟수를 갱신하는 api입니다.", notes = "challengeId를 활용하여 나의 챌린지 성공횟수를 갱신합니다.")
    public ResponseEntity<String> updateSuccessCount(HttpServletRequest request, @PathParam("challengeId") Long challengeId) {

        String loginId = request.getAttribute("loginId").toString();
        boardService.updateSuccessCount(loginId, challengeId);
        HashMap<String, String> dto = new HashMap<>();
        dto.put("result", "success");
        return new ResponseEntity(dto, HttpStatus.OK); // 200
//        return ResponseEntity.ok("result : Success Count updated!");
    }

}
