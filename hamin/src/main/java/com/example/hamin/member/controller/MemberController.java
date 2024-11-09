package com.example.hamin.member.controller;

import com.example.hamin.member.domain.Member;
import com.example.hamin.member.domain.requestdto.ChangeNickNameDto;
import com.example.hamin.member.domain.requestdto.LoginDto;
import com.example.hamin.member.domain.requestdto.SignUpDto;
import com.example.hamin.member.domain.requestdto.VerifyEmailDto;
import com.example.hamin.member.domain.responsedto.CheckLoginDto;
import com.example.hamin.member.domain.responsedto.MemberDetailDto;
import com.example.hamin.member.model.service.MemberService;
import com.example.hamin.session.ManageSession;
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
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    // join(회원가입)
    @PostMapping("/signup")
    public ResponseEntity<?> SignUp(@RequestBody SignUpDto signUpDto) {
        if(memberService.searchByEmail(signUpDto.getEmail()) != null) {
            return new ResponseEntity<>("이미 존재하는 이메일입니다", HttpStatus.CONFLICT);
        }
        memberService.signUp(signUpDto);
        return new ResponseEntity<>("회원가입 성공", HttpStatus.CREATED);
    }

    @GetMapping("/checknickname")
    public ResponseEntity<?> checkNickName(@RequestBody Map<String, Object> request) {
        if(memberService.searchByNickName(request.get("nickName").toString())) {
            return ResponseEntity.ok("사용 가능한 닉네임입니다");
        }
        return new ResponseEntity<>("이미 존재하는 닉네임입니다", HttpStatus.CONFLICT);
    }
    // login(로그인)
    @PostMapping("/signin")
    public ResponseEntity<?> SignIn(@RequestBody LoginDto loginDto, HttpServletRequest request, HttpServletResponse response) {
        // 회원 아이디, 비밀번호 검증
        Member member = memberService.signIn(loginDto);
        if(member != null) {
            HttpSession session = request.getSession();
            ManageSession.createSession(session.getId(), member.getEmail());
            return ResponseEntity.ok("로그인 성공");
        }
        else {
            return new ResponseEntity<>("아이디 또는 비밀번호가 틀렸습니다", HttpStatus.UNAUTHORIZED);
        }
    }
    // changeNickName(닉네임 변경)
    @PostMapping("/chgnickname")
    public ResponseEntity<?> changeNickName(@RequestBody ChangeNickNameDto nickName, HttpServletRequest request) {
        memberService.updateNickName(request.getAttribute("user").toString(), nickName.getNickName());
        return ResponseEntity.ok("닉네임 변경 완료");
    }
    // logout(로그아웃)

    // 회원 정보 조회
    @GetMapping("/detail")
    public ResponseEntity<?> searchMemberDetail(HttpServletRequest request) {
        Member member = memberService.searchByEmail(request.getAttribute("user").toString());
        System.out.println(member.getNickName());
        MemberDetailDto memberDetailDto =
                new MemberDetailDto(memberService.searchByEmail(request.getAttribute("user").toString()));
        return ResponseEntity.ok(memberDetailDto);
    }

    @PostMapping("/verify")
    public ResponseEntity<?> sendMail(@RequestBody VerifyEmailDto emailDto) {
        memberService.sendEmail(emailDto.getEmail());
        return ResponseEntity.ok("이메일 전송 완료");
    }

    @GetMapping("/checksignin")
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
            if(session != null) {
                if(session.getCreationTime().isBefore(LocalDateTime.now())) {
                    ManageSession.removeSession(sessionId);
                }
                logInMember = memberService.searchByEmail(session.getValue());
            }
        }
        CheckLoginDto loginDto;
        if(logInMember != null) {
            loginDto = new CheckLoginDto(true, logInMember.getNickName(), logInMember.getEmail());
        }
        else {
            loginDto = new CheckLoginDto(false, null, null);
        }
        return ResponseEntity.ok(loginDto);
    }

}
