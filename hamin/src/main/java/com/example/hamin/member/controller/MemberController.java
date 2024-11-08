package com.example.hamin.member.controller;

import com.example.hamin.member.domain.Member;
import com.example.hamin.member.domain.requestdto.*;
import com.example.hamin.member.domain.responsedto.CheckLoginDto;
import com.example.hamin.member.domain.responsedto.MemberDetailDto;
import com.example.hamin.member.model.service.MemberService;
import com.example.hamin.session.ManageSession;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Arrays;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
@Tag(name = "Member Controller", description = "회원 관련 API")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup")
    @Operation(summary = "회원가입", description = "사용자의 회원가입을 처리합니다.")
    @ApiResponse(responseCode = "201", description = "회원가입 성공", content = @Content(schema = @Schema(example = "회원가입 성공")))
    public ResponseEntity<?> signUp(
            @Parameter(description = "회원가입 요청 데이터", required = true) @RequestBody SignUpDto signUpDto) {
        memberService.signUp(signUpDto);
        return new ResponseEntity<>("회원가입 성공", HttpStatus.CREATED);
    }

    @PostMapping("/signin")
    @Operation(summary = "로그인", description = "사용자의 로그인을 처리합니다.")
    @ApiResponse(responseCode = "200", description = "로그인 성공", content = @Content(schema = @Schema(example = "로그인 성공")))
    @ApiResponse(responseCode = "401", description = "아이디 또는 비밀번호가 틀렸습니다", content = @Content(schema = @Schema(example = "아이디 또는 비밀번호가 틀렸습니다")))
    public ResponseEntity<?> signIn(
            @Parameter(description = "로그인 요청 데이터", required = true) @RequestBody LoginDto loginDto,
            HttpServletRequest request,
            HttpServletResponse response) {
        Member member = memberService.signIn(loginDto);
        if (member != null) {
            HttpSession session = request.getSession();
            ManageSession.createSession(session.getId(), member.getEmail());
            return ResponseEntity.ok("로그인 성공");
        } else {
            return new ResponseEntity<>("아이디 또는 비밀번호가 틀렸습니다", HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/chgnickname")
    @Operation(summary = "닉네임 변경", description = "사용자의 닉네임을 변경합니다.")
    @ApiResponse(responseCode = "200", description = "닉네임 변경 완료", content = @Content(schema = @Schema(example = "닉네임 변경 완료")))
    public ResponseEntity<?> changeNickName(
            @Parameter(description = "새 닉네임 정보", required = true) @RequestBody ChangeNickNameDto nickName,
            HttpServletRequest request) {
        memberService.updateNickName(request.getAttribute("user").toString(), nickName.getNickName());
        return ResponseEntity.ok("닉네임 변경 완료");
    }

    @PostMapping("/chgprofile")
    @Operation(summary = "프로필 변경", description = "사용자의 프로필 사진을 변경합니다.")
    @ApiResponse(responseCode = "200", description = "프로필 변경 완료", content = @Content(schema = @Schema(example = "닉네임 변경 완료")))
    public ResponseEntity<?> changeProfile(
            @Parameter(description = "새 프로필 정보", required = true) @RequestBody ChangeProfileDto profileDto,
            HttpServletRequest request) {
        memberService.updateProfile(request.getAttribute("user").toString(), profileDto.getProfile());
        return ResponseEntity.ok("프로필 변경 완료");
    }

    @GetMapping("/detail")
    @Operation(summary = "회원 정보 조회", description = "사용자의 상세 정보를 조회합니다.")
    @ApiResponse(responseCode = "200", description = "회원 정보 조회 성공", content = @Content(schema = @Schema(implementation = MemberDetailDto.class)))
    public ResponseEntity<?> searchMemberDetail(HttpServletRequest request) {
        Member member = memberService.searchByEmail(request.getAttribute("user").toString());
        MemberDetailDto memberDetailDto = new MemberDetailDto(member);
        return ResponseEntity.ok(memberDetailDto);
    }

    @PostMapping("/verify")
    @Operation(summary = "이메일 인증", description = "사용자의 이메일 인증을 처리합니다.")
    @ApiResponse(responseCode = "200", description = "이메일 전송 완료", content = @Content(schema = @Schema(example = "이메일 전송 완료")))
    public ResponseEntity<?> sendMail(
            @Parameter(description = "이메일 정보", required = true) @RequestBody VerifyEmailDto emailDto) {
        memberService.sendEmail(emailDto.getEmail());
        return ResponseEntity.ok("이메일 전송 완료");
    }

    @GetMapping("/checksignin")
    @Operation(summary = "로그인 상태 확인", description = "사용자의 로그인 상태를 확인합니다.")
    @ApiResponse(responseCode = "200", description = "로그인 상태 확인", content = @Content(schema = @Schema(implementation = CheckLoginDto.class)))
    public ResponseEntity<?> checkSignIn(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String sessionId = null;
        Member logInMember = null;

        if (cookies != null) {
            sessionId = Arrays.stream(cookies)
                    .filter(cookie -> "JSESSIONID".equals(cookie.getName()))
                    .map(Cookie::getValue)
                    .findFirst()
                    .orElse(null);
        }

        if (sessionId != null) {
            ManageSession.Session session = ManageSession.getSession(sessionId);
            if (session != null) {
                if (session.getCreationTime().isBefore(LocalDateTime.now())) {
                    ManageSession.removeSession(sessionId);
                }
                logInMember = memberService.searchByEmail(session.getValue());
            }
        }

        CheckLoginDto loginDto = (logInMember != null)
                ? new CheckLoginDto(true, logInMember.getNickName(), logInMember.getEmail(), logInMember.getProfile())
                : new CheckLoginDto(false, null, null, null);

        return ResponseEntity.ok(loginDto);
    }
}