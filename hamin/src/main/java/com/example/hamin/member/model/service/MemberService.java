package com.example.hamin.member.model.service;

import com.example.hamin.member.domain.Member;
import com.example.hamin.member.domain.requestdto.LoginDto;
import com.example.hamin.member.domain.requestdto.SignUpDto;

public interface MemberService {
    void signUp(SignUpDto signUpDto);
    Member signIn(LoginDto loginDto);
    void updateNickName(String email, String nickName);
    Member searchByEmail(String email);
    void sendEmail(String email);
    boolean searchByNickName(String nickName);
}
