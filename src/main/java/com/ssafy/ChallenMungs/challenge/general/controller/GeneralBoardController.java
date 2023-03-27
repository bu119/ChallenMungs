package com.ssafy.ChallenMungs.challenge.general.controller;

import com.ssafy.ChallenMungs.challenge.common.entity.Challenge;
import com.ssafy.ChallenMungs.challenge.common.entity.MyChallenge;
import com.ssafy.ChallenMungs.challenge.common.service.ChallengeService;
import com.ssafy.ChallenMungs.challenge.common.service.MyChallengeService;
import com.ssafy.ChallenMungs.challenge.general.entity.GeneralBoard;
import com.ssafy.ChallenMungs.challenge.general.service.GeneralBoardService;
import com.ssafy.ChallenMungs.image.service.FileServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
    FileServiceImpl fileService;

    @Autowired
    ChallengeService challengeService;

    @Autowired
    MyChallengeService myChallengeService;


    // 인증사진을 등록하는 API
    @PostMapping("/tokenConfirm/registerPicture")
    @ApiOperation(value = "인증사진을 등록하는 api입니다.", notes = "결과 값으로 boardId를 반환합니다.")
    public ResponseEntity<Integer> savePicture(
            HttpServletRequest request,
            @RequestParam("challengeId") Long challengeId,
            @RequestParam("pictureUri") MultipartFile file
    ) {

        Challenge challenge = challengeService.findByChallengeId(challengeId);

        // 챌린지가 진행중인지 확인
        if (challenge.getStatus() != 1) {
            log.info("챌린지가 진행중이지 않습니다.");
            return ResponseEntity.notFound().build();
        }

        String loginId = request.getAttribute("loginId").toString();

        // 해당 챌린지에 로그인한 유저가 참여하고 있는지 확인
        MyChallenge myChallenge = myChallengeService.findByLoginIdAndChallengeId(loginId,challengeId);
        if (myChallenge == null) {
            log.info("참여하지않은 챌린지입니다.");
            return ResponseEntity.notFound().build();
        }

        // 해당 챌린지에 이미 인증을 완료한 사진이 있는지 확인
        GeneralBoard board = boardService.findByChallengeIdAndLoginIdAndRegisterDay(challengeId, loginId, LocalDate.now());
        if (board != null) {
            log.info("이미 인증을 완료하였습니다.");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        String url = null;
        try {
            url = fileService.saveFile(file, "challenge");
        } catch (IOException e) {
            log.info("사진 업로드 실패");
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
        }

        LocalDate today = LocalDate.now();
        int boardId = boardService.savePicture(
                GeneralBoard.builder()
                        .challengeId(challengeId)
                        .loginId(loginId)
                        .pictureUri(url)
                        .rejectCount(0)
                        .registerDay(today)
                        .build()
        );
        return ResponseEntity.ok(boardId);
    }

    @GetMapping("tokenConfirm/getToday")
    @ApiOperation(value = "투데이 게시판을 조회하는 api입니다.", notes = "결과 값으로 오늘 등록된 주어진 challengeId와 일치하는 모든 GeneralBoard 객체들이 반환합니다.")
    public ResponseEntity<List<GeneralBoard>> getBoardsByChallengeIdToday(
            HttpServletRequest request, @PathParam("challengeId") Long challengeId) {
        return boardService.getBoardsByChallengeIdToday(challengeId);
    }

    @GetMapping("tokenConfirm/history")
    @ApiOperation(value = "히스토리 게시판을 조회하는 api입니다.", notes = "결과 값으로 challengeI에 해당하는 유저의 데이터들을 반환합니다.")
    public ResponseEntity<List<GeneralBoard>> getBoardsByChallengeIdAndLoginId(
            HttpServletRequest request,@PathParam("challengeId") Long challengeId) {

        String loginId = request.getAttribute("loginId").toString();
        return boardService.getBoardsByChallengeIdAndLoginId(challengeId, loginId);
    }
    

}
